package com.weilaizhe.merchant.alipaymerchant.controller;

import com.weilaizhe.common.alipaymerchant.service.IAlipayMerchantService;
import com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.util.CommonUtil;
import com.weilaizhe.common.util.RedissonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: dameizi
 * @description: 支付宝商户
 * @dateTime 2019-04-07 14:10
 * @className com.weilaizhe.merchant.alipaymerchant.controller.AlipayMerchantController
 */
@RestController
@RequestMapping(value = "/alipayMerchant")
@Api(value = "AlipayMerchantController", tags = "AlipayMerchantController", description = "支付宝商户")
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
    @ApiOperation(value="支付宝商户分页查询",notes="必填参数：Authorization")
    public Object page(AlipayMerchantVO alipayMerchantVO, PageVO pageVO){
        alipayMerchantVO.setMerchantId(RedissonUtil.getContextMerchantInfo().getMerchantId());
        return iAlipayMerchantService.page(alipayMerchantVO,pageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 15:05
     * @description: 支付宝商户新增
     * @param: [alipayMerchantVO]
     * @return: java.lang.Object
     */
    @PostMapping("/insert")
    @ApiOperation(value="支付宝商户新增",notes="必填参数：Authorization")
    public Object insert(@RequestBody AlipayMerchantVO alipayMerchantVO){
        MerchantVO merchantInfo = RedissonUtil.getContextMerchantInfo();
        alipayMerchantVO.setMerchantName(merchantInfo.getMerchantName());
        alipayMerchantVO.setMerchantId(merchantInfo.getMerchantId());
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
    @ApiOperation(value="支付宝商户修改",notes="必填参数：Authorization")
    public Object update(@RequestBody AlipayMerchantVO alipayMerchantVO){
        alipayMerchantVO.setMerchantId(RedissonUtil.getContextMerchantInfo().getMerchantId());
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
    @ApiOperation(value="支付宝商户删除",notes="必填参数：Authorization")
    public Object delete(AlipayMerchantVO alipayMerchantVO){
        alipayMerchantVO.setMerchantId(RedissonUtil.getContextMerchantInfo().getMerchantId());
        return iAlipayMerchantService.delete(alipayMerchantVO);
    }

}
