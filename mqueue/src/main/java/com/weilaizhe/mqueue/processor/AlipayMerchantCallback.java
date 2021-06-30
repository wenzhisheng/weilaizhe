package com.weilaizhe.mqueue.processor;

import com.alibaba.fastjson.JSONObject;
import com.weilaizhe.common.alipaymerchant.service.IAlipayMerchantService;
import com.weilaizhe.common.balancechange.service.IBalanceChangeService;
import com.weilaizhe.common.config.rocketmq.MessageProcessor;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.constant.RocketmqConst;
import com.weilaizhe.common.merchant.service.IMerchantService;
import com.weilaizhe.common.order.service.IPaymentOrderService;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
import com.weilaizhe.common.util.CommonUtil;
import com.weilaizhe.common.util.ReturnJson;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author: dameizi
 * @description: 支付宝商户回调消费
 * @dateTime 2019-04-09 21:46
 * @className com.weilaizhe.mqueue.processor.AlipayMerchantCallback
 */
@Component("alipay_merchant_callback_tag")
public class AlipayMerchantCallback implements MessageProcessor {

    private static final Logger logger = LogManager.getLogger(AlipayMerchantCallback.class);

    @Autowired
    private DefaultMQProducer mqProducer;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IMerchantService iMerchantService;
    @Autowired
    private IPaymentOrderService iPaymentOrderService;
    @Autowired
    private IBalanceChangeService iBalanceChangeService;
    @Autowired
    private IAlipayMerchantService iAlipayMerchantService;

    @Override
    public String handleMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody());
        logger.info("支付宝商户回调消费：" + message);
        if(StringUtils.isNotBlank(message)){
            String[] arr = message.split("-");
            PaymentOrderVO orderVO = new PaymentOrderVO();
            MerchantVO merchantVO = new MerchantVO();
            // 表示消息重新发送五次就不再回调
            if(Integer.valueOf(arr[0]) < Integer.valueOf(arr[3])){
                return null;
            }else{
                // 缓存获取订单记录
                String tradeNoKey = MessageFormat.format(RedisKeyConst.TRADE_NO, arr[1]);
                RMap cache = redissonClient.getMap(tradeNoKey);
                if(cache == null || cache.size() == 0){
                    orderVO.setTradeNo(arr[1]);
                    orderVO = iPaymentOrderService.getOrder(orderVO, arr[2]);
                    merchantVO.setMerchantId(orderVO.getMerchantId());
                    merchantVO = iMerchantService.getMerchantById(merchantVO);
                }else{
                    orderVO = (PaymentOrderVO) cache.get(RedisKeyConst.ORDER_KEY);
                    merchantVO = (MerchantVO) cache.get(RedisKeyConst.MERCHANT_KEY);
                }
                // 组装返回json格式
                JSONObject jsonObj = ReturnJson.getJsonObject(orderVO, merchantVO);
                // 平台回调商户
                String result = CommonUtil.sendPost(orderVO.getNotifyUrl(), jsonObj.toString());
                logger.info("支付宝商户回调商户异步通知返回：" + result);
                if (CommonConst.CALLBACK_STATUS_SUCCESS.equals(result)) {
                    // 回调状态（0:等待回调 1:回调成功 2:回调结束 3:回调失败）
                    orderVO.setCallbackStatus(1);
                    // 更新订单状态
                    iPaymentOrderService.updateOrder(orderVO, arr[2]);
                    // 更新当前支付宝商户收款额
                    iAlipayMerchantService.updateDailyTotalAmount(orderVO.getTradeAccount(), String.valueOf(orderVO.getTotalAmount()));
                    // 实际付款成功且回调成功即写入账变,更新商户余额
                    iBalanceChangeService.insertPayment(orderVO, merchantVO);
                } else {
                    // 支付宝商户生产回调消息
                    this.sendMqMessageCallback(Integer.valueOf(arr[0]), orderVO.getTradeNo(), arr[2], Integer.valueOf(arr[3]));
                }
            }
        }
        return null;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-09 14:24
     * @description: 支付宝商户生产回调消息
     * @param: [level, orderNo, tableKey, jsonObject]
     * @return: void
     */
    private void sendMqMessageCallback(int level, String orderNo, String tableKey, int next) {
        try {
            String keyJoin = "{0}-{1}";
            String body = MessageFormat.format("{0}-{1}-{2}-{3}", level+1, orderNo, tableKey, next);
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
            msg.setDelayTimeLevel(level+1);
            mqProducer.send(msg);
        } catch (Exception e) {
            logger.error("支付宝商户回调发送消息异常" + e);
        }
    }

}
