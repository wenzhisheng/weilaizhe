package com.weilaizhe.admin.alipaymerchant.controller;

import com.weilaizhe.common.alipaymerchant.service.IAlipayMerchantService;
import com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO;
import com.weilaizhe.common.pojo.base.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: dameizi
 * @description: 支付宝商户管理
 * @dateTime 2019-04-15 14:10
 * @className com.weilaizhe.admin.alipaymerchant.controller.AlipayMerchantController
 */
@RestController
@RequestMapping(value = "/alipayMerchant")
@Api(value = "AlipayMerchantController", tags = "AlipayMerchant", description = "支付宝商户管理")
public class AlipayMerchantController {

    @Autowired
    private IAlipayMerchantService iAlipayMerchantService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 15:05
     * @description: 支付宝商户分页查询
     * @param: [alipayMerchantVO, pageVO]
     * @return: java.lang.Object
     */
    @GetMapping("/page")
    @ApiOperation(value = "支付宝商户分页查询", notes = "必填参数：Authorization")
    public Object page(AlipayMerchantVO alipayMerchantVO, PageVO pageVO) {
        return iAlipayMerchantService.page(alipayMerchantVO, pageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:19
     * @description: 批量绑定商户
     * @param: [wechatMerchantVO]
     * @return: java.lang.Object
     */
    @PostMapping("/batchBinding")
    @ApiOperation(value="批量绑定商户",notes="必填字段：id")
    public Object batchBinding(@RequestBody AlipayMerchantVO alipayMerchantVO){
        return iAlipayMerchantService.batchBinding(alipayMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 18:38
     * @description: 解绑
     * @param: [wechatMerchantVO]
     * @return: java.lang.Object
     */
    @PostMapping("/unbind")
    @ApiOperation(value="解绑",notes="必填字段：alipayMerchantId")
    public Object unbind(@RequestBody AlipayMerchantVO alipayMerchantVO){
        return iAlipayMerchantService.unbind(alipayMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 15:05
     * @description: 支付宝商户新增
     * @param: [alipayMerchantVO]
     * @return: java.lang.Object
     */
    @PostMapping("/insert")
    @ApiOperation(value = "支付宝商户新增", notes = "必填参数：Authorization")
    public Object insert(@RequestBody AlipayMerchantVO alipayMerchantVO) {
        return iAlipayMerchantService.insert(alipayMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 15:05
     * @description: 支付宝商户修改
     * @param: [alipayMerchantVO]
     * @return: java.lang.Object
     */
    @PostMapping("/update")
    @ApiOperation(value = "支付宝商户修改", notes = "必填参数：Authorization")
    public Object update(@RequestBody AlipayMerchantVO alipayMerchantVO) {
        return iAlipayMerchantService.update(alipayMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 15:05
     * @description: 支付宝商户删除
     * @param: [alipayMerchantVO]
     * @return: java.lang.Object
     */
    @GetMapping("/delete")
    @ApiOperation(value = "支付宝商户删除", notes = "必填参数：Authorization")
    public Object delete(AlipayMerchantVO alipayMerchantVO) {
        return iAlipayMerchantService.delete(alipayMerchantVO);
    }

}