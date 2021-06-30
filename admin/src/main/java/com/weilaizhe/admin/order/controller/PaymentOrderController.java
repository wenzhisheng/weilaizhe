package com.weilaizhe.admin.order.controller;

import com.weilaizhe.common.order.service.IPaymentOrderService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dameizi
 * @description 支付订单管理
 * @dateTime 2019-04-17 11:19
 * @className com.weilaizhe.admin.order.controller.PaymentOrderController
 */
@RestController
@RequestMapping("/paymentOrder")
@Api(value = "PaymentOrderController", tags = "PaymentOrder", description = "支付订单管理")
public class PaymentOrderController {

    @Autowired
    private IPaymentOrderService iPaymentOrderService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 14:20
     * @description: 订单分页查询
     * @param: [orderVO, pageVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value="订单分页查询",notes="必填参数：Authorization")
    public Object pageOrder(PaymentOrderVO paymentOrderVO, PageVO pageVO){
        return iPaymentOrderService.pageOrder(paymentOrderVO, pageVO);
    }

}
