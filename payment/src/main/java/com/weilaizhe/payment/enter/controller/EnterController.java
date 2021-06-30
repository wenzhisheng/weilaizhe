package com.weilaizhe.payment.enter.controller;

import com.weilaizhe.common.merchant.service.IMerchantService;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.pay.PaymentVO;
import com.weilaizhe.common.util.CurlUtil;
import com.weilaizhe.common.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.weilaizhe.common.util.CurlUtil.partitionCommandLine;

/**
 * @author: dameizi
 * @description: 入款测试
 * @dateTime 2019-04-10 20:16
 * @className EnterController
 */
@RestController
@RequestMapping("/enter")
@Api(value = "EnterController", tags = "EnterController", description = "入款测试")
public class EnterController {

    @Autowired
    private IMerchantService iMerchantService;

    @GetMapping("/receive")
    @ApiOperation(value = "是否入款", notes = "必填参数：支付参数")
    public Object receive(PaymentVO paymentVO){
        MerchantVO merchantVO = new MerchantVO();
        merchantVO.setMerchantId(paymentVO.getApp_id());
        iMerchantService.getMerchantById(merchantVO);
        if (!paymentVO.getSign().equals(SignUtil.dataSignature(paymentVO, merchantVO))){
            return "TRADE_CLOSED";
        }
        return "TRADE_SUCCESS";
    }

}
