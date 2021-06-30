package com.weilaizhe.merchant.orderexception.controller;

import com.weilaizhe.common.orderexception.service.IOrderExceptionService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.orderexception.OrderExceptionVO;
import com.weilaizhe.common.util.CommonUtil;
import com.weilaizhe.common.util.RedissonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: dameizi
 * @description: 异常订单
 * @dateTime 2019-04-04 19:25
 * @className com.weilaizhe.merchant.orderexception.controller.OrderExceptionController
 */
@RestController
@RequestMapping("/orderException")
@Api(value = "OrderExceptionController", tags = "OrderExceptionController", description = "异常订单")
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
    @ApiOperation(value = "分页查询异常订单", notes = "必填参数：Authorization，属性，分页")
    public Object page(OrderExceptionVO orderExceptionVO, PageVO pageVO){
        orderExceptionVO.setMerchantId(RedissonUtil.getContextMerchantInfo().getMerchantId());
        return iOrderExceptionService.page(orderExceptionVO, pageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 19:35
     * @description: 提交异常订单
     * @param: [orderExceptionVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "提交异常订单", notes = "必填参数：Authorization，订单号，金额，时间")
    public Object insert(@RequestBody OrderExceptionVO orderExceptionVO){
        return iOrderExceptionService.insert(orderExceptionVO);
    }

}
