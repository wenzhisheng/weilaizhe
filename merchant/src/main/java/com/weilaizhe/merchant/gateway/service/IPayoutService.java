package com.weilaizhe.merchant.gateway.service;

import com.alibaba.fastjson.JSONObject;
import com.weilaizhe.common.pojo.pay.PayoutVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: dameizi
 * @description: 支出接口层
 * @dateTime 2019-04-11 22:01
 * @className com.weilaizhe.merchant.gateway.service.IPayoutService
 */
public interface IPayoutService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-12 18:28
     * @description: 支出接口
     * @param: [jsonObject, payoutVO, request, response]
     * @return: boolean
     */
    boolean requestPayout(JSONObject jsonObject, PayoutVO payoutVO, HttpServletRequest request, HttpServletResponse response);

}