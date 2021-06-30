package com.weilaizhe.common.merchantloginlog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.merchantloginlog.dao.IMerchantLoginLogDao;
import com.weilaizhe.common.merchantloginlog.service.IMerchantLoginLogService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchantloginlog.MerchantLoginLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: dameizi
 * @description: 商户登录日志业务层
 * @dateTime 2019-04-02 19:33
 * @className com.weilaizhe.common.merchantloginlog.service.MerchantLoginLogServiceImpl
 */
@Service
@Transactional
public class MerchantLoginLogServiceImpl implements IMerchantLoginLogService {

    @Autowired
    private IMerchantLoginLogDao iMerchantLoginLogDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:35
     * @description: 批量删除商户登录日志
     * @param: [ids]
     * @return: int
     */
    @Override
    public int batchDelete(MerchantLoginLogVO merchantLoginLogVO){
        return iMerchantLoginLogDao.batchDelete(merchantLoginLogVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:15
     * @description: 分页查询商户登录日志
     * @param: [merchantLoginLogVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.merchantloginlog.MerchantLoginLogVO>
     */
    @Override
    public IPage<MerchantLoginLogVO> page(MerchantLoginLogVO merchantLoginLogVO, PageVO pageVO){
        //mybatis plus分页查询插件，第一个参数必须是Page<T>，返回类型必须IPage<T>接收
        Page<MerchantLoginLogVO> page = new Page<MerchantLoginLogVO>(pageVO.getPageNo(),pageVO.getPageSize());
        return iMerchantLoginLogDao.page(page, merchantLoginLogVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:37
     * @description: 新增商户登录日志
     * @param: [merchantLoginLogVO]
     * @return: int
     */
    @Override
    public int insert(MerchantLoginLogVO merchantLoginLogVO){
        return iMerchantLoginLogDao.insert(merchantLoginLogVO);
    }


}
