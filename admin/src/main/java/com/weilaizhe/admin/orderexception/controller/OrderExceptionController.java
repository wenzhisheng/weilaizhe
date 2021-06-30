package com.weilaizhe.admin.orderexception.controller;

import com.weilaizhe.common.orderexception.service.IOrderExceptionService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.orderexception.OrderExceptionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: dameizi
 * @description: 异常订单管理
 * @dateTime 2019-04-15 23:41
 * @className com.weilaizhe.admin.orderexception.controller.OrderExceptionController
 */
@RestController
@RequestMapping("/orderException")
@Api(value = "OrderExceptionController", tags = "OrderExceptionController", description = "异常订单管理")
public class OrderExceptionController {

    @Autowired
    private IOrderExceptionService iOrderExceptionService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 19:53
     * @description: 分页查询异常订单
     * @param: [orderExceptionVO, pageVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询异常订单", notes = "必填参数：Authorization，分页")
    public Object page(OrderExceptionVO orderExceptionVO, PageVO pageVO){
        return iOrderExceptionService.page(orderExceptionVO, pageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-15 23:43
     * @description: 处理异常订单
     * @param: [exceptionOrderVO]
     * @return: java.lang.Object
     */
    @PostMapping("/update")
    @ApiOperation(value = "处理异常订单",notes = "必填参数：id")
    public Object update(@RequestBody OrderExceptionVO exceptionOrderVO){
        return iOrderExceptionService.update(exceptionOrderVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-15 23:43
     * @description: 删除异常订单
     * @param: [exceptionOrderVO]
     * @return: java.lang.Object
     */
    @GetMapping("/delete")
    @ApiOperation(value = "删除异常订单",notes = "必填参数：ids")
    public Object delete(OrderExceptionVO exceptionOrderVO){
        return iOrderExceptionService.delete(exceptionOrderVO);
    }

}