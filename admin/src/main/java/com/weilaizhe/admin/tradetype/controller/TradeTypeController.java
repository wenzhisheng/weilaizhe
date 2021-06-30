package com.weilaizhe.admin.tradetype.controller;

import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.tradetype.TradeTypeVO;
import com.weilaizhe.common.tradetype.service.ITradeTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dameizi
 * @description 交易类型管理
 * @dateTime 2019-04-16 23:39
 * @className com.weilaizhe.admin.tradetype.controller.TradeTypeController
 */
@RestController
@RequestMapping(value = "/tradeType")
@Api(value="TradeTypeController", tags="TradeType", description="交易类型管理")
public class TradeTypeController {

    @Autowired
    private ITradeTypeService iTradeTypeService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 12:32
     * @description: 分页查询交易类型
     * @param: [receiptTypeVO, pageVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询交易类型", notes = "必填参数：Authorization，分页")
    public Object pageTradeType(TradeTypeVO receiptTypeVO, PageVO pageVO){
        return iTradeTypeService.pageTradeType(receiptTypeVO,pageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 23:48
     * @description: 列表查询交易类型名称
     * @param: []
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value="列表查询交易类型", notes="无需参数")
    public Object listTradeTypeName(){
        return iTradeTypeService.listTradeTypeName();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 23:28
     * @description: 删除交易类型
     * @param: [receiptTypeVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation(value = "删除交易类型", notes = "必填参数：交易类型ID")
    public Object delete(TradeTypeVO receiptTypeVO){
        return iTradeTypeService.delete(receiptTypeVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 17:07
     * @description: 更新交易类型
     * @param: [receiptTypeVO, file]
     * @return: java.lang.Object
     */
    @PostMapping("/update")
    @ApiOperation(value="更新交易类型",notes="必填参数：交易类型ID")
    public Object update(@RequestParam(value = "file", required = false) MultipartFile file, TradeTypeVO receiptTypeVO) {
        return iTradeTypeService.update(receiptTypeVO, file);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 13:29
     * @description: 新增交易类型
     * @param: [receiptTypeVO, file]
     * @return: java.lang.Object
     */
    @PostMapping(value = "/insert")
    @ApiOperation(value="新增交易类型",notes="必填字段：交易类型编码、交易类型名称、交易类型排序字段、是否推荐 0:未推荐 1:推荐、是否启用 0:未启用 1:已启用")
    public Object insert(@RequestParam(value = "file", required = false) MultipartFile file, TradeTypeVO receiptTypeVO) {
        return iTradeTypeService.insert(file, receiptTypeVO);
    }

}
