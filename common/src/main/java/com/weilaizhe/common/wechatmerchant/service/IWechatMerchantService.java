package com.weilaizhe.common.wechatmerchant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.wechatmerchant.WechatMerchantVO;

/**
 * @author: dameizi
 * @description: 微信商户接口层
 * @dateTime 2019-04-10 23:10
 * @className com.weilaizhe.common.wechatmerchant.service.IWechatMerchantService
 */
public interface IWechatMerchantService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:17
     * @description: 分页查询微信商户
     * @param: [wechatMerchantVO]
     * @return: int
     */
    IPage<WechatMerchantVO> page(WechatMerchantVO wechatMerchantVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:17
     * @description: 删除微信商户
     * @param: [wechatMerchantVO]
     * @return: int
     */
    int delete(WechatMerchantVO wechatMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:17
     * @description: 批量绑定商户
     * @param: [wechatMerchantVO]
     * @return: int
     */
    int batchBinding(WechatMerchantVO wechatMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 18:43
     * @description: 解绑
     * @param: [wechatMerchantVO]
     * @return: int
     */
    int unbind(WechatMerchantVO wechatMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:17
     * @description: 更新微信商户
     * @param: [wechatMerchantVO]
     * @return: int
     */
    int update(WechatMerchantVO wechatMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:17
     * @description: 新增微信商户
     * @param: [wechatMerchantVO]
     * @return: int
     */
    int insert(WechatMerchantVO wechatMerchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-10 23:15
     * @description: 获取支付宝商户号（启用的未满的限额内的且最空闲的）
     * @param: [app_id, tradeType]
     * @return: com.weilaizhe.common.pojo.wechatmerchant.WechatMerchantVO
     */
    WechatMerchantVO getIdleWechatMerchant(Integer app_id, String tradeType);

}
