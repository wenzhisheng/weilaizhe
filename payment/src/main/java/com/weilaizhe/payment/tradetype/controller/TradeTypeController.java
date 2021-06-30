package com.weilaizhe.payment.tradetype.controller;

import com.weilaizhe.common.pojo.tradetype.TradeTypeVO;
import com.weilaizhe.common.tradetype.service.ITradeTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dameizi
 * @description 交易类型
 * @dateTime 2019-04-16 23:39
 * @className com.weilaizhe.admin.tradetype.controller.TradeTypeController
 */
@RestController
@RequestMapping(value = "/tradeType")
@Api(value="TradeTypeController", tags="TradeType", description="交易类型")
public class TradeTypeController {

    @Autowired
    private ITradeTypeService iTradeTypeService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 23:48
     * @description: 查询交易类型列表
     * @param: [tradeTypeVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value="查询交易类型列表", notes="无需参数")
    public Object listTradeType(TradeTypeVO tradeTypeVO){
        return iTradeTypeService.listTradeType(tradeTypeVO);
    }

}
