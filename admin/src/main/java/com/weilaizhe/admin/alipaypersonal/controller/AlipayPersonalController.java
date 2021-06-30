package com.weilaizhe.admin.alipaypersonal.controller;

import com.weilaizhe.common.alipaypersonal.service.IAlipayPersonalService;
import com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO;
import com.weilaizhe.common.pojo.base.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: dameizi
 * @description: 支付宝个人商户管理
 * @dateTime 2019-06-04 14:10
 * @className com.weilaizhe.admin.alipaypersonal.controller.AlipayPersonalController
 */
@RestController
@RequestMapping(value = "/alipayPersonal")
@Api(value = "AlipayPersonalController", tags = "AlipayPersonal", description = "支付宝个人商户管理")
public class AlipayPersonalController {

    @Autowired
    private IAlipayPersonalService iAlipayPersonalService;

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 16:30
     * @description: 支付宝个人商户分页
     * @param: [alipayPersonalVO, pageVO]
     * @return: java.lang.Object
     */
    @GetMapping("/page")
    @ApiOperation(value = "支付宝个人商户分页", notes = "必填参数：Authorization")
    public Object page(AlipayPersonalVO alipayPersonalVO, PageVO pageVO) {
        return iAlipayPersonalService.page(alipayPersonalVO, pageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 16:30
     * @description: 批量绑定商户
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    @PostMapping("/batchBinding")
    @ApiOperation(value="批量绑定商户",notes="必填字段：id")
    public Object batchBinding(@RequestBody AlipayPersonalVO alipayPersonalVO){
        return iAlipayPersonalService.batchBinding(alipayPersonalVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 16:29
     * @description: 解绑
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    @PostMapping("/unbind")
    @ApiOperation(value="解绑",notes="必填字段：alipayMerchantId")
    public Object unbind(@RequestBody AlipayPersonalVO alipayPersonalVO){
        return iAlipayPersonalService.unbind(alipayPersonalVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 16:29
     * @description: 支付宝个人商户修改
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    @PostMapping("/update")
    @ApiOperation(value = "支付宝个人商户修改", notes = "必填参数：Authorization")
    public Object update(@RequestBody AlipayPersonalVO alipayPersonalVO) {
        return iAlipayPersonalService.update(alipayPersonalVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 16:29
     * @description: 支付宝个人商户新增
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    @PostMapping("/insert")
    @ApiOperation(value = "支付宝商户新增", notes = "必填参数：Authorization")
    public Object insert(@RequestBody AlipayPersonalVO alipayPersonalVO) {
        return iAlipayPersonalService.insert(alipayPersonalVO);
    }

}