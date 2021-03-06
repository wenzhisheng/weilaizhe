package com.weilaizhe.mqueue.consumber;

import com.weilaizhe.common.config.rocketmq.MessageListener;
import com.weilaizhe.common.config.rocketmq.RocketMQException;
import com.weilaizhe.common.constant.RocketmqConst;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: dameizi
 * @description: 支付宝个人商户回调生产配置
 * @dateTime 2019-04-09 21:43
 * @className com.weilaizhe.mqueue.consumber.AlipayPersonalCallbackConfig
 */
@Configuration
public class AlipayPersonalCallbackConfig {

    private static final Logger logger = LogManager.getLogger(AlipayPersonalCallbackConfig.class);

    /** 通用配置 */
    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;

    /** groupName topic tag */
    private String groupName = RocketmqConst.MQ_ALIPAY_PERSONAL_CALLBACK_GROUPNAME;
    private String topic = RocketmqConst.MQ_ALIPAY_PERSONAL_CALLBACK_TOPIC;
    private String tag = RocketmqConst.MQ_ALIPAY_PERSONAL_CALLBACK_TAG;

    @Bean(value = "alipayPersonalCallback")
    public DefaultMQPushConsumer getRocketMQConsumer() throws RocketMQException {
        if (StringUtils.isBlank(this.namesrvAddr)){
            throw new RocketMQException("namesrvAddr is null !!!");
        }
        if (StringUtils.isBlank(this.groupName)){
            throw new RocketMQException("groupName is null !!!");
        }
        if (StringUtils.isBlank(this.topic)){
            throw new RocketMQException("topic is null !!!");
        }
        if (StringUtils.isBlank(this.tag)){
            throw new RocketMQException("tag is null !!!");
        }
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(this.groupName);
        consumer.setNamesrvAddr(this.namesrvAddr);
        consumer.setConsumeThreadMin(this.consumeThreadMin);
        consumer.setConsumeThreadMax(this.consumeThreadMax);
        //这里设置的是一个consumer的消费策略
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.registerMessageListener(new MessageListener());
        try {
            consumer.subscribe(this.topic, this.tag);
            consumer.start();
            logger.info("consumer run success! groupName:{},topic:{},namesrvAddr:{}", this.groupName, this.topic, this.namesrvAddr);
        }catch (MQClientException e){
            logger.error("consumer is fail! groupName:{},topic:{},namesrvAddr:{}",this.groupName, this.topic, this.namesrvAddr,e);
            throw new RocketMQException(e);
        }
        return consumer;
    }

}
