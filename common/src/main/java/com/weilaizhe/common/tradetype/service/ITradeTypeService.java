package com.weilaizhe.common.tradetype.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.tradetype.TradeTypeVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: dameizi
 * @description: 交易类型接口层
 * @dateTime 2019-04-01 18:00
 * @className com.weilaizhe.common.tradetype.service.ITradeTypeService
 */
public interface ITradeTypeService {

    /**
     * @author: dameizi
     * @dateTime: 2019-06-01 16:37
     * @description: 查询交易类型列表
     * @param: [tradeTypeVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.tradetype.TradeTypeVO>
     */
    List<TradeTypeVO> listTradeType(TradeTypeVO tradeTypeVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 12:58
     * @description: 分页查询交易类型
     * @param: [receiptTypeVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.receipttype.ReceiptTypeVO>
     */
    IPage<TradeTypeVO> pageTradeType(TradeTypeVO receiptTypeVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-08 23:33
     * @description: 列表查询交易类型
     * @param: []
     * @return: java.util.List<java.lang.String>
     */
    List<String> listTradeTypeName();

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 23:29
     * @description: 删除交易类型
     * @param: [receiptTypeVO]
     * @return: int
     */
    int delete(TradeTypeVO receiptTypeVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 17:12
     * @description: 更新交易类型
     * @param: [receiptTypeVO, file]
     * @return: int
     */
    int update(TradeTypeVO receiptTypeVO, MultipartFile file) ;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-01 22:12
     * @description: 新增交易类型
     * @param: [receiptTypeVO, file]
     * @return: int
     */
    int insert(MultipartFile file, TradeTypeVO receiptTypeVO);

}
