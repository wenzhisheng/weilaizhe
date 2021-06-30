package com.weilaizhe.common.tradetype.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.pojo.tradetype.TradeTypeVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

/**
 * @author: dameizi
 * @description: 交易类型数据层
 * @dateTime 2019-04-01 18:00
 * @className com.weilaizhe.common.tradetype.dao.ITradeTypeDao
 */
public interface ITradeTypeDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 13:03
     * @description: 分页查询交易类型
     * @param: [page, receiptTypeVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.receipttype.ReceiptTypeVO>
     */
    IPage<TradeTypeVO> pageTradeType(Page<TradeTypeVO> page, @Param("vo") TradeTypeVO receiptTypeVO);
    
    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 23:45
     * @description: 列表查询交易类型名称
     * @param: []
     * @return: java.util.List<java.lang.String>
     */
    List<String> listTradeTypeName();

    /**
     * @author: dameizi
     * @dateTime: 2019-04-08 23:34
     * @description: 查询交易类型列表
     * @param: [tradeTypeVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.tradetype.TradeTypeVO>
     */
    List<TradeTypeVO> listTradeType(@Param("vo") TradeTypeVO tradeTypeVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 23:47
     * @description: 删除交易类型
     * @param: [receiptTypeId]
     * @return: int
     */
    int delete(@Param("receiptTypeId") Integer receiptTypeId);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 17:12
     * @description: 更新交易类型
     * @param: [receiptTypeVO, file]
     * @return: int
     */
    int update(@Param("vo") TradeTypeVO receiptTypeVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-01 22:12
     * @description: 新增交易类型
     * @param: [receiptTypeVO, file]
     * @return: int
     */
    int insert(@Param("vo") TradeTypeVO receiptTypeVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-24 21:22
     * @description: 获取交易类型
     * @param: [receiptTypeVO]
     * @return: com.weilaizhe.common.pojo.tradetype.TradeTypeVO
     */
    TradeTypeVO getTradeTypeRepeat(@Param("vo") TradeTypeVO receiptTypeVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 15:10
     * @description: 根据交易类型名称查询
     * @param: [receiptTypeVO]
     * @return: com.weilaizhe.common.pojo.receipttype.ReceiptTypeVO
     */
    TradeTypeVO getReceiptTypeByName(@Param("vo") TradeTypeVO receiptTypeVO);

}
