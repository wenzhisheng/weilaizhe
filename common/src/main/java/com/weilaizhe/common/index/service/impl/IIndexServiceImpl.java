package com.weilaizhe.common.index.service.impl;

import com.weilaizhe.common.index.dao.IIndexDao;
import com.weilaizhe.common.index.service.IIndexService;
import com.weilaizhe.common.pojo.index.IndexMoneyVO;
import com.weilaizhe.common.pojo.index.IndexOrderVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dameizi
 * @description 首页数据业务层
 * @dateTime 2019-04-17 13:48
 * @className com.weilaizhe.common.index.service.impl.IIndexServiceImpl
 */
@Service
public class IIndexServiceImpl implements IIndexService {

    @Autowired
    private IIndexDao iIndexDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-05-22 22:48
     * @description: 当天收款总额、支付获利、总获利、累计收款、当前余额
     * @param: [merchantVO]
     * @return: com.weilaizhe.common.pojo.index.IndexMoneyVO
     */
    @Override
    public IndexMoneyVO getTodayMoney(MerchantVO merchantVO) {
        return iIndexDao.getTodayMoney(merchantVO);
    }

    @Override
    public IndexOrderVO getTotalOrder(MerchantVO merchantVO) {
        // 支付今日订单量、累计订单量
        IndexOrderVO totalOrder = iIndexDao.getTotalOrderEnter(merchantVO);
        // 支出今日订单量、累计订单量
        IndexOrderVO totalOrderOut = iIndexDao.getTotalOrderOut(merchantVO);
        // 支付和支付相加
        totalOrder.setTodayOrder(totalOrder.getTodayOrder()+totalOrderOut.getTodayOrder());
        totalOrder.setTotalOrder(totalOrder.getTotalOrder()+totalOrderOut.getTotalOrder());
        // 获取过去15天订单量
        totalOrder.setLastFifteenDaysOrder(iIndexDao.lastFifteenDaysOrder(merchantVO));
        return totalOrder;
    }
}
