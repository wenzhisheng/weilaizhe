package com.weilaizhe.mqueue.processor;

import com.weilaizhe.common.config.rocketmq.MessageProcessor;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.order.service.IPaymentOrderService;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
import org.apache.commons.lang.StringUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author: dameizi
 * @description: 支付延时失效消费
 * @dateTime 2019-04-09 21:46
 * @className com.weilaizhe.mqueue.processor.Payement
 */
@Component("payment_delay_time_tag")
public class Payement implements MessageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(Payement.class);

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IPaymentOrderService iPaymentOrderService;

    @Override
    public String handleMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody());
        logger.info("支付延时时间：" + message);
        String[] arr = message.split("-");
        if(StringUtils.isNotBlank(message)){
            PaymentOrderVO orderVO = new PaymentOrderVO();
            // 获取当前MQ订单
            String tradeNoKey = MessageFormat.format(RedisKeyConst.TRADE_NO, arr[0]);
            RMap cache = redissonClient.getMap(tradeNoKey);
            if(cache == null || cache.size() == 0){
                orderVO.setTradeNo(arr[0]);
                orderVO = iPaymentOrderService.getOrder(orderVO, arr[1]);
            }else{
                orderVO = (PaymentOrderVO)cache.get(RedisKeyConst.ORDER_KEY);
            }
            // 判断三分钟后为未支付的更新状态
            if (orderVO.getTradeStatus() == 0 && orderVO.getCallbackStatus() == 0) {
                // 支付状态（0:等待支付 1:支付成功 2:支付超时 3:支付失败）
                orderVO.setTradeStatus(2);
                // 回调状态（0:等待回调 1:回调成功 2:回调结束 3:回调失败）
                orderVO.setCallbackStatus(2);
                //更新订单状态
                iPaymentOrderService.updateOrder(orderVO, arr[1]);
                //清除redis缓存
                cache.clear();
            }
        }
        return null;
    }

}
