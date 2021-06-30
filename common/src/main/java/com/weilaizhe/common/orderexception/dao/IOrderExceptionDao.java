package com.weilaizhe.common.orderexception.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.pojo.orderexception.OrderExceptionVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author: dameizi
 * @description: 异常订单数据层
 * @dateTime 2019-04-04 19:28
 * @className com.weilaizhe.common.orderexception.dao.IOrderExceptionDao
 */
public interface IOrderExceptionDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-15 23:50
     * @description: 处理异常订单
     * @param: [exceptionOrderVO]
     * @return: int
     */
    int update(@Param("vo") OrderExceptionVO exceptionOrderVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-15 23:50
     * @description: 删除异常订单
     * @param: [exceptionOrderVO]
     * @return: int
     */
    int delete(@Param("vo") OrderExceptionVO exceptionOrderVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 19:56
     * @description: 分页查询异常订单
     * @param: [page, orderExceptionVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.orderexception.OrderExceptionVO>
     */
    IPage<OrderExceptionVO> page(Page<OrderExceptionVO> page, @Param("vo") OrderExceptionVO orderExceptionVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 19:38
     * @description: 提交异常订单
     * @param: [orderExceptionVO]
     * @return: int
     */
    int insert(@Param("vo") OrderExceptionVO orderExceptionVO);

}
