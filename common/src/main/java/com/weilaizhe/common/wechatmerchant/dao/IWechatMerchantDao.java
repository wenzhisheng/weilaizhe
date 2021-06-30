package com.weilaizhe.common.wechatmerchant.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.pojo.wechatmerchant.WechatMerchantVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: dameizi
 * @description: 微信商户数据层
 * @dateTime 2019-04-10 23:09
 * @className com.weilaizhe.common.wechatmerchant.dao.IWechatMerchantDao
 */
public interface IWechatMerchantDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 15:54
     * @description: 根据编码查询微信商户
     * @param: [wechatMerchantVO]
     * @return: com.weilaizhe.common.pojo.wechatmerchant.WechatMerchantVO
     */
    WechatMerchantVO getWechatMerchantByCode(@Param("vo") WechatMerchantVO wechatMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:17
     * @description: 分页查询微信商户
     * @param: [wechatMerchantVO]
     * @return: int
     */
    IPage<WechatMerchantVO> pageWechatMerchant(Page<WechatMerchantVO> page, @Param("vo") WechatMerchantVO wechatMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:17
     * @description: 删除微信商户
     * @param: [wechatMerchantVO]
     * @return: int
     */
    int delete(@Param("vo") WechatMerchantVO wechatMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 23:03
     * @description: 批量获取列表
     * @param: [wechatMerchantVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.wechatmerchant.WechatMerchantVO>
     */
    List<WechatMerchantVO> batchList(@Param("vo") WechatMerchantVO wechatMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:17
     * @description: 批量绑定商户
     * @param: [wechatMerchantVO]
     * @return: int
     */
    int batchBinding(@Param("vo") WechatMerchantVO wechatMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 18:46
     * @description: 解绑
     * @param: [wechatMerchantVO]
     * @return: int
     */
    int unbind(@Param("vo") WechatMerchantVO wechatMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:17
     * @description: 更新微信商户
     * @param: [wechatMerchantVO]
     * @return: int
     */
    int update(@Param("vo") WechatMerchantVO wechatMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:17
     * @description: 新增微信商户
     * @param: [wechatMerchantVO]
     * @return: int
     */
    int insert(@Param("vo") WechatMerchantVO wechatMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-10 23:34
     * @description: 获取支付宝商户号（启用的未满的限额内的且最空闲的）
     * @param: [app_id, dailyLimit]
     * @return: com.weilaizhe.common.pojo.wechatmerchant.WechatMerchantVO
     */
    WechatMerchantVO getIdleAlipayMerchant(@Param("app_id") Integer app_id, @Param("dailyLimit") BigDecimal dailyLimit);

}
