package com.weilaizhe.common.merchantloginlog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchantloginlog.MerchantLoginLogVO;

/**
 * @author: dameizi
 * @description: 商户登录日志接口层
 * @dateTime 2019-04-02 19:32
 * @className com.weilaizhe.common.merchantloginlog.service.IMerchantLoginLogService
 */
public interface IMerchantLoginLogService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:35
     * @description: 批量删除商户登录日志
     * @param: [merchantLoginLogVO]
     * @return: int
     */
    int batchDelete(MerchantLoginLogVO merchantLoginLogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:15
     * @description: 分页查询商户登录日志
     * @param: [merchantLoginLogVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.merchantloginlog.MerchantLoginLogVO>
     */
    IPage<MerchantLoginLogVO> page(MerchantLoginLogVO merchantLoginLogVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:37
     * @description: 新增商户登录日志
     * @param: [merchantLoginLogVO]
     * @return: int
     */
    int insert(MerchantLoginLogVO merchantLoginLogVO);

}
