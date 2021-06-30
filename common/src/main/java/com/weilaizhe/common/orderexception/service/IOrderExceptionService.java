package com.weilaizhe.common.orderexception.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.orderexception.OrderExceptionVO;

/**
 * @author: dameizi
 * @description: 异常订单接口层
 * @dateTime 2019-04-04 19:28
 * @className com.weilaizhe.common.orderexception.service.IOrderExceptionService
 */
public interface IOrderExceptionService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-15 23:45
     * @description: 处理异常订单
     * @param: [exceptionOrderVO]
     * @return: int
     */
    int update(OrderExceptionVO exceptionOrderVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-15 23:45
     * @description: 删除异常订单
     * @param: [exceptionOrderVO]
     * @return: int
     */
    int delete(OrderExceptionVO exceptionOrderVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 19:53
     * @description: 分页查询异常订单
     * @param: [orderExceptionVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.orderexception.OrderExceptionVO>
     */
    IPage<OrderExceptionVO> page(OrderExceptionVO orderExceptionVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 19:38
     * @description: 提交异常订单
     * @param: [orderExceptionVO]
     * @return: int
     */
    int insert(OrderExceptionVO orderExceptionVO);

}
