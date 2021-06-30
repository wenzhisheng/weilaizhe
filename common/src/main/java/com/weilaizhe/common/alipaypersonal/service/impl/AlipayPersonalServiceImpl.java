package com.weilaizhe.common.alipaypersonal.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.alipaypersonal.dao.IAlipayPersonalDao;
import com.weilaizhe.common.alipaypersonal.service.IAlipayPersonalService;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.tradetype.TradeTypeVO;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;

/**
 * @author dameizi
 * @description 支付宝个人商户业务
 * @dateTime 2019-06-04 16:06
 * @className com.weilaizhe.common.alipaypersonal.service.impl.AlipayPersonalImpl
 */
@Service
public class AlipayPersonalServiceImpl implements IAlipayPersonalService {

    @Autowired
    private IAlipayPersonalDao iAlipayPersonalDao;
    @Autowired
    private RedissonClient redissonClient;

    /**
     * @author: dameizi
     * @dateTime: 2019-06-05 12:51
     * @description: 获取支付宝个人商户
     * @param: [alipayPersonalVO]
     * @return: com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO
     */
    @Override
    public AlipayPersonalVO getAlipayPersonal(AlipayPersonalVO alipayPersonalVO) {
        return iAlipayPersonalDao.getAlipayPersonal(alipayPersonalVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 20:24
     * @description: 获取可用支付宝个人商户号
     * @param: [merchantId, tradeType]
     * @return: com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO
     */
    @Override
    public AlipayPersonalVO getIdleAlipayPersonal(Integer merchantId, String tradeType) {
        // 查询缓存根据限额获取到支付账号
        RMap<String, TradeTypeVO> tradeTypeMap = redissonClient.getMap(RedisKeyConst.TRADETYPE_INFO_MAP);
        TradeTypeVO tradeTypeVO = tradeTypeMap.get(tradeType);
        AlipayPersonalVO alipayPersonalVO = new AlipayPersonalVO();
        alipayPersonalVO.setMerchantId(merchantId);
        alipayPersonalVO.setDailyTotalAmount(tradeTypeVO.getDailyLimit());
        return iAlipayPersonalDao.getIdleAlipayPersonal(alipayPersonalVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 17:00
     * @description: 支付宝个人商户分页
     * @param: [alipayPersonalVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO>
     */
    @Override
    public IPage<AlipayPersonalVO> page(AlipayPersonalVO alipayPersonalVO, PageVO pageVO) {
        Page<AlipayPersonalVO> page = new Page<AlipayPersonalVO>(pageVO.getPageNo(), pageVO.getPageSize());
        return iAlipayPersonalDao.page(page, alipayPersonalVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 17:00
     * @description: 批量绑定
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    @Override
    public Object batchBinding(AlipayPersonalVO alipayPersonalVO) {
        if (StringUtils.isEmpty(alipayPersonalVO.getIds())){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.ID_NOT_EMPTY);
        }
        return iAlipayPersonalDao.batchBinding(alipayPersonalVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 17:00
     * @description: 解绑
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    @Override
    public Object unbind(AlipayPersonalVO alipayPersonalVO) {
        if (StringUtils.isEmpty(alipayPersonalVO.getAlipayPersonalId())){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.ID_NOT_EMPTY);
        }
        return iAlipayPersonalDao.unbind(alipayPersonalVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 17:01
     * @description: 支付宝个人商户修改
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    @Override
    public Object update(AlipayPersonalVO alipayPersonalVO) {
        if (StringUtils.isEmpty(alipayPersonalVO.getAlipayPersonalId())){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.ID_NOT_EMPTY);
        }
        return iAlipayPersonalDao.update(alipayPersonalVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 17:01
     * @description: 支付宝个人商户新增
     * @param: [alipayPersonalVO]
     * @return: java.lang.Object
     */
    @Override
    public Object insert(AlipayPersonalVO alipayPersonalVO) {
        alipayPersonalVO.setDailyTotalAmount(BigDecimal.ZERO);
        return iAlipayPersonalDao.insert(alipayPersonalVO);
    }
}
