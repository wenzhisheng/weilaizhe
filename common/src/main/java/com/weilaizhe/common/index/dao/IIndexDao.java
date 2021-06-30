package com.weilaizhe.common.index.dao;

import com.weilaizhe.common.pojo.index.IndexMoneyVO;
import com.weilaizhe.common.pojo.index.IndexOrderVO;
import com.weilaizhe.common.pojo.index.StrokeCountVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dameizi
 * @description 首页数据数据层
 * @dateTime 2019-04-17 13:47
 * @className com.weilaizhe.common.index.dao.IIndexDao
 */
public interface IIndexDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-05-22 22:48
     * @description: 当天收款总额、支付获利、总获利、累计收款、当前余额
     * @param: [merchantVO]
     * @return: com.weilaizhe.common.pojo.index.IndexMoneyVO
     */
    IndexMoneyVO getTodayMoney(@Param("vo") MerchantVO merchantVO);

    IndexOrderVO getTotalOrderEnter(@Param("vo") MerchantVO merchantVO);

    IndexOrderVO getTotalOrderOut(@Param("vo") MerchantVO merchantVO);

    List<StrokeCountVO> lastFifteenDaysOrder(@Param("vo") MerchantVO merchantVO);

}
