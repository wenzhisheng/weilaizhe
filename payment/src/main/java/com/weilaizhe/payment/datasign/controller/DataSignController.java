package com.weilaizhe.payment.datasign.controller;

import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.pay.PaymentVO;
import com.weilaizhe.common.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dameizi
 * @description 数据签名
 * @dateTime 2019-06-04 12:57
 * @className com.weilaizhe.payment.datasign.controller.DataSignController
 */
@RestController
@RequestMapping(value = "/dataSign")
@Api(value="DataSignController", tags="DataSign", description="支付请求")
public class DataSignController {

    /**
     * @author: dameizi
     * @dateTime: 2019-06-03 20:49
     * @description: 获取数据签名
     * @param: [paymentVO, merchantVO]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @GetMapping(value = "/getSign")
    @ApiOperation(value="获取数据签名", notes="加密参数")
    public Object getSign(PaymentVO paymentVO) {
        MerchantVO merchantVO = new MerchantVO();
        merchantVO.setSecretKey(paymentVO.getExtend_params());
        return SignUtil.dataSignature(paymentVO, merchantVO);
    }

}
