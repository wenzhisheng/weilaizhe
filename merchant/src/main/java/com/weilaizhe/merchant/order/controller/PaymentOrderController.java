package com.weilaizhe.merchant.order.controller;

import com.weilaizhe.common.order.service.IPaymentOrderService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
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
 * @description: 支付订单
 * @dateTime 2019-04-04 13:46
 * @className com.weilaizhe.merchant.order.controller.OrderController
 */
@RestController
@RequestMapping("/paymentOrder")
@Api(value = "PaymentOrderController", tags = "PaymentOrderController", description = "支付订单")
public class PaymentOrderController {

    @Autowired
    private IPaymentOrderService iPaymentOrderService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:21
     * @description: 更新订单
     * @param: [paymentOrderVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新订单", notes = "必填参数：Authorization，订单ID，平台订单号")
    public Object updateOrder(@RequestBody PaymentOrderVO paymentOrderVO){
        paymentOrderVO.setMerchantId(RedissonUtil.getContextMerchantInfo().getMerchantId());
        String tableKey = paymentOrderVO.getTradeNo().substring(0, 6);
        return iPaymentOrderService.updateOrder(paymentOrderVO, tableKey);
    }

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
        paymentOrderVO.setMerchantId(RedissonUtil.getContextMerchantInfo().getMerchantId());
        return iPaymentOrderService.pageOrder(paymentOrderVO, pageVO);
    }

}
