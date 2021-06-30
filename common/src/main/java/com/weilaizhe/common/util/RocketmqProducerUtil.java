package com.weilaizhe.common.util;

import com.alibaba.fastjson.JSONObject;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RocketmqConst;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * @author dameizi
 * @description Rocketmq工具类
 * @dateTime 2019-06-06 14:32
 * @className com.weilaizhe.common.util.RocketmqProducerUtil
 */
public class RocketmqProducerUtil {

    private static final Logger logger = LoggerFactory.getLogger(RocketmqProducerUtil.class);

    /** 通过上下文获取MQ生产者 */
    private static DefaultMQProducer defaultMQProducer = ApplicationContextUtil.getBean("rocketMQProducer", DefaultMQProducer.class);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-06 21:42
     * @description: 支付宝个人商户
     * @param: [level, orderNumber, tableKey, jsonObject]
     * @return: boolean
     */
    public static boolean sendMqAlipayPersonal(int level, String orderNumber, String tableKey, JSONObject jsonObject) {
        try {
            String keyJoin = "{0}-{1}";
            String body = MessageFormat.format(keyJoin, orderNumber, tableKey);
            Message msg = new Message(
                    // 保持跟消费者的topic一致
                    RocketmqConst.MQ_ALIPAY_PERSONAL_POLL_TOPIC,
                    // 保持跟消费者的tag一致
                    RocketmqConst.MQ_ALIPAY_PERSONAL_POLL_TAG,
                    // key
                    MessageFormat.format(keyJoin, body, Long.toString(System.currentTimeMillis())),
                    // body
                    body.getBytes(CommonConst.CODING_UTF8)
            );
            // messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            msg.setDelayTimeLevel(level);
            // Call send message to deliver message to one of brokers.
            SendResult sendResult = defaultMQProducer.send(msg);
            logger.info(String.format("MQ生产消息：%s%n", sendResult));
        } catch (Exception e) {
            ReturnJson.serviceCurrently(jsonObject);
            logger.error("平台订单号：{}，发送消息失败", orderNumber ,e);
            return true;
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-06 22:44
     * @description: 支付生产消息
     * @param: [level,orderNumber,tableKey,jsonObject]
     * @return: boolean
     */
    public static boolean sendMqMerchantCallback(int level, String orderNumber, String tableKey, JSONObject jsonObject) {
        try {
            String keyJoin = "{0}-{1}";
            String body = MessageFormat.format(keyJoin, orderNumber, tableKey);
            Message msg = new Message(
                    // 保持跟消费者的topic一致
                    RocketmqConst.MQ_PAYMENT_DELAY_TIME_TOPIC,
                    // 保持跟消费者的tag一致
                    RocketmqConst.MQ_PAYMENT_DELAY_TIME_TAG,
                    // key
                    MessageFormat.format(keyJoin, body, Long.toString(System.currentTimeMillis())),
                    // body
                    body.getBytes(CommonConst.CODING_UTF8)
            );
            // messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            msg.setDelayTimeLevel(level);
            // Call send message to deliver message to one of brokers.
            SendResult sendResult = defaultMQProducer.send(msg);
            logger.info(String.format("MQ生产消息：%s%n", sendResult));
        } catch (Exception e) {
            ReturnJson.serviceCurrently(jsonObject);
            logger.error("平台订单号：{}，发送消息失败", orderNumber ,e);
            return true;
        }
        return false;
    }

}
