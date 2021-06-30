package com.weilaizhe.common.merchanttradetype.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.pojo.merchanttradetype.MerchantTradeTypeVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

/**
 * @author: dameizi
 * @description: 商户交易类型数据层
 * @dateTime 2019-03-31 22:49
 * @className com.weilaizhe.common.merchanttradetype.dao.IMerchantTradeTypeDao
 */
public interface IMerchantTradeTypeDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-05-30 23:55
     * @description: 商户更新商户交易类型
     * @param: [merchantTradeTypeVO]
     * @return: int
     */
    int updateMerchantTradeType(@Param("vo") MerchantTradeTypeVO merchantTradeTypeVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-30 17:20
     * @description: 分页查询商户交易类型
     * @param: [page, merchantTradeTypeVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.merchanttradetype.MerchantTradeTypeVO>
     */
    IPage<MerchantTradeTypeVO> page(Page<MerchantTradeTypeVO> page, @Param("vo") MerchantTradeTypeVO merchantTradeTypeVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-31 13:03
     * @description: 商户交易类型列表
     * @param: [merchantTradeTypeVO]
     * @return: java.util.List<java.lang.String>
     */
    List<String> listMerchantTradeTypeName(@Param("vo") MerchantTradeTypeVO merchantTradeTypeVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 22:24
     * @description: 商户商户交易类型
     * @param: [merchantId]
     * @return: int
     */
    int delete(@Param("merchantId") Integer merchantId);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-30 15:54
     * @description: 配置商户交易类型
     * @param: [merchantReceiptTypeVO]
     * @return: int
     */
    int update(@Param("vo") MerchantTradeTypeVO merchantReceiptTypeVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 15:22
     * @description: 配置商户交易类型
     * @param: [merchantReceiptTypeVO]
     * @return: int
     */
    int insert(@Param("vo") MerchantTradeTypeVO merchantReceiptTypeVO);

}