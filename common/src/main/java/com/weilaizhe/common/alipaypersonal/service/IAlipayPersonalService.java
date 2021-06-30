package com.weilaizhe.common.alipaypersonal.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO;
import com.weilaizhe.common.pojo.base.PageVO;

/**
 * @author dameizi
 * @description 支付宝个人商户接口
 * @dateTime 2019-06-04 16:04
 * @className com.weilaizhe.common.alipaypersonal.service.AlipayPersonalService
 */
public interface IAlipayPersonalService {

    /**
     * @author: dameizi
     * @dateTime: 2019-06-05 12:49
     * @description: 获取支付宝个人商户
     * @param: [alipayPersonalVO]
     * @return: com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO
     */
    AlipayPersonalVO getAlipayPersonal(AlipayPersonalVO alipayPersonalVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 20:06
     * @description: 获取可用支付宝个人商户号
     * @param: [Integer, tradeTypeCode]
     * @return: com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO
     */
    AlipayPersonalVO getIdleAlipayPersonal(Integer merchantId, String tradeTypeCode);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 17:00
     * @description: 支付宝个人商户分页
     * @param: [alipayPersonalVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO>
     */
    IPage<AlipayPersonalVO> page(AlipayPersonalVO alipayPersonalVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 17:00
     * @description: 批量绑定
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    Object batchBinding(AlipayPersonalVO alipayPersonalVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 16:59
     * @description: 解绑
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    Object unbind(AlipayPersonalVO alipayPersonalVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 16:59
     * @description: 支付宝个人商户修改
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    Object update(AlipayPersonalVO alipayPersonalVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 16:59
     * @description: 支付宝个人商户新增
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    Object insert(AlipayPersonalVO alipayPersonalVO);

}
