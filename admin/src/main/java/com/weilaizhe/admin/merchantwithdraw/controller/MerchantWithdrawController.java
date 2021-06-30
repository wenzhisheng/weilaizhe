package com.weilaizhe.admin.merchantwithdraw.controller;

import com.weilaizhe.common.merchantwithdraw.service.IMerchantWithdrawService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchantwithdraw.MerchantWithdrawVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: dameizi
 * @description: 商户提现管理
 * @dateTime 2019-04-16 13:42
 * @className com.weilaizhe.admin.merchantwithdraw.controller.MerchantWithdrawController
 */
@RestController
@RequestMapping("/withdraw")
@Api(value = "MerchantWithdraw", tags = "MerchantWithdrawController", description = "商户提现管理")
public class MerchantWithdrawController {

    @Autowired
    private IMerchantWithdrawService iMerchantWithdrawService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-15 23:43
     * @description: 提现审核
     * @param: [merchantWithdrawVO]
     * @return: java.lang.Object
     */
    @PostMapping("/update")
    @ApiOperation(value = "提现审核",notes = "必填参数：id")
    public Object update(@RequestBody MerchantWithdrawVO merchantWithdrawVO){
        return iMerchantWithdrawService.update(merchantWithdrawVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 15:51
     * @description: 分页查询商户提现
     * @param: [merchantWithdrawVO, pageVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询商户提现", notes = "必填参数：Authorization，分页")
    public Object page(MerchantWithdrawVO merchantWithdrawVO, PageVO pageVO){
        return iMerchantWithdrawService.page(merchantWithdrawVO, pageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 15:50
     * @description: 删除商户提现
     * @param: [merchantWithdrawVO]
     * @return: java.lang.Object
     */
    @GetMapping("/delete")
    @ApiOperation(value = "删除商户提现",notes = "必填参数：ids")
    public Object delete(MerchantWithdrawVO merchantWithdrawVO){
        return iMerchantWithdrawService.delete(merchantWithdrawVO);
    }

}
