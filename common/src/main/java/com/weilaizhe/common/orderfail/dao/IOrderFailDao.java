package com.weilaizhe.common.orderfail.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.pojo.orderfail.OrderFailVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author: dameizi
 * @description: 失败订单数据层
 * @dateTime 2019-04-16 12:23
 * @className com.weilaizhe.common.orderfail.dao.IOrderFailDao
 */
public interface IOrderFailDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:16
     * @description: 更新失败订单
     * @param: [orderFailVO]
     * @return: int
     */
    int update(@Param("vo") OrderFailVO orderFailVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:16
     * @description: 删除失败订单
     * @param: [orderFailVO]
     * @return: int
     */
    int delete(@Param("vo") OrderFailVO orderFailVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:14
     * @description: 分页查询失败订单
     * @param: [page, orderFailVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.orderfail.OrderFailVO>
     */
    IPage<OrderFailVO> page(Page<OrderFailVO> page, @Param("vo") OrderFailVO orderFailVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:13
     * @description: 新增失败订单
     * @param: [orderFailVO]
     * @return: int
     */
    int insert(@Param("vo") OrderFailVO orderFailVO);

}
