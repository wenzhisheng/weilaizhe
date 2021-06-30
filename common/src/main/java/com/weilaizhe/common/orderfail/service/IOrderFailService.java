package com.weilaizhe.common.orderfail.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.orderfail.OrderFailVO;

/**
 * @author: dameizi
 * @description: 失败订单接口层
 * @dateTime 2019-04-16 12:23
 * @className com.weilaizhe.common.orderfail.service.IOrderFailService
 */
public interface IOrderFailService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:16
     * @description: 更新失败订单
     * @param: [orderFailVO]
     * @return: int
     */
    int update(OrderFailVO orderFailVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:16
     * @description: 删除失败订单
     * @param: [orderFailVO]
     * @return: int
     */
    int delete(OrderFailVO orderFailVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:17
     * @description: 分页查询失败订单
     * @param: [orderFailVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.orderfail.OrderFailVO>
     */
    IPage<OrderFailVO> page(OrderFailVO orderFailVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:13
     * @description: 新增失败订单
     * @param: [orderFailVO]
     * @return: int
     */
    int insert(OrderFailVO orderFailVO);

}
