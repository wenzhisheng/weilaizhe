package com.weilaizhe.merchant.orderfail.controller;

import com.weilaizhe.common.orderfail.service.IOrderFailService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.orderfail.OrderFailVO;
import com.weilaizhe.common.util.RedissonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: dameizi
 * @description: 失败订单
 * @dateTime 2019-05-29 12:20
 * @className com.weilaizhe.merchant.orderfail.controller.OrderFailController
 */
@RestController
@RequestMapping("/orderFail")
@Api(value = "OrderFail Controller", tags = "OrderFailController", description = "失败订单")
public class OrderFailController {

    @Autowired
    private IOrderFailService iOrderFailService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:26
     * @description: 分页查询失败订单
     * @param: [orderFailVO, pageVO]
     * @return: java.lang.Object
     */
    @GetMapping("/page")
    @ApiOperation(value="分页查询失败订单",notes="必填参数：Authorization")
    public Object page(OrderFailVO orderFailVO, PageVO pageVO){
        orderFailVO.setMerchantId(RedissonUtil.getContextMerchantInfo().getMerchantId());
        return iOrderFailService.page(orderFailVO, pageVO);
    }

}
