package com.weilaizhe.admin.balancechange.controller;

import com.weilaizhe.common.balancechange.service.IBalanceChangeService;
import com.weilaizhe.common.pojo.balancechange.BalanceChangeVO;
import com.weilaizhe.common.pojo.base.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author dameizi
 * @description 资金明细管理
 * @dateTime 2019-04-16 16:04
 * @className BalanceChangController
 */
@RestController
@RequestMapping("/balanceChange")
@Api(value = "BalanceChange", tags = "BalanceChangeController", description = "资金明细管理")
public class BalanceChangeController {

    @Autowired
    private IBalanceChangeService iBalanceChangeService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 18:50
     * @description: 导出资金明细
     * @param: [balanceChangeVO, response]
     * @return: void
     */
    @GetMapping("/exportExcel")
    @ApiOperation(value = "导出资金明细", notes = "必填参数：token")
    public void exportExcel(BalanceChangeVO balanceChangeVO, HttpServletResponse response){
        iBalanceChangeService.exportExcel(balanceChangeVO, response);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 16:23
     * @description: 分页查询资金明细
     * @param: [balanceChangeVO, pageVO]
     * @return: java.lang.Object
     */
    @GetMapping("/page")
    @ApiOperation(value="分页查询资金明细",notes="必填参数：token")
    public Object page(BalanceChangeVO balanceChangeVO, PageVO pageVO){
        return iBalanceChangeService.page(balanceChangeVO, pageVO);
    }

}
