package com.weilaizhe.common.merchanttradetype.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchanttradetype.MerchantTradeTypeVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

/**
 * @author: dameizi
 * @description: 商户交易类型接口层
 * @dateTime 2019-03-31 22:49
 * @className com.weilaizhe.common.merchanttradetype.service.IMerchantTradeTypeService
 */
public interface IMerchantTradeTypeService {

    Object update(MerchantTradeTypeVO merchantTradeTypeVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-30 17:18
     * @description: 分页查询商户交易类型
     * @param: [merchantTradeTypeVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.merchanttradetype.MerchantTradeTypeVO>
     */
    IPage<MerchantTradeTypeVO> page(MerchantTradeTypeVO merchantTradeTypeVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-31 13:00
     * @description: 商户交易类型列表
     * @param: [merchantTradeTypeVO]
     * @return: java.util.List<java.lang.String>
     */
    List<String> listMerchantTradeTypeName(MerchantTradeTypeVO merchantTradeTypeVO);

}