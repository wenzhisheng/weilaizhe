package com.weilaizhe.common.merchantwithdraw.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.merchant.dao.IMerchantDao;
import com.weilaizhe.common.merchantwithdraw.dao.IMerchantWithdrawDao;
import com.weilaizhe.common.merchantwithdraw.service.IMerchantWithdrawService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.merchantwithdraw.MerchantWithdrawVO;
import com.weilaizhe.common.util.CommonUtil;
import com.weilaizhe.common.util.RedissonUtil;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;

/**
 * @author dameizi
 * @description 商户提现业务层
 * @dateTime 2019-04-16 13:47
 * @className com.weilaizhe.common.merchantwithdraw.service.impl.MerchantWithdrawServiceImpl
 */
@Service
public class MerchantWithdrawServiceImpl implements IMerchantWithdrawService {

    @Autowired
    private IMerchantWithdrawDao iMerchantWithdrawDao;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IMerchantDao iMerchantDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 15:46
     * @description: 提现审核
     * @param: [merchantWithdrawVO]
     * @return: int
     */
    @Override
    public int update(MerchantWithdrawVO merchantWithdrawVO) {
        CommonUtil.integerEmptyVerify(merchantWithdrawVO.getWithdrawId(), CommonConst.ID_NOT_EMPTY);
        CommonUtil.integerEmptyVerify(merchantWithdrawVO.getMerchantId(), CommonConst.MERCHANT_ID_NOT_EMPTY);
        return iMerchantWithdrawDao.update(merchantWithdrawVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 15:46
     * @description: 删除商户提现
     * @param: [merchantWithdrawVO]
     * @return: int
     */
    @Override
    public int delete(MerchantWithdrawVO merchantWithdrawVO) {
        // id不能为空
        CommonUtil.paramEmptyVerify(String.valueOf(merchantWithdrawVO.getIds()), CommonConst.ID_NOT_EMPTY);
        return iMerchantWithdrawDao.delete(merchantWithdrawVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 15:45
     * @description: 分页查询商户提现
     * @param: [merchantWithdrawVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.merchantwithdraw.MerchantWithdrawVO>
     */
    @Override
    public IPage<MerchantWithdrawVO> page(MerchantWithdrawVO merchantWithdrawVO, PageVO pageVO) {
        Page<MerchantWithdrawVO> page = new Page<MerchantWithdrawVO>(pageVO.getPageNo(), pageVO.getPageSize());
        return iMerchantWithdrawDao.page(page, merchantWithdrawVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 15:44
     * @description: 申请商户提现
     * @param: [merchantWithdrawVO]
     * @return: int
     */
    @Override
    public Object insert(MerchantWithdrawVO merchantWithdrawVO) {
        CommonUtil.paramEmptyVerify(merchantWithdrawVO.getBankNumber(), "银行卡不能为空");
        if(!CommonUtil.bankNumberVerfy(merchantWithdrawVO.getBankNumber())){
            return MessageFormat.format(CommonConst.ERROR, "银行卡必须是12-19位数字组成");
        }
        // 校验手机验证码
        if (!RedissonUtil.verifySms(merchantWithdrawVO.getPhoneSmsCode(), merchantWithdrawVO.getTelPhone())){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.VERIFY_CODE_ERROR);
        }
        // 初始信息
        MerchantVO merchantInfo = RedissonUtil.getContextMerchantInfo();
        merchantWithdrawVO.setMerchantId(merchantInfo.getMerchantId());
        merchantWithdrawVO.setMerchantName(merchantInfo.getMerchantName());
        merchantWithdrawVO.setWithdrawOrderNumber(CommonUtil.generateOrderNo(merchantInfo.getMerchantId()));
        // 判断提现密码
        if (withdrawPassworCount(merchantWithdrawVO, merchantInfo)){
            return MessageFormat.format(CommonConst.ERROR, "账号提现冻结，请联系客服！");
        }
        return iMerchantWithdrawDao.insert(merchantWithdrawVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-30 23:10
     * @description: 判断提现密码
     * @param: [merchantWithdrawVO, merchantInfo]
     * @return: boolean
     */
    private boolean withdrawPassworCount(MerchantWithdrawVO merchantWithdrawVO, MerchantVO merchantInfo) {
        if (merchantInfo.getFreezeType() == CommonConst.NUMBER_2){
            return true;
        }
        // 记录提现密码错误次数
        String withdrawPasswordKey = MessageFormat.format(RedisKeyConst.WITHDRAW_PASSWORD_COUNT, merchantInfo.getMerchantCode());
        RBucket<Integer> withdrawPassworCount = redissonClient.getBucket(withdrawPasswordKey);
        MerchantVO merchantVO = iMerchantDao.getMerchantById(merchantInfo);
        // 判断提现密码是否正确
        if (!merchantVO.getWithdrawPassword().equals(merchantWithdrawVO.getWithdrawPassword())){
            Integer count = withdrawPassworCount.get();
            // 因为Integer的默认值是null,而redis不支持基本类型
            if (StringUtils.isEmpty(count)){
                count=0;
            }
            if(CommonConst.NUMBER_5 == count){
                //更新商户冻结状态
                merchantVO.setFreezeType(2);
                iMerchantDao.updateMerchantByFreezeType(merchantVO);
            }else{
                withdrawPassworCount.set(count+1);
            }
            return true;
        }
        return false;
    }
}
