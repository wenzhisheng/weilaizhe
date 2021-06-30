package com.weilaizhe.common.merchantbalance.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.pojo.merchantbalance.MerchantBalanceVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author: dameizi
 * @description: 商户余额数据层
 * @dateTime 2019-03-31 22:49
 * @className com.weilaizhe.common.merchantbalance.dao.IMerchantBalanceDao
 */
public interface IMerchantBalanceDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 23:38
     * @description: 分页条件查询
     * @param: [page, merchantBalanceVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.merchantbalance.MerchantBalanceVO>
     */
    IPage<MerchantBalanceVO> page(Page<MerchantBalanceVO> page, @Param("vo") MerchantBalanceVO merchantBalanceVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 23:51
     * @description: 更新商户余额
     * @param: [merchantBalanceVO]
     * @return: int
     */
    int update(@Param("vo") MerchantBalanceVO merchantBalanceVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 22:42
     * @description: 获取商户余额
     * @param: [merchantBalanceVO]
     * @return: com.weilaizhe.common.pojo.merchantbalance.MerchantBalanceVO
     */
    MerchantBalanceVO getMerchantBalance(@Param("vo") MerchantBalanceVO merchantBalanceVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-03-31 23:27
     * @description: 新增商户余额
     * @param: [merchantBalanceVO]
     * @return: int
     */
    int insert(@Param("vo") MerchantBalanceVO merchantBalanceVO);

}