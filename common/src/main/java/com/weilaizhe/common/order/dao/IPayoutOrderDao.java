package com.weilaizhe.common.order.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
import com.weilaizhe.common.pojo.order.PayoutOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author: dameizi
 * @description: 支出订单数据层
 * @dateTime 2019-04-04 13:54
 * @className com.weilaizhe.common.order.dao.IPayoutOrderDao
 */
public interface IPayoutOrderDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-12 15:01
     * @description: 商户订单重复排查
     * @param: [payoutOrderVO]
     * @return: int
     */
    int repeatOrderSubmission(@Param("vo") PayoutOrderVO payoutOrderVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:27
     * @description: 订单更新
     * @param: [tableKey, payoutOrderVO]
     * @return: int
     */
    int updateOrder(@Param("vo") PayoutOrderVO payoutOrderVO, @Param("tableKey")String tableKey);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:06
     * @description: 订单分页查询
     * @param: [page, payoutOrderVO, tableSet]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.order.PaymentOrderVO>
     */
    IPage<PayoutOrderVO> pageOrder(Page<PayoutOrderVO> page, @Param("vo") PayoutOrderVO payoutOrderVO, @Param("list") Set<String> tableSet);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:08
     * @description: 新增订单
     * @param: [payoutOrderVO]
     * @return: int
     */
    int insertOrder(@Param("vo") PayoutOrderVO payoutOrderVO, @Param("tableKey")String tableKey);

}
