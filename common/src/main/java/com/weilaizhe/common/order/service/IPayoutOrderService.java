package com.weilaizhe.common.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.order.PayoutOrderVO;

/**
 * @author: dameizi
 * @description: 支出订单接口层
 * @dateTime 2019-04-04 13:55
 * @className com.weilaizhe.common.order.service.IPayoutOrderService
 */
public interface IPayoutOrderService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-06 17:33
     * @description: 商户订单重复排查
     * @param: [out_trade_no, auth_code]
     * @return: int
     */
    int repeatOrderSubmission(String out_trade_no, String auth_code);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:21
     * @description: 订单更新
     * @param: [paymentOrderVO]
     * @return: int
     */
    int updateOrder(PayoutOrderVO payoutOrderVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:06
     * @description: 订单分页查询
     * @param: [orderVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.order.PayoutOrderVO>
     */
    IPage<PayoutOrderVO> pageOrder(PayoutOrderVO payoutOrderVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:09
     * @description: 新增订单
     * @param: [paymentOrderVO, tableKey]
     * @return: int
     */
    int insertOrder(PayoutOrderVO payoutOrderVO, String tableKey);

}
