package com.weilaizhe.common.alipaypersonal.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author dameizi
 * @description 支付宝个人商户数据
 * @dateTime 2019-06-04 16:04
 * @className com.weilaizhe.common.alipaypersonal.dao.AlipayPersonalDao
 */
public interface IAlipayPersonalDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-06-05 12:49
     * @description: 获取支付宝个人商户
     * @param: [alipayPersonalVO]
     * @return: com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO
     */
    AlipayPersonalVO getAlipayPersonal(@Param("vo") AlipayPersonalVO alipayPersonalVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 20:24
     * @description: 获取可用支付宝个人商户号
     * @param: [alipayPersonalVO]
     * @return: com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO
     */
    AlipayPersonalVO getIdleAlipayPersonal(@Param("vo") AlipayPersonalVO alipayPersonalVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 17:02
     * @description: 支付宝个人商户分页
     * @param: [page, alipayPersonalVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO>
     */
    IPage<AlipayPersonalVO> page(Page<AlipayPersonalVO> page, @Param("vo") AlipayPersonalVO alipayPersonalVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 17:01
     * @description: 批量绑定
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    int batchBinding(@Param("vo") AlipayPersonalVO alipayPersonalVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 17:01
     * @description: 解绑
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    int unbind(@Param("vo") AlipayPersonalVO alipayPersonalVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 17:01
     * @description: 支付宝个人商户修改
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    int update(@Param("vo") AlipayPersonalVO alipayPersonalVO);

    /*
     * @author: dameizi
     * @dateTime: 2019-06-04 17:01
     * @description: 支付宝个人商户新增
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    int insert(@Param("vo") AlipayPersonalVO alipayPersonalVO);

}
