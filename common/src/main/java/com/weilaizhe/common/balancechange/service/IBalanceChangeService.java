package com.weilaizhe.common.balancechange.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weilaizhe.common.pojo.balancechange.BalanceChangeVO;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: dameizi
 * @description: 帐变接口层
 * @dateTime 2019-04-07 21:36
 * @className com.weilaizhe.common.balancechange.service.IBalanceChangeService
 */
public interface IBalanceChangeService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 22:00
     * @description: 导出资金明细
     * @param: [balanceChangeVO, response]
     * @return: java.lang.Object
     */
    void exportExcel(BalanceChangeVO balanceChangeVO, HttpServletResponse response);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 21:00
     * @description: 列表获取资金明细
     * @param: [balanceChangeVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.balancechange.BalanceChangeVO>
     */
    List<BalanceChangeVO> list(BalanceChangeVO balanceChangeVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 17:04
     * @description: 分页查询资金明细
     * @param: [balanceChangeVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.balancechange.BalanceChangeVO>
     */
    IPage<BalanceChangeVO> page(BalanceChangeVO balanceChangeVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 22:28
     * @description: 写帐变
     * @param: [payOrder, merchantVO]
     * @return: int
     */
    int insertPayment(PaymentOrderVO payOrder, MerchantVO merchantVO);

}
