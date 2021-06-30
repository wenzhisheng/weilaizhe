package com.weilaizhe.common.smsverify.controller;

import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.smsverify.service.ISmsVerifyService;
import com.weilaizhe.common.util.CommonUtil;
import com.weilaizhe.common.util.RedissonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: dameizi
 * @description: 短信验证
 * @dateTime 2019-04-03 14:50
 * @className com.weilaizhe.common.smsverify.controller.SmsVerifyController
 */
@RestController
@RequestMapping("/smsVerify")
@Api(value = "SmsVerifyController", tags = "SmsVerifyController", description = "短信验证")
public class SmsVerifyController {

    @Autowired
    private ISmsVerifyService iSmsVerifyService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 15:27
     * @description: 发送验证码
     * @param: [smsVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/sendSms", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value="发送验证码",notes="必填参数：用途")
    public Object sendSms(@ApiParam( required = true) String phoneNumbers){
        MerchantVO merchantVO = RedissonUtil.getContextMerchantInfo();
        return iSmsVerifyService.sendSms(phoneNumbers, merchantVO);
    }

}
