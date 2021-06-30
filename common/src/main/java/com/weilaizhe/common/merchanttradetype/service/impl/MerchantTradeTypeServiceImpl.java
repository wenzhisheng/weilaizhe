package com.weilaizhe.common.merchanttradetype.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.merchanttradetype.dao.IMerchantTradeTypeDao;
import com.weilaizhe.common.merchanttradetype.service.IMerchantTradeTypeService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.merchanttradetype.MerchantTradeTypeVO;
import com.weilaizhe.common.util.RedissonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author: dameizi
 * @description: 商户交易类型业务层
 * @dateTime 2019-03-31 22:56
 * @className com.weilaizhe.common.merchanttradetype.service.impl.MerchantTradeTypeServiceImpl
 */
@Service
public class MerchantTradeTypeServiceImpl implements IMerchantTradeTypeService {

    @Autowired
    private IMerchantTradeTypeDao iMerchantTradeTypeDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-05-30 23:55
     * @description: 商户更新商户交易类型
     * @param: [merchantTradeTypeVO]
     * @return: java.lang.Object
     */
    @Override
    public Object update(MerchantTradeTypeVO merchantTradeTypeVO) {
        if (StringUtils.isEmpty(merchantTradeTypeVO.getMerchantTradeTypeId())){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.ID_NOT_EMPTY);
        }
        MerchantVO merchantInfo = RedissonUtil.getContextMerchantInfo();
        merchantTradeTypeVO.setMerchantId(merchantInfo.getMerchantId());
        merchantTradeTypeVO.setIsEnable(0);
        return iMerchantTradeTypeDao.updateMerchantTradeType(merchantTradeTypeVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-30 17:18
     * @description: 分页查询商户交易类型
     * @param: [merchantTradeTypeVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.merchanttradetype.MerchantTradeTypeVO>
     */
    @Override
    public IPage<MerchantTradeTypeVO> page(MerchantTradeTypeVO merchantTradeTypeVO, PageVO pageVO) {
        merchantTradeTypeVO.setIsEnable(1);
        merchantTradeTypeVO.setMerchantId(RedissonUtil.getContextMerchantInfo().getMerchantId());
        Page<MerchantTradeTypeVO> page = new Page<MerchantTradeTypeVO>(pageVO.getPageNo(), pageVO.getPageSize());
        return iMerchantTradeTypeDao.page(page, merchantTradeTypeVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-31 13:00
     * @description: 商户交易类型列表
     * @param: [merchantTradeTypeVO]
     * @return: java.util.List<java.lang.String>
     */
    @Override
    public List<String> listMerchantTradeTypeName(MerchantTradeTypeVO merchantTradeTypeVO) {
        merchantTradeTypeVO.setMerchantId(RedissonUtil.getContextMerchantInfo().getMerchantId());
        return iMerchantTradeTypeDao.listMerchantTradeTypeName(merchantTradeTypeVO);
    }
}
