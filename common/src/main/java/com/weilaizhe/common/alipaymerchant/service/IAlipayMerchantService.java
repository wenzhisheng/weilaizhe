package com.weilaizhe.common.alipaymerchant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO;
import com.weilaizhe.common.pojo.base.PageVO;

import java.util.List;

/**
 * @author: dameizi
 * @description: 支付宝商户接口层
 * @dateTime 2019-04-07 12:31
 * @className com.weilaizhe.common.alipaymerchant.service.IAlipayMerchantService
 */
public interface IAlipayMerchantService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-09 17:22
     * @description: 更新当前支付宝商户收款额
     * @param: [passbackParams, totalAmount]
     * @return: int
     */
    int updateDailyTotalAmount(String passbackParams, String totalAmount);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:57
     * @description: 定时任务，每天凌晨更新未满
     * @param: []
     * @return: int
     */
    int updateStatus();
    
    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 13:28
     * @description: 获取支付宝商户号（启用的未满的且最空闲的）
     * @param: [app_id, tradeType]
     * @return: com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO
     */
    AlipayMerchantVO getIdleAlipayMerchant(Integer app_id, String tradeType);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:45
     * @description: 支付宝商户列表
     * @param: [alipayMerchantVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO>
     */
    List<AlipayMerchantVO> list(AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-14 20:48
     * @description: 获取支付宝商户
     * @param: [alipayMerchantVO]
     * @return: com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO
     */
    AlipayMerchantVO getAlipayMerchant(AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:45
     * @description: 支付宝商户分页
     * @param: [alipayMerchantVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO>
     */
    IPage<AlipayMerchantVO> page(AlipayMerchantVO alipayMerchantVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:45
     * @description: 支付宝商户删除
     * @param: [alipayMerchantVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO>
     */
    int delete(AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:17
     * @description: 批量绑定商户
     * @param: [alipayMerchantVO]
     * @return: int
     */
    int batchBinding(AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 18:43
     * @description: 解绑
     * @param: [alipayMerchantVO]
     * @return: int
     */
    int unbind(AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:45
     * @description: 支付宝商户更新
     * @param: [alipayMerchantVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO>
     */
    int update(AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:45
     * @description: 支付宝商户新增
     * @param: [alipayMerchantVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO>
     */
    int insert(AlipayMerchantVO alipayMerchantVO);

}
