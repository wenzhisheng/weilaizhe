package com.weilaizhe.common.merchantbalance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchantbalance.MerchantBalanceVO;

/**
 * @author: dameizi
 * @description: 商户余额接口层
 * @dateTime 2019-03-31 22:49
 * @className com.weilaizhe.common.merchantbalance.service.IMerchantBalanceService
 */
public interface IMerchantBalanceService {

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 23:33
     * @description: 分页条件查询
     * @param: [merchantBalanceVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.merchantbalance.MerchantBalanceVO>
     */
    IPage<MerchantBalanceVO> page(MerchantBalanceVO merchantBalanceVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-12 15:24
     * @description: 获取商户余额
     * @param: [merchantBalanceVO]
     * @return: com.weilaizhe.common.pojo.merchantbalance.MerchantBalanceVO
     */
    MerchantBalanceVO getMerchantBalance(MerchantBalanceVO merchantBalanceVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-03-31 23:27
     * @description: 新增商户余额
     * @param: [merchantBalanceVO]
     * @return: int
     */
    int insert(MerchantBalanceVO merchantBalanceVO);

}