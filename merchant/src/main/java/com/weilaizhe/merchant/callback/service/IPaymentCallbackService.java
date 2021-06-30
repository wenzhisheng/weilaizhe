package com.weilaizhe.merchant.callback.service;

import com.alibaba.fastjson.JSONObject;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
import com.weilaizhe.common.pojo.pay.WechatCallbackVO;

import java.util.Map;

/**
 * @author: dameizi
 * @description: 支付回调接口
 * @dateTime 2019-04-04 21:37
 * @className com.weilaizhe.merchant.callback.service.IPaymentCallbackService
 */
public interface IPaymentCallbackService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-17 12:13
     * @description: 手工回调
     * @param: [paymentOrderVO, jsonObject]
     * @return: boolean
     */
    boolean manualWorkCallback(PaymentOrderVO paymentOrderVO, JSONObject jsonObject);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-13 23:49
     * @description: 微信商户回调
     * @param: [wechatCallback, jsonObject]
     * @return: boolean
     */
    boolean callbackWechatMerchant(WechatCallbackVO wechatCallback, JSONObject jsonObject);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 19:10
     * @description: 支付宝商户回调验证
     * @param: [params]
     * @return: boolean
     */
    boolean alipayMerchantIsSuccess(Map<String,String> params, JSONObject jsonObject);

}
