package com.weilaizhe.admin.index.controller;

import com.weilaizhe.common.index.service.IIndexService;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: dameizi
 * @description: 首页统计管理
 * @dateTime 2019-04-15 23:59
 * @className com.weilaizhe.admin.index.controller.IndexController
 */
@RestController
@RequestMapping("/index")
@Api(value = "IndexController", tags = "IndexController", description = "数据分析管理")
public class IndexController {

    @Autowired
    private IIndexService indexService;

    /**
     * @author: dameizi
     * @dateTime: 2019-05-22 22:36
     * @description: 当天收款总额、支付获利、总获利、累计收款、当前余额
     * @param: []
     * @return: java.lang.Object
     */
    @GetMapping("/getTodayMoney")
    @ApiOperation(value = "当天收款总额、支付获利、总获利、累计收款、当前余额", notes = "必填参数：Authorization")
    public Object getTodayMoney(){
        return indexService.getTodayMoney(new MerchantVO());
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-22 22:58
     * @description: 累计订单量、15天订单量、今日订单量
     * @param: []
     * @return: java.lang.Object
     */
    @GetMapping("/getTotalOrder")
    @ApiOperation(value = "累计订单量、15天订单量、今日订单量", notes = "必填参数：Authorization")
    public Object getTotalOrder(){
        return indexService.getTotalOrder(new MerchantVO());
    }

}
