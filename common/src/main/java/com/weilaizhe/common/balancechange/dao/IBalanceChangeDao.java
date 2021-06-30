package com.weilaizhe.common.balancechange.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.pojo.balancechange.BalanceChangeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: dameizi
 * @description: 帐变数据层
 * @dateTime 2019-04-07 21:36
 * @className com.weilaizhe.common.balancechange.dao.IBalanceChangeDao
 */
public interface IBalanceChangeDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 21:00
     * @description: 列表获取资金明细
     * @param: [balanceChangeVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.balancechange.BalanceChangeVO>
     */
    List<BalanceChangeVO> list(@Param("vo") BalanceChangeVO balanceChangeVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 17:04
     * @description: 分页查询资金明细
     * @param: [page, balanceChangeVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.balancechange.BalanceChangeVO>
     */
    IPage<BalanceChangeVO> page(Page<BalanceChangeVO> page, @Param("vo") BalanceChangeVO balanceChangeVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 17:00
     * @description: 支付产生帐变
     * @param: [balanceChangeVO]
     * @return: int
     */
    int insertPayment(@Param("vo") BalanceChangeVO balanceChangeVO);

}
