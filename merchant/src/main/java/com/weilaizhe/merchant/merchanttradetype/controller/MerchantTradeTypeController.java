package com.weilaizhe.merchant.merchanttradetype.controller;

import com.weilaizhe.common.merchanttradetype.service.IMerchantTradeTypeService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchanttradetype.MerchantTradeTypeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dameizi
 * @description 商户交易类型
 * @dateTime 2019-05-30 17:14
 * @className com.weilaizhe.merchant.merchanttradetype.controller.MerchantTradeTypeController
 */
@RestController
@RequestMapping("/merchantTradeType")
@Api(value = "MerchantTradeType", tags = "MerchantTradeTypeController", description = "商户交易类型")
public class MerchantTradeTypeController {

    @Autowired
    private IMerchantTradeTypeService iMerchantTradeTypeService;

    /**
     * @author: dameizi
     * @dateTime: 2019-05-30 23:48
     * @description: 更新商户交易类型
     * @param: [merchantTradeTypeVO]
     * @return: java.lang.Object
     */
    @PostMapping(value = "/update")
    @ApiOperation(value="更新商户交易类型",notes="必填参数：商户交易类型ID")
    public Object update(@RequestBody MerchantTradeTypeVO merchantTradeTypeVO) {
        return iMerchantTradeTypeService.update(merchantTradeTypeVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-30 17:16
     * @description: 分页查询商户交易类型
     * @param: [merchantWithdrawVO, pageVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询商户交易类型", notes = "必填参数：Authorization，分页")
    public Object page(MerchantTradeTypeVO merchantTradeTypeVO, PageVO pageVO){
        return iMerchantTradeTypeService.page(merchantTradeTypeVO, pageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-31 13:09
     * @description: 商户交易类型列表名称
     * @param: [merchantTradeTypeVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/listName", method = RequestMethod.GET)
    @ApiOperation(value="列表查询商户交易类型",notes="无需参数")
    public Object listMerchantTradeTypeName(MerchantTradeTypeVO merchantTradeTypeVO){
        return iMerchantTradeTypeService.listMerchantTradeTypeName(merchantTradeTypeVO);
    }

}
