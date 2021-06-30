package com.weilaizhe.common.index.service;

import com.weilaizhe.common.pojo.index.IndexMoneyVO;
import com.weilaizhe.common.pojo.index.IndexOrderVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;

/**
 * @author dameizi
 * @description 首页数据接口层
 * @dateTime 2019-04-17 13:48
 * @className com.weilaizhe.common.index.service.IIndexService
 */
public interface IIndexService {

    /**
     * @author: dameizi
     * @dateTime: 2019-05-22 22:47
     * @description: 当天收款总额、支付获利、总获利、累计收款、当前余额
     * @param: [merchantVO]
     * @return: com.weilaizhe.common.pojo.index.IndexMoneyVO
     */
    IndexMoneyVO getTodayMoney(MerchantVO merchantVO);

    IndexOrderVO getTotalOrder(MerchantVO merchantVO);

}
