package com.weilaizhe.merchant.order.controller;

import com.weilaizhe.common.order.service.IPayoutOrderService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.order.PayoutOrderVO;
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
 * @description: 支出订单
 * @dateTime 2019-04-04 14:59
 * @className com.weilaizhe.merchant.order.controller.PayoutOrderController
 */
@RestController
@RequestMapping("/payoutOrder")
@Api(value = "PayoutOrderController", tags = "PayoutOrderController", description = "支出订单")
public class PayoutOrderController {

    @Autowired
    private IPayoutOrderService iPayoutOrderService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:21
     * @description: 更新订单
     * @param: [paymentOrderVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新订单", notes = "必填参数：Authorization，订单ID，订单号")
    public Object updateOrder(@RequestBody PayoutOrderVO payoutOrderVO){
        payoutOrderVO.setMerchantCode(RedissonUtil.getContextMerchantInfo().getMerchantCode());
        return iPayoutOrderService.updateOrder(payoutOrderVO);
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
    public Object pageOrder(@RequestBody PayoutOrderVO payoutOrderVO, PageVO pageVO){
        payoutOrderVO.setMerchantCode(RedissonUtil.getContextMerchantInfo().getMerchantCode());
        return iPayoutOrderService.pageOrder(payoutOrderVO, pageVO);
    }

}
