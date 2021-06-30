package com.weilaizhe.merchant.callback.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.weilaizhe.common.alipaymerchant.service.IAlipayMerchantService;
import com.weilaizhe.common.balancechange.service.IBalanceChangeService;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.constant.RocketmqConst;
import com.weilaizhe.common.merchant.service.IMerchantService;
import com.weilaizhe.common.order.service.IPaymentOrderService;
import com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
import com.weilaizhe.common.pojo.pay.WechatCallbackVO;
import com.weilaizhe.common.util.CommonUtil;
import com.weilaizhe.common.util.ReturnJson;
import com.weilaizhe.common.util.SignUtil;
import com.weilaizhe.merchant.callback.service.IPaymentCallbackService;
import com.weilaizhe.merchant.gatewayconfig.AlipayConfig;
import com.weilaizhe.merchant.gatewayconfig.WechatConfig;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: dameizi
 * @description: 支付回调业务
 * @dateTime 2019-04-04 21:38
 * @className com.weilaizhe.merchant.callback.service.impl.PaymentCallbackServiceImpl
 */
@Service
@Transactional
public class PaymentCallbackServiceImpl implements IPaymentCallbackService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentCallbackServiceImpl.class);

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private DefaultMQProducer mqProducer;
    @Autowired
    private IMerchantService iMerchantService;
    @Autowired
    private IPaymentOrderService iPaymentOrderService;
    @Autowired
    private IAlipayMerchantService iAlipayMerchantService;
    @Autowired
    private IBalanceChangeService iBalanceChangeService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-17 12:15
     * @description: 手工回调回调验证
     * @param: [payOrder, jsonObject]
     * @return: boolean
     */
    @Override
    public boolean manualWorkCallback(PaymentOrderVO payOrder, JSONObject jsonObject) {
        // 获取商户信息
        MerchantVO merchantVO = new MerchantVO();
        merchantVO.setMerchantId(payOrder.getMerchantId());
        merchantVO = iMerchantService.getMerchantById(merchantVO);
        // 表分键
        String tableKey = payOrder.getTradeNo().substring(0,6);
        PaymentOrderVO tempOrder = new PaymentOrderVO();
        tempOrder.setTradeNo(payOrder.getTradeNo());
        // 回调商户处理
        if (callbackMerchant(jsonObject, String.valueOf(payOrder.getTotalAmount()), payOrder.getTradeAccount(), tableKey, payOrder, merchantVO, tempOrder)) {
            return false;
        }
        return true;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-17 12:16
     * @description: 微信商户回调验证
     * @param: [wechatCallback, jsonObject]
     * @return: boolean
     */
    @Override
    public boolean callbackWechatMerchant(WechatCallbackVO wechatCallback, JSONObject jsonObject) {
        try {
            // 校验签名是否正确
            if (wechatMerchantSign(wechatCallback, jsonObject)) {
                return false;
            }
            // 缓存获取订单记录
            String outTradeNo = wechatCallback.getOut_trade_no();
            String tableKey = outTradeNo.substring(0, 6);
            CallbackGetCache callbackGetCache = new CallbackGetCache(outTradeNo, tableKey).invoke();
            PaymentOrderVO payOrder = callbackGetCache.getPayOrder();
            MerchantVO merchantVO = callbackGetCache.getMerchantVO();
            // 微信金额分为单位要除以100
            BigDecimal totalAmount = wechatCallback.getTotal_fee().divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
            // 状态与金额验证
            if (statusAndAmountVerify(jsonObject, totalAmount, payOrder)) {
                return false;
            }
            PaymentOrderVO tempOrder = new PaymentOrderVO();
            tempOrder.setTradeNo(payOrder.getTradeNo());
            // 回调商户处理
            if (callbackMerchant(jsonObject, String.valueOf(totalAmount), payOrder.getTradeAccount(), tableKey, payOrder, merchantVO, tempOrder)) {
                return false;
            }
        }catch (Exception ex){
            ReturnJson.platformHandlingExceptions(jsonObject);
            logger.error("微信商户回调处理异常；{}", ex);
            return true;
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 19:11
     * @description: 支付宝商户回调验证
     * @param: [params]
     * @return: boolean
     */
    @Override
    public boolean alipayMerchantIsSuccess(Map<String, String> params, JSONObject jsonObject) {
        // 平台订单号
        String outTradeNo = params.get("out_trade_no");
        // 支付金额
        String totalAmount = params.get("total_amount");
        // 支付状态
        String tradeStatus = params.get("trade_status");
        // 回传的支付宝商户号
        String passbackParams = params.get("passback_params");
        String tableKey = outTradeNo.substring(0, 6);
        // 支付宝处理
        if (alipayProcessing(outTradeNo, totalAmount)){
            jsonObject.put(CommonConst.CODE, "70001");
            jsonObject.put(CommonConst.MSG, "Alipay notifies parameter error");
            jsonObject.put(CommonConst.SUB_CODE, "Notifies-alipay-parameter-error");
            jsonObject.put(CommonConst.SUB_MSG, "支付宝回调参数错误");
            return false;
        }
        // 验证签名
        AlipayMerchantVO alipayMerchantVO = new AlipayMerchantVO();
        try {
            alipayMerchantVO.setIsEnable(1);
            alipayMerchantVO.setAlipayMerchantCode(passbackParams);
            alipayMerchantVO = iAlipayMerchantService.getAlipayMerchant(alipayMerchantVO);
            if (alipayMerchantVO == null || alipayMerchantVO.getAlipayMerchantCode() == null) {
                ReturnJson.serviceCurrently(jsonObject);
                logger.error("处理支付宝商户回调失败的商户号：{}", passbackParams);
                return false;
            }
            logger.info("支付宝商户回调参数：{}，支付宝商户号：{}，公钥：{}" ,params, alipayMerchantVO.getAlipayMerchantCode(), alipayMerchantVO.getAlipayPublicKey());
            if (!AlipaySignature.rsaCheckV1(params, alipayMerchantVO.getAlipayPublicKey().trim(), AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE)){
                ReturnJson.callbakcSignError(jsonObject);
                logger.error("支付宝商户回调签名错误");
                return false;
            }
        } catch (AlipayApiException e) {
            jsonObject.put(CommonConst.CODE, "40002");
            jsonObject.put(CommonConst.MSG, "decryption-error-unknown");
            jsonObject.put(CommonConst.SUB_CODE, "Decryption error, unknown exception");
            jsonObject.put(CommonConst.SUB_MSG, "解密出错，未知异常");
            logger.error("支付宝解签失败" + e);
            return false;
        }
        // 缓存获取订单记录
        CallbackGetCache callbackGetCache = new CallbackGetCache(outTradeNo, tableKey).invoke();
        PaymentOrderVO payOrder = callbackGetCache.getPayOrder();
        MerchantVO merchantVO = callbackGetCache.getMerchantVO();
        // 状态与金额验证
        BigDecimal total_amount = new BigDecimal(totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
        if (statusAndAmountVerify(jsonObject, total_amount, payOrder)) {
            return false;
        }
        PaymentOrderVO tempOrder = new PaymentOrderVO();
        tempOrder.setTradeNo(payOrder.getTradeNo());
        if (CommonConst.ALIPAY_MERCHANT_CALLBACK_SUCCESS.equals(tradeStatus)) {
            //支付状态（0:等待支付 1:支付成功 2:支付超时 3:支付失败）
            tempOrder.setTradeStatus(1);
        } else if (CommonConst.ALIPAY_MERCHANT_CALLBACK_WAIT.equals(tradeStatus)) {
            tempOrder.setTradeStatus(0);
        } else if (CommonConst.ALIPAY_MERCHANT_CALLBACK_CLOSED.equals(tradeStatus)) {
            tempOrder.setTradeStatus(2);
        }
        // 回调商户处理
        if (callbackMerchant(jsonObject, totalAmount, passbackParams, tableKey, payOrder, merchantVO, tempOrder)) {
            return false;
        }
        return true;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-14 21:00
     * @description: 状态与金额校验
     * @param: [jsonObject, totalAmount, payOrder]
     * @return: boolean
     */
    private boolean statusAndAmountVerify(JSONObject jsonObject, BigDecimal totalAmount, PaymentOrderVO payOrder) {
        //判断订单是否处理过,防止重复处理
        if (payOrder.getCallbackStatus() != 0) {
            ReturnJson.orderProcessed(jsonObject);
            return true;
        }
        // 核对金额
        if (payOrder.getTotalAmount().compareTo(totalAmount) != 0) {
            ReturnJson.unequalAmount(jsonObject);
            logger.error("支付宝回调金额不正确，订单金额：{}，回调金额：{}", payOrder.getTotalAmount(), totalAmount);
            return true;
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-14 21:13
     * @description: 回调通知商户
     * @param: [jsonObject, totalAmount, otherMerchantCode, tableKey, payOrder, merchantVO, tempOrder]
     * @return: boolean
     */
    private boolean callbackMerchant(JSONObject jsonObject, String totalAmount, String otherMerchantCode, String tableKey, PaymentOrderVO payOrder, MerchantVO merchantVO, PaymentOrderVO tempOrder) {
        // 组装返回json格式
        JSONObject jsonObj = ReturnJson.getJsonObject(payOrder, merchantVO);
        // 平台回调商户
        String result = CommonUtil.sendPost(payOrder.getNotifyUrl(), jsonObj.toString());
        logger.info("平台回调成功商户异步通知返回：{}", result);
        if (CommonConst.CALLBACK_STATUS_SUCCESS.equals(result)) {
            // 回调状态（0:等待回调 1:回调成功 2:回调结束 3:回调失败）
            tempOrder.setCallbackStatus(1);
            // 更新订单状态
            iPaymentOrderService.updateOrder(tempOrder, tableKey);
            // 更新当前支付宝商户收款额
            iAlipayMerchantService.updateDailyTotalAmount(otherMerchantCode, totalAmount);
            // 实际付款成功且回调成功即写入账变,更新商户余额
            iBalanceChangeService.insertPayment(payOrder, merchantVO);
        } else if (CommonConst.CALLBACK_STATUS_CLOSED.equals(result)){
            ReturnJson.platformHandlingExceptions(jsonObject);
            return true;
        }else {
            // 生产回调消息
            int level = CommonUtil.getMqDelayLevel(merchantVO.getOrderDelayTime());
            if (sendMqMessageCallback(level, payOrder.getTradeNo(), tableKey, jsonObject)){
                return true;
            }
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-09 14:24
     * @description: 商户生产回调消息
     * @param: [level, orderNo, tableKey, jsonObject]
     * @return: void
     */
    private boolean sendMqMessageCallback(int level, String orderNo, String tableKey, JSONObject jsonObject) {
        try {
            String keyJoin = "{0}-{1}";
            String body = MessageFormat.format("{0}-{1}-{2}-{3}", level, orderNo, tableKey, level+5);
            Message msg = new Message(
                    // 保持跟消费者的topic一致
                    RocketmqConst.MQ_ALIPAY_MERCHANT_CALLBACK_TOPIC,
                    // 保持跟消费者的tag一致
                    RocketmqConst.MQ_ALIPAY_MERCHANT_CALLBACK_TAG,
                    // key
                    MessageFormat.format(keyJoin, body, Long.toString(System.currentTimeMillis())),
                    // body
                    body.getBytes(CommonConst.CODING_UTF8)
            );
            //messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            msg.setDelayTimeLevel(level);
            mqProducer.send(msg);
        } catch (Exception e) {
            ReturnJson.serviceCurrently(jsonObject);
            logger.error("商户回调发送消息异常" + e);
            return true;
        }
        return false;
    }

    private boolean alipayProcessing(String outTradeNo, String totalAmount) {
        if (StringUtils.isEmpty(outTradeNo)) {
            logger.error("AliPay Notify parameter out_trade_no is empty. out_trade_no={}", outTradeNo);
            return true;
        }
        if (StringUtils.isEmpty(totalAmount)) {
            logger.error("AliPay Notify parameter total_amount is empty. total_fee={}", totalAmount);
            return true;
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-14 21:49
     * @description: 微信商户回调签名校验
     * @param: [wechatCallback, jsonObject]
     * @return: boolean
     */
    private boolean wechatMerchantSign(WechatCallbackVO wechatCallback, JSONObject jsonObject) {
        Map<String,String> hashMap = new HashMap<String,String>(16);
        hashMap.put("out_trade_no", wechatCallback.getOut_trade_no());
        hashMap.put("transaction_id", wechatCallback.getTransaction_id());
        hashMap.put("cash_fee", String.valueOf(wechatCallback.getCash_fee()));
        hashMap.put("total_fee", String.valueOf(wechatCallback.getTotal_fee()));
        hashMap.put("result_code", wechatCallback.getResult_code());
        hashMap.put("sign_type", wechatCallback.getSign_type());
        hashMap.put("time_end",wechatCallback.getTime_end());
        String sign = SignUtil.generateSignature(hashMap, WechatConfig.GETKEY);
        if(!wechatCallback.getSign().equals(sign)){
            ReturnJson.callbakcSignError(jsonObject);
            logger.error("微信商户回调签名错误");
            return true;
        }
        return false;
    }

    private class CallbackGetCache {
        private String outTradeNo;
        private String tableKey;
        private PaymentOrderVO payOrder;
        private MerchantVO merchantVO;

        public CallbackGetCache(String outTradeNo, String tableKey) {
            this.outTradeNo = outTradeNo;
            this.tableKey = tableKey;
        }

        public PaymentOrderVO getPayOrder() {
            return payOrder;
        }

        public MerchantVO getMerchantVO() {
            return merchantVO;
        }

        public CallbackGetCache invoke() {
            String tradeNoKey = MessageFormat.format(RedisKeyConst.TRADE_NO, outTradeNo);
            RMap cache = redissonClient.getMap(tradeNoKey);
            payOrder = new PaymentOrderVO();
            merchantVO = new MerchantVO();
            // 没有则数据库获取
            if(cache == null || cache.size() == 0){
                payOrder.setTradeNo(outTradeNo);
                payOrder = iPaymentOrderService.getOrder(payOrder, tableKey);
                merchantVO.setMerchantId(payOrder.getMerchantId());
                merchantVO = iMerchantService.getMerchantById(merchantVO);
            }else{
                payOrder = (PaymentOrderVO) cache.get(RedisKeyConst.ORDER_KEY);
                merchantVO = (MerchantVO) cache.get(RedisKeyConst.MERCHANT_KEY);
            }
            return this;
        }
    }
}
