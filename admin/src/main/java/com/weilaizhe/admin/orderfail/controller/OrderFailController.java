package com.weilaizhe.admin.orderfail.controller;

import com.weilaizhe.common.orderfail.service.IOrderFailService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.orderfail.OrderFailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: dameizi
 * @description: 失败订单
 * @dateTime 2019-04-16 12:20
 * @className com.weilaizhe.admin.orderfail.controller.OrderFailController
 */
@RestController
@RequestMapping("/orderFail")
@Api(value = "OrderFail Controller", tags = "OrderFailController", description = "失败订单")
public class OrderFailController {

    @Autowired
    private IOrderFailService iOrderFailService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:28
     * @description: 更新失败订单
     * @param: [payCallbackFailVo]
     * @return: java.lang.Object
     */
    @PostMapping("/update")
    @ApiOperation(value="更新失败订单",notes="必填参数：id")
    public Object updatePayState(@RequestBody OrderFailVO orderFailVO) {
        return iOrderFailService.update(orderFailVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:27
     * @description: 删除失败订单
     * @param: [orderFailVO]
     * @return: java.lang.Object
     */
    @GetMapping("/delete")
    @ApiOperation(value="删除失败订单",notes="必填参数：ids")
    public Object delete(OrderFailVO orderFailVO){
        return iOrderFailService.delete(orderFailVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:26
     * @description: 分页查询失败订单
     * @param: [orderFailVO, pageVO]
     * @return: java.lang.Object
     */
    @GetMapping("/page")
    @ApiOperation(value="分页查询失败订单",notes="必填参数：token")
    public Object page(OrderFailVO orderFailVO, PageVO pageVO){
        return iOrderFailService.page(orderFailVO, pageVO);
    }

}
