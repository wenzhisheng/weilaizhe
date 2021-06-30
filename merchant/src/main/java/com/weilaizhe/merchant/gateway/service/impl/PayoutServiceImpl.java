package com.weilaizhe.merchant.gateway.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.weilaizhe.common.pojo.pay.PayoutVO;
import com.weilaizhe.merchant.gateway.service.IPayoutService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: dameizi
 * @description: 支出业务层
 * @dateTime 2019-04-11 22:00
 * @className com.weilaizhe.merchant.gateway.service.impl.PayoutServiceImpl
 */
@Service
public class PayoutServiceImpl implements IPayoutService {


    @Override
    public boolean requestPayout(JSONObject jsonObject, PayoutVO payoutVO, HttpServletRequest request, HttpServletResponse response) {
        // 获取出款账号
        //
        return false;
    }

}
