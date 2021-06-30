package com.weilaizhe.merchant.merchantwithdraw.controller;

import com.weilaizhe.common.merchantwithdraw.service.IMerchantWithdrawService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchantwithdraw.MerchantWithdrawVO;
import com.weilaizhe.common.util.RedissonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dameizi
 * @description 商户提现
 * @dateTime 2019-04-16 15:52
 * @className com.weilaizhe.merchant.merchantwithdraw.controller.MerchantWithdrawController
 */
@RestController
@RequestMapping("/withdraw")
@Api(value = "MerchantWithdraw", tags = "MerchantWithdrawController", description = "商户提现")
public class MerchantWithdrawController {

    @Autowired
    private IMerchantWithdrawService iMerchantWithdrawService;

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
        merchantWithdrawVO.setMerchantId(RedissonUtil.getContextMerchantInfo().getMerchantId());
        return iMerchantWithdrawService.page(merchantWithdrawVO, pageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 15:54
     * @description: 申请提现
     * @param: [merchantWithdrawVO]
     * @return: java.lang.Object
     */
    @PostMapping(value = "/insert")
    @ApiOperation(value = "申请提现", notes = "必填参数：银行名称，银行卡，收款人")
    public Object insert(@RequestBody MerchantWithdrawVO merchantWithdrawVO){
        return iMerchantWithdrawService.insert(merchantWithdrawVO);
    }

}
