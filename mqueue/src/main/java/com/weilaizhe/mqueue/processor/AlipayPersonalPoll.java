package com.weilaizhe.mqueue.processor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weilaizhe.common.alipaypersonal.service.IAlipayPersonalService;
import com.weilaizhe.common.balancechange.service.IBalanceChangeService;
import com.weilaizhe.common.config.rocketmq.MessageProcessor;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.constant.RocketmqConst;
import com.weilaizhe.common.merchant.dao.IMerchantDao;
import com.weilaizhe.common.merchant.service.IMerchantService;
import com.weilaizhe.common.order.dao.IPaymentOrderDao;
import com.weilaizhe.common.order.service.IPaymentOrderService;
import com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
import com.weilaizhe.common.pojo.pay.PaymentVO;
import com.weilaizhe.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author: dameizi
 * @description: 支付宝个人商户轮询消费
 * @dateTime 2019-04-09 20:24
 * @className com.weilaizhe.mqueue.processor.AlipayPersonalPoll
 */
@Component("alipay_personal_poll_tag")
public class AlipayPersonalPoll implements MessageProcessor {

    private static final Logger logger = LogManager.getLogger(AlipayPersonalPoll.class);

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IMerchantService iMerchantService;
    @Autowired
    private IPaymentOrderService iPaymentOrderService;
    @Autowired
    private IAlipayPersonalService iAlipayPersonalService;
    @Autowired
    private IPaymentOrderDao iPaymentOrderDao;
    @Autowired
    private IMerchantDao iMerchantDao;
    @Autowired
    private IBalanceChangeService iBalanceChangeService;
    @Autowired
    private DefaultMQProducer mqProducer;

    @Override
    public String handleMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody());
        PaymentOrderVO orderVO = new PaymentOrderVO();
        MerchantVO merchantVO = new MerchantVO();
        PaymentVO paymentVO = new PaymentVO();
        AlipayPersonalVO alipayPersonalVO = new AlipayPersonalVO();
        logger.info("支付宝个人商户轮询消费消息队列：{}", message);
        if(StringUtils.isNotBlank(message)){
            String[] arr = message.split("-");
            try {
                Thread.sleep(3000);
                RLock rLock = null;
                String tradeNoKey = MessageFormat.format(RedisKeyConst.TRADE_NO, arr[1]);
                RMap cache = redissonClient.getMap(tradeNoKey);
                if(cache == null || cache.size() == 0){
                    orderVO.setTradeNo(arr[0]);
                    orderVO = iPaymentOrderService.getOrder(orderVO, arr[1]);
                    merchantVO.setMerchantId(orderVO.getMerchantId());
                    merchantVO = iMerchantService.getMerchantById(merchantVO);
                }else{
                    orderVO = (PaymentOrderVO) cache.get(RedisKeyConst.ORDER_KEY);
                    merchantVO = (MerchantVO) cache.get(RedisKeyConst.MERCHANT_KEY);
                    paymentVO = (PaymentVO) cache.get(RedisKeyConst.PAYMENY_KEY);
                }
                // 获取对应二维码信息  （查詢商戶信息）
                alipayPersonalVO.setAlipayAccount(orderVO.getTradeAccount());
                alipayPersonalVO = iAlipayPersonalService.getAlipayPersonal(alipayPersonalVO);
                // 计算轮询时间范围
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(orderVO.getGmtCreate());
                calendar.add(Calendar.SECOND,120);
                Date endTime = calendar.getTime();
                String startTimeStr = DateUtil.dateToString2(orderVO.getGmtCreate());
                String endTimeStr = DateUtil.dateToString2(endTime);
                merchantVO.setMerchantId(orderVO.getMerchantId());
                //遍历12次
                for(int i = 0; i<12; i++){
                    String session = alipayPersonalVO.getSessionInfo();
                    String account = alipayPersonalVO.getAlipayAccount();
                    String token = alipayPersonalVO.getLoginToken();
                    logger.info("startTimeStr is {}",startTimeStr);
                    logger.info("endTimeStr is {}",endTimeStr);
                    String result = CurlUtil.callAlipayOrder(session,account,token,startTimeStr,endTimeStr);
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    // 如果无法获取到result视为接口异常
                    if(null == jsonObject.get("result")){
                        alipayPersonalVO.setRemark("支付宝接口异常,商户号需要重新登录，再开启");
                        alipayPersonalVO.setIsUse(0);
                        alipayPersonalVO.setIsEnable(1);
                        // 支付超时、回调结束
                        orderVO.setTradeStatus(2);
                        orderVO.setCallbackStatus(2);
                        break;
                    }
                    JSONArray array = jsonObject.getJSONObject("result").getJSONArray("detail");
                    if(null != array && array.size()>0){
                        alipayPersonalVO.setRemark("登录正常");
                        JSONObject d = (JSONObject) array.get(0);
                        String totalAmount = (String) d.get("totalAmount");
                        logger.info("订单金额 {}",totalAmount);
                        logger.info("系统订单号{}",orderVO.getOutTradeNo());
                        logger.info("支付宝订单号{}",String.valueOf(d.get("tradeNo")));
                        if(!totalAmount.isEmpty()){
                            // 接口抓取到数据
                            BigDecimal totalAmount1 = new BigDecimal(totalAmount).setScale(2,BigDecimal.ROUND_FLOOR);
                            BigDecimal amount1 = orderVO.getTradeAmount().setScale(2, BigDecimal.ROUND_FLOOR);
                            if(totalAmount1.equals(amount1)){
                                orderVO.setTradeStatus(1);
                                alipayPersonalVO.setIsUse(0);
                            }else{
                                // 金额不对则支付失败
                                orderVO.setTotalAmount(totalAmount1);
                                orderVO.setTradeStatus(3);
                                alipayPersonalVO.setIsUse(0);
                                //（更新订单状态）支付失败优先更新订单状态，因写入账变时取实际付款金额为准
                                iPaymentOrderDao.updateOrder(orderVO, "tableKey");
                                try{
                                    rLock = redissonClient.getFairLock("alipayPersonCallback" + orderVO.getOutTradeNo());
                                    rLock.lock(5, TimeUnit.SECONDS);
                                    // 只有实际付款成功才写入账变
                                    merchantVO = iMerchantDao.getMerchantById(merchantVO);
                                    // 更新商户余额,记录账变
                                    iBalanceChangeService.insertPayment(orderVO,merchantVO);
                                }finally {
                                    if (rLock != null){
                                        rLock.unlock();
                                    }
                                }
                                logger.info("交易金额等于{},付款金额等于{}", amount1, totalAmount);
                            }
                            break;
                        }
                    }
                    //如果12次 无法轮询到数据 视为订单未支付
                    if(i==11) {
                        //支付状态（0:等待支付 1:支付成功 2:支付超时 3:支付失败）
                        orderVO.setTradeStatus(2);
                        //回调状态（0:等待回调 1:回调成功 2:回调结束 3:回调失败）
                        orderVO.setCallbackStatus(2);
                        //账户是否被占用 0:未占用 1:已占用
                        alipayPersonalVO.setIsUse(0);
                    }
                    Thread.sleep(10000);
                }
                //如果支付成功进行回调
                if(1 == orderVO.getTradeStatus()){
                    //查询商户信息
                    merchantVO = iMerchantDao.getMerchantById(merchantVO);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("tradeNo", orderVO.getTradeNo());
                    jsonObject.put("outTradeNo",orderVO.getOutTradeNo());
                    jsonObject.put("tradeAmount",orderVO.getTradeAmount());
                    jsonObject.put("tradeTypeCode",orderVO.getTradeTypeCode());
                    jsonObject.put("sign", SignUtil.dataSignature(paymentVO, merchantVO));
                    //发送http回调请求
                    logger.info("支付宝个人商户回调接入方请求信息为"+jsonObject.toString());
                    String result = CommonUtil.sendPost(orderVO.getNotifyUrl(),jsonObject.toString());
                    logger.info("支付宝个人商户回调接入方返回信息:{}", result);
                    if(CommonConst.CALLBACK_STATUS_SUCCESS.equals(result)){
                        //回调状态 0:等待回调 1:回调成功 2:回调结束 3:回调失败
                        orderVO.setCallbackStatus(1);
                        try{
                            rLock = redissonClient.getFairLock("alipayPersonCallback" + orderVO.getOutTradeNo());
                            rLock.lock(30, TimeUnit.SECONDS);
                            // 实际付款成功且回调成功即写入账变
                            merchantVO = iMerchantDao.getMerchantById(merchantVO);
                            // 更新商户余额,记录账变
                            iBalanceChangeService.insertPayment(orderVO,merchantVO);
                        }finally {
                            if (rLock!=null){
                                rLock.unlock();
                            }
                        }
                    }else{
                        orderVO.setCallbackStatus(3);
                        //发送延迟消息进行再次通知
                        sendMqMessageMerchantPay(4, orderVO.getTradeNo(), "");
                    }
                }
                //（更新订单状态）
                iPaymentOrderDao.updateOrder(orderVO, "tableKey");
                //(更新个人商户状态)
                iAlipayPersonalService.update(alipayPersonalVO);
                logger.info("支付宝个人商户监听结束 ==> " + orderVO.toString()+"==>"+alipayPersonalVO.toString());
            } catch (Exception e) {
                logger.error("支付宝订单轮询出现异常{}",e);
            }
        }
        return null;
    }

    private void sendMqMessageMerchantPay(int level, String orderNumber, String tableKey) {
        try {
            String keyJoin = "{0}-{1}";
            String body = MessageFormat.format(keyJoin, orderNumber, tableKey);
            Message msg = new Message(
                    //保持跟消费者的topic一致
                    RocketmqConst.MQ_PAYMENT_DELAY_TIME_TOPIC,
                    //保持跟消费者的tag一致
                    RocketmqConst.MQ_PAYMENT_DELAY_TIME_TAG,
                    //key
                    MessageFormat.format(keyJoin, body, Long.toString(System.currentTimeMillis())),
                    //body
                    body.getBytes(CommonConst.CODING_UTF8)
            );
            // messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            msg.setDelayTimeLevel(level);
            mqProducer.send(msg);
        } catch (Exception e) {
            logger.error("平台订单号：{}，发送消息失败", orderNumber ,e);
        }
    }

}
