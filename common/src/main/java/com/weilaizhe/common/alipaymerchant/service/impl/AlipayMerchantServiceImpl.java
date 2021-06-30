package com.weilaizhe.common.alipaymerchant.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.alipaymerchant.dao.IAlipayMerchantDao;
import com.weilaizhe.common.alipaymerchant.service.IAlipayMerchantService;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.exception.DescribeException;
import com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.tradetype.TradeTypeVO;
import com.weilaizhe.common.util.CommonUtil;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: dameizi
 * @description: 支付宝商户业务层
 * @dateTime 2019-04-07 12:32
 * @className com.weilaizhe.common.alipaymerchant.service.AlipayMerchantServiceImpl
 */
@Service
public class AlipayMerchantServiceImpl implements IAlipayMerchantService {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IAlipayMerchantDao iAlipayMerchantDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-09 17:22
     * @description: 更新当前支付宝商户收款额
     * @param: [passbackParams, totalAmount]
     * @return: int
     */
    @Override
    public int updateDailyTotalAmount(String passbackParams, String totalAmount) {
        // 获取当前交易类型当天限额
        RMap<Object, Object> map = redissonClient.getMap(RedisKeyConst.TRADETYPE_INFO_MAP);
        TradeTypeVO tradeTypeVO = (TradeTypeVO) map.get(CommonConst.PAYMENT_CHANNELS_ZFBSH);
        BigDecimal dailyLimit = tradeTypeVO.getDailyLimit();
        // 更新当天收款总额
        AlipayMerchantVO alipayMerchantVO = new AlipayMerchantVO();
        alipayMerchantVO.setDailyTotalAmount(new BigDecimal(totalAmount));
        alipayMerchantVO.setDailyLimit(dailyLimit);
        alipayMerchantVO.setAlipayMerchantCode(passbackParams);
        return iAlipayMerchantDao.updateDailyTotalAmount(alipayMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:59
     * @description: 定时任务，每天凌晨更新未满
     * @param: []
     * @return: int
     */
    @Override
    public int updateStatus() {
        return iAlipayMerchantDao.updateStatus();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 13:28
     * @description: 获取支付宝商户号（启用的未满的且最空闲的）
     * @param: [app_id, tradeType]
     * @return: com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO
     */
    @Override
    public AlipayMerchantVO getIdleAlipayMerchant(Integer app_id,  String tradeType) {
        // 查询缓存根据限额获取到支付账号
        RMap<String, TradeTypeVO> tradeTypeMap = redissonClient.getMap(RedisKeyConst.TRADETYPE_INFO_MAP);
        TradeTypeVO tradeTypeVO = tradeTypeMap.get(tradeType);
        return iAlipayMerchantDao.getIdleAlipayMerchant(app_id, tradeTypeVO.getDailyLimit());
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:42
     * @description: 支付宝商户列表查询
     * @param: [alipayMerchantVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO>
     */
    @Override
    public List<AlipayMerchantVO> list(AlipayMerchantVO alipayMerchantVO) {
        return iAlipayMerchantDao.list(alipayMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-14 20:48
     * @description: 获取支付宝商户
     * @param: [alipayMerchantVO]
     * @return: com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO
     */
    @Override
    public AlipayMerchantVO getAlipayMerchant(AlipayMerchantVO alipayMerchantVO) {
        return iAlipayMerchantDao.getAlipayMerchant(alipayMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:42
     * @description: 支付宝商户分页查询
     * @param: [alipayMerchantVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO>
     */
    @Override
    public IPage<AlipayMerchantVO> page(AlipayMerchantVO alipayMerchantVO, PageVO pageVO) {
        Page<AlipayMerchantVO> page = new Page<AlipayMerchantVO>(pageVO.getPageNo(), pageVO.getPageSize());
        return iAlipayMerchantDao.page(page, alipayMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:40
     * @description: 支付宝商户删除
     * @param: [alipayMerchantVO]
     * @return: int
     */
    @Override
    public int delete(AlipayMerchantVO alipayMerchantVO) {
        return iAlipayMerchantDao.delete(alipayMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 22:23
     * @description: 批量绑定商户
     * @param: [alipayMerchantVO]
     * @return: int
     */
    @Override
    public int batchBinding(AlipayMerchantVO alipayMerchantVO) {
        // 参数校验
        CommonUtil.paramEmptyVerify(String.valueOf(alipayMerchantVO.getIds()), CommonConst.ID_NOT_EMPTY);
        CommonUtil.integerEmptyVerify(alipayMerchantVO.getMerchantId(), CommonConst.MERCHANT_ID_NOT_EMPTY);
        // 判断是否启用或已绑定
        List<AlipayMerchantVO> voList = iAlipayMerchantDao.batchList(alipayMerchantVO);
        for(int i=0; i<voList.size(); i++){
            if(voList.get(i).getMerchantId() != null && voList.get(i).getIsEnable() == 0){
                throw new DescribeException("绑定过或禁用不能操作", 0);
            }
        }
        return iAlipayMerchantDao.batchBinding(alipayMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 22:23
     * @description:
     * @param: [alipayMerchantVO]
     * @return: int
     */
    @Override
    public int unbind(AlipayMerchantVO alipayMerchantVO) {
        CommonUtil.integerEmptyVerify(alipayMerchantVO.getAlipayMerchantId(), CommonConst.ID_NOT_EMPTY);
        return iAlipayMerchantDao.unbind(alipayMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:40
     * @description: 支付宝商户更新
     * @param: [alipayMerchantVO]
     * @return: int
     */
    @Override
    public int update(AlipayMerchantVO alipayMerchantVO) {
        // 参数校验
        CommonUtil.integerEmptyVerify(alipayMerchantVO.getAlipayMerchantId(), CommonConst.ID_NOT_EMPTY);
        CommonUtil.paramEmptyVerify(alipayMerchantVO.getAlipayMerchantCode(), CommonConst.MERCHANT_CODE_ERROR);
        // 商户编码不能重复
        if (iAlipayMerchantDao.getAlipayMerchantByCode(alipayMerchantVO) != null){
            throw new DescribeException(CommonConst.MERCHANT_CODE_ERROR, -3);
        }
        return iAlipayMerchantDao.update(alipayMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 14:41
     * @description: 支付宝商户新增
     * @param: [alipayMerchantVO]
     * @return: int
     */
    @Override
    public int insert(AlipayMerchantVO alipayMerchantVO) {
        CommonUtil.paramEmptyVerify(alipayMerchantVO.getAlipayMerchantCode(), CommonConst.MERCHANT_CODE_ERROR);
        // 商户编码不能重复
        if (iAlipayMerchantDao.getAlipayMerchantByCode(alipayMerchantVO) != null){
            throw new DescribeException(CommonConst.MERCHANT_CODE_ERROR, -3);
        }
        alipayMerchantVO.setDailyTotalAmount(BigDecimal.ZERO);
        return iAlipayMerchantDao.insert(alipayMerchantVO);
    }

}
