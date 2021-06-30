package com.weilaizhe.payment.exit.controller;

import com.weilaizhe.common.merchant.service.IMerchantService;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.pay.PaymentVO;
import com.weilaizhe.common.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

/**
 * @author: dameizi
 * @description: 出款测试
 * @dateTime 2019-04-10 20:16
 * @className ExitController
 */
@RestController
@RequestMapping("/exit")
@Api(value = "ExitController", tags = "ExitController", description = "出款测试")
public class ExitController {

    @Autowired
    private IMerchantService iMerchantService;

    @GetMapping("/receive")
    @ApiOperation(value = "是否出款", notes = "必填参数：支付参数")
    public Object receive(PaymentVO paymentVO){
        MerchantVO merchantVO = new MerchantVO();
        merchantVO.setMerchantId(paymentVO.getApp_id());
        iMerchantService.getMerchantById(merchantVO);
        if (!paymentVO.getSign().equals(SignUtil.dataSignature(paymentVO, merchantVO))){
            return "TRADE_CLOSED";
        }
        return "TRADE_SUCCESS";
    }

    public static void main(String[] args) {

    }

    public static String CreateDNSRecord(){
        String pathname = "D:\\doman\\222.txt";

        return null;
    }

}
