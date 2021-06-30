package com.weilaizhe.common.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;

/**
 * @author: dameizi
 * @description: 支付订单接口层
 * @dateTime 2019-04-04 13:55
 * @className com.weilaizhe.common.order.service.IPaymentOrderService
 */
public interface IPaymentOrderService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-10 0:27
     * @description: 获取订单
     * @param: [paymentOrderVO, tableKey]
     * @return: com.weilaizhe.common.pojo.order.PaymentOrderVO
     */
    PaymentOrderVO getOrder(PaymentOrderVO paymentOrderVO, String tableKey);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-06 17:33
     * @description: 商户订单重复排查
     * @param: [out_trade_no]
     * @return: int
     */
    int repeatOrderSubmission(String out_trade_no, Integer app_id);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:21
     * @description: 订单更新
     * @param: [paymentOrderVO, tableKey]
     * @return: int
     */
    int updateOrder(PaymentOrderVO paymentOrderVO, String tableKey);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:06
     * @description: 订单分页查询
     * @param: [orderVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.order.PaymentOrderVO>
     */
    IPage<PaymentOrderVO> pageOrder(PaymentOrderVO orderVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:09
     * @description: 新增订单
     * @param: [paymentOrderVO, tableKey]
     * @return: int
     */
    int insertOrder(PaymentOrderVO paymentOrderVO, String tableKey);

}
