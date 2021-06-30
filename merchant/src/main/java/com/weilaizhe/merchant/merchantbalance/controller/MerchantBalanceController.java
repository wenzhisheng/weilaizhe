package com.weilaizhe.merchant.merchantbalance.controller;

import com.weilaizhe.common.merchantbalance.service.IMerchantBalanceService;
import com.weilaizhe.common.pojo.merchantbalance.MerchantBalanceVO;
import com.weilaizhe.common.util.RedissonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dameizi
 * @description 商户余额
 * @dateTime 2019-05-23 23:28
 * @className com.weilaizhe.merchant.merchantbalance.controller.MerchantBalanceController
 */
@RestController
@RequestMapping(value = "/merchantBalance")
@Api(value = "MerchantBalanceController", tags = "MerchantBalance", description = "商户余额")
public class MerchantBalanceController {

    @Autowired
    private IMerchantBalanceService iMerchantBalanceService;

    /**
     * @author: dameizi
     * @dateTime: 2019-05-24 15:50
     * @description: 获取商户余额
     * @param: [merchantBalanceVO]
     * @return: java.lang.Object
     */
    @GetMapping("/getMerchantBalance")
    @ApiOperation(value = "获取商户余额", notes = "必填参数：Authorization")
    public Object queryById(MerchantBalanceVO merchantBalanceVO) {
        merchantBalanceVO.setMerchantId(RedissonUtil.getContextMerchantInfo().getMerchantId());
        return iMerchantBalanceService.getMerchantBalance(merchantBalanceVO);
    }

}
