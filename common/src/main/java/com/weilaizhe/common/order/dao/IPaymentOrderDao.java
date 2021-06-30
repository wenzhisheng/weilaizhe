package com.weilaizhe.common.order.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author: dameizi
 * @description: 支付订单数据层
 * @dateTime 2019-04-04 13:54
 * @className com.weilaizhe.common.order.dao.IPaymentOrderDao
 */
public interface IPaymentOrderDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-06-05 13:21
     * @description: 获取订单
     * @param: [paymentOrderVO, tableKey]
     * @return: com.weilaizhe.common.pojo.order.PaymentOrderVO
     */
    PaymentOrderVO getPaymentOrder(@Param("vo") PaymentOrderVO paymentOrderVO, @Param("tableKey")String tableKey);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-06 17:36
     * @description: 商户订单重复排查
     * @param: [out_trade_no]
     * @return: int
     */
    int repeatOrderSubmission(@Param("vo") PaymentOrderVO paymentOrderVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:27
     * @description: 订单更新
     * @param: [tableKey, paymentOrderVO]
     * @return: int
     */
    int updateOrder(@Param("vo") PaymentOrderVO paymentOrderVO, @Param("tableKey")String tableKey);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:06
     * @description: 订单分页查询
     * @param: [page, orderVO, tableSet]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.order.PaymentOrderVO>
     */
    IPage<PaymentOrderVO> pageOrder(Page<PaymentOrderVO> page, @Param("vo") PaymentOrderVO orderVO, @Param("list") Set<String> tableSet);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:08
     * @description: 新增订单
     * @param: [paymentOrderVO]
     * @return: int
     */
    int insertOrder(@Param("vo") PaymentOrderVO paymentOrderVO, @Param("tableKey")String tableKey);

}
