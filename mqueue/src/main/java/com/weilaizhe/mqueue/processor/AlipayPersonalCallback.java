package com.weilaizhe.mqueue.processor;

import com.weilaizhe.common.config.rocketmq.MessageProcessor;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

/**
 * @author: dameizi
 * @description: 支付宝个人商户回调消费
 * @dateTime 2019-04-09 21:44
 * @className com.weilaizhe.mqueue.processor.AlipayPersonalCallback
 */
@Component("alipay_personal_callback_tag")
public class AlipayPersonalCallback implements MessageProcessor {

    private static final Logger logger = LogManager.getLogger(AlipayPersonalPoll.class);

    @Override
    public String handleMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody());
        logger.info("支付宝个人商户回调消费消息队列：" + message);
        String[] arr = message.split("-");
        if(StringUtils.isNotBlank(message)){

        }
        return null;
    }

}
