package com.weilaizhe.merchant.gateway.service;

import com.alibaba.fastjson.JSONObject;
import com.weilaizhe.common.pojo.pay.PaymentVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: dameizi
 * @description: 支付接口
 * @dateTime 2019-04-04 21:31
 * @className com.weilaizhe.merchant.action.service.IPaymentService
 */
public interface IPaymentService {

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 19:16
     * @description: 支付宝个人商户支付
     * @param: [paymentVO, request, response, jsonObject]
     * @return: boolean
     */
    boolean alipayPersonal(PaymentVO paymentVO, HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-08 22:53
     * @description: 微信商户
     * @param: [paymentVO, request, response, jsonObject]
     * @return: boolean
     */
    boolean wechatMerchant(PaymentVO paymentVO, HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-06 16:49
     * @description: 支付宝商户
     * @param: [paymentVO, response, jsonObject]
     * @return: void
     */
    boolean alipayMerchant(PaymentVO paymentVO, HttpServletResponse response, JSONObject jsonObject);

}
