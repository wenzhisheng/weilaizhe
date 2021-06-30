package com.weilaizhe.common.smsverify.service;

import com.weilaizhe.common.pojo.merchant.MerchantVO;

/**
 * @author: dameizi
 * @description: 短信验证接口层
 * @dateTime 2019-04-03 14:57
 * @className com.weilaizhe.common.smsverify.service.ISmsVerifyService
 */
public interface ISmsVerifyService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 15:27
     * @description: 发送验证码
     * @param: [smsVO, merchantVO]
     * @return: java.lang.String
     */
    String sendSms(String phoneNumbers, MerchantVO merchantVO);

}
