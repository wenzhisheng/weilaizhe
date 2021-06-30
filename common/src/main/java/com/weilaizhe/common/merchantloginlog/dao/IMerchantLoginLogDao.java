package com.weilaizhe.common.merchantloginlog.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.pojo.merchantloginlog.MerchantLoginLogVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author: dameizi
 * @description: 商户登录日志数据层
 * @dateTime 2019-04-02 19:32
 * @className com.weilaizhe.common.merchantloginlog.service.IMerchantLoginLogService
 */
public interface IMerchantLoginLogDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:35
     * @description: 批量删除商户登录日志
     * @param: [ids]
     * @return: int
     */
    int batchDelete(@Param("vo") MerchantLoginLogVO merchantLoginLogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:19
     * @description: 分页查询商户登录日志
     * @param: [page, merchantLoginLogVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.merchantloginlog.MerchantLoginLogVO>
     */
    IPage<MerchantLoginLogVO> page(Page<MerchantLoginLogVO> page, @Param("vo") MerchantLoginLogVO merchantLoginLogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:37
     * @description: 新增商户登录日志
     * @param: [merchantLoginLogVO]
     * @return: int
     */
    int insert(@Param("vo") MerchantLoginLogVO merchantLoginLogVO);

}
