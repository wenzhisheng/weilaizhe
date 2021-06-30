package com.weilaizhe.common.alipaymerchant.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO;
import com.weilaizhe.common.pojo.wechatmerchant.WechatMerchantVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: dameizi
 * @description: 支付宝商户数据层
 * @dateTime 2019-04-07 12:31
 * @className com.weilaizhe.common.alipaymerchant.dao.IAlipayMerchantDao
 */
public interface IAlipayMerchantDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-09 17:22
     * @description: 更新当前支付宝商户收款额
     * @param: [alipayMerchantVO]
     * @return: int
     */
    int updateDailyTotalAmount(@Param("vo") AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 15:00
     * @description: 定时任务，每天凌晨更新未满
     * @param: []
     * @return: int
     */
    int updateStatus();

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 13:28
     * @description: 获取支付宝商户号（启用的未满的且最空闲的）
     * @param: [app_id]
     * @return: com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO
     */
    AlipayMerchantVO getIdleAlipayMerchant(@Param("app_id") Integer app_id, @Param("dailyLimit") BigDecimal dailyLimit);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:46
     * @description: 支付宝商户时间修改
     * @param: [alipayMerchantVO]
     * @return: int
     */
    int updateGmtModified(@Param("app_id") Integer app_id);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:46
     * @description: 支付宝商户列表
     * @param: [alipayMerchantVO]
     * @return: int
     */
    List<AlipayMerchantVO> list(@Param("vo") AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-14 20:49
     * @description: 获取支付宝商户
     * @param: [alipayMerchantVO]
     * @return: com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO
     */
    AlipayMerchantVO getAlipayMerchant(@Param("vo") AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:46
     * @description: 支付宝商户分页
     * @param: [alipayMerchantVO]
     * @return: int
     */
    IPage<AlipayMerchantVO> page(Page<AlipayMerchantVO> page, @Param("vo") AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:46
     * @description: 支付宝商户删除
     * @param: [alipayMerchantVO]
     * @return: int
     */
    int delete(@Param("vo") AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 22:35
     * @description: 根据编码查询
     * @param: [alipayMerchantVO]
     * @return: com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO
     */
    AlipayMerchantVO getAlipayMerchantByCode(@Param("vo") AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 23:03
     * @description: 批量获取列表
     * @param: [alipayMerchantVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.alipayMerchant.AlipayMerchantVO>
     */
    List<AlipayMerchantVO> batchList(@Param("vo") AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:17
     * @description: 批量绑定商户
     * @param: [alipayMerchantVO]
     * @return: int
     */
    int batchBinding(@Param("vo") AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 18:46
     * @description: 解绑
     * @param: [alipayMerchantVO]
     * @return: int
     */
    int unbind(@Param("vo") AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:46
     * @description: 支付宝商户更新
     * @param: [alipayMerchantVO]
     * @return: int
     */
    int update(@Param("vo") AlipayMerchantVO alipayMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:46
     * @description: 支付宝商户
     * @param: [alipayMerchantVO]
     * @return: int
     */
    int insert(@Param("vo") AlipayMerchantVO alipayMerchantVO);

}
