package com.weilaizhe.common.merchantbalance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.merchantbalance.dao.IMerchantBalanceDao;
import com.weilaizhe.common.merchantbalance.service.IMerchantBalanceService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.merchantbalance.MerchantBalanceVO;
import com.weilaizhe.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: dameizi
 * @description: 商户余额业务层
 * @dateTime 2019-03-31 22:49
 * @className com.weilaizhe.common.merchantbalance.service.impl.MerchantBalanceService
 */
@Service
@Transactional
public class MerchantBalanceServiceImpl implements IMerchantBalanceService {

    @Autowired
    private IMerchantBalanceDao iMerchantBalanceDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 23:34
     * @description: 分页条件查询
     * @param: [alipayMerchantVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.merchantbalance.MerchantBalanceVO>
     */
    @Override
    public IPage<MerchantBalanceVO> page(MerchantBalanceVO alipayMerchantVO, PageVO pageVO) {
        Page<MerchantBalanceVO> page = new Page<MerchantBalanceVO>(pageVO.getPageNo(), pageVO.getPageSize());
        return iMerchantBalanceDao.page(page, alipayMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-12 15:25
     * @description: 获取商户余额
     * @param: [merchantBalanceVO]
     * @return: com.weilaizhe.common.pojo.merchantbalance.MerchantBalanceVO
     */
    @Override
    public MerchantBalanceVO getMerchantBalance(MerchantBalanceVO merchantBalanceVO) {
        CommonUtil.integerEmptyVerify(merchantBalanceVO.getMerchantId(), CommonConst.MERCHANT_ID_NOT_EMPTY);
        return iMerchantBalanceDao.getMerchantBalance(merchantBalanceVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-31 23:27
     * @description: 新增商户余额
     * @param: [merchantBalanceVO]
     * @return: int
     */
    @Override
    public int insert(MerchantBalanceVO merchantBalanceVO) {
        return iMerchantBalanceDao.insert(merchantBalanceVO);
    }
}
