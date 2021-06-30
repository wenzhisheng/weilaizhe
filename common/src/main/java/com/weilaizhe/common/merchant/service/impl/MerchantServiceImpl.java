package com.weilaizhe.common.merchant.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.exception.DescribeException;
import com.weilaizhe.common.merchant.dao.IMerchantDao;
import com.weilaizhe.common.merchant.service.IMerchantService;
import com.weilaizhe.common.merchantbalance.service.IMerchantBalanceService;
import com.weilaizhe.common.merchanttradetype.dao.IMerchantTradeTypeDao;
import com.weilaizhe.common.merchanttradetype.service.IMerchantTradeTypeService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.merchantbalance.MerchantBalanceVO;
import com.weilaizhe.common.pojo.merchanttradetype.MerchantTradeTypeVO;
import com.weilaizhe.common.pojo.tradetype.TradeTypeVO;
import com.weilaizhe.common.tradetype.dao.ITradeTypeDao;
import com.weilaizhe.common.util.CommonUtil;
import com.weilaizhe.common.util.RedissonUtil;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: dameizi
 * @description: 商户业务层
 * @dateTime 2019-03-29 16:07
 * @className com.weilaizhe.common.merchant.service.impl.MerchantService
 */
@Service
public class MerchantServiceImpl implements IMerchantService {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IMerchantDao iMerchantDao;
    @Autowired
    private IMerchantBalanceService iMerchantBalanceService;
    @Autowired
    private ITradeTypeDao iReceiptTypeDao;
    @Autowired
    private IMerchantTradeTypeDao iMerchantTradeTypeDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-06 15:05
     * @description: 应用ID与授权码合法验证
     * @param: [app_id, auth_code]
     * @return: int
     */
    @Override
    public int checkMerchantCode(Integer app_id, String auth_code){
        MerchantVO merchantVO = new MerchantVO();
        merchantVO.setMerchantId(app_id);
        merchantVO.setMerchantCode(auth_code);
        return iMerchantDao.checkMerchantCode(merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 12:43
     * @description: 更新提现密码
     * @param: [merchantVO, merchantInfo]
     * @return: java.lang.String
     */
    @Override
    public Object withdrawPassword(MerchantVO merchantVO, MerchantVO merchantInfo){
        //参数判空校验
        CommonUtil.paramEmptyVerify(merchantVO.getWithdrawPassword(), CommonConst.WITHDRAW_PASSWORD_NOT_EMPTY);
        CommonUtil.paramEmptyVerify(merchantVO.getPhoneSmsCode(),CommonConst.SMS_VERIFY_CODE_NOT_EMPTY);
        String withdrawPassword = merchantVO.getWithdrawPassword();
        if (merchantVO.getNewWithdrawPassword() != null){
            if (!merchantVO.getWithdrawPassword().equals(merchantInfo.getWithdrawPassword())){
                return MessageFormat.format(CommonConst.ERROR, CommonConst.WITHDRAW_PASSWORD_ERROR);
            }
            // 密码校验
            CommonUtil.passwordEasy(merchantVO.getNewWithdrawPassword());
            withdrawPassword = merchantVO.getNewWithdrawPassword();
        }
        // 密码校验
        CommonUtil.passwordEasy(merchantVO.getWithdrawPassword());
        // 校验手机验证码
        if (!RedissonUtil.verifySms(merchantVO.getPhoneSmsCode(), merchantVO.getOldTelPhone())){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.VERIFY_CODE_ERROR);
        }
        merchantVO.setWithdrawPassword(withdrawPassword);
        // 更新提现密码
        iMerchantDao.updateWithdrawPassWord(merchantVO);
        // 更新缓存
        String redisKey = MessageFormat.format(RedisKeyConst.WITHDRAW_PASSWORD_COUNT, merchantInfo.getMerchantCode());
        RBucket<Integer> withdrawPassworCount = redissonClient.getBucket(redisKey);
        withdrawPassworCount.set(5);
        updateMerchantCache();
        return CommonConst.SETTING_SUCCESS;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 15:31
     * @description: 验证码验证
     * @param: [merchantVO]
     * @return: int
     */
    @Override
    public Object smsVerify(MerchantVO merchantVO){
        CommonUtil.checkMobileNumber(merchantVO.getOldTelPhone());
        // 校验验证码
        if (!RedissonUtil.verifySms(merchantVO.getPhoneSmsCode(), merchantVO.getOldTelPhone())){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.VERIFY_CODE_ERROR);
        }
        iMerchantDao.smsVerify(merchantVO);
        //更新缓存
        updateMerchantCache();
        return 1;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 13:22
     * @description: 更新商户密码
     * @param: [merchantVO]
     * @return: int
     */
    @Override
    public Object updateMerchantPassword(MerchantVO merchantVO){
        // 原始密码校验
        MerchantVO adminUserTemp = iMerchantDao.getMerchantById(merchantVO);
        String oldPassword = CommonUtil.md5Password16(merchantVO.getOldPassword());
        if(merchantVO != null && adminUserTemp != null &&
                !oldPassword.equals(adminUserTemp.getPassword())){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.OLD_PASSWORD_ERROR);
        }
        // 密码校验
        this.passwordVerify(merchantVO);
        // 两个密码比较以免有误
        if(!merchantVO.getPassword().equals(merchantVO.getConfirmPassword()) ){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.CONFIRM_PASSWORD_ERROR);
        }
        // 密码MD532
        merchantVO.setPassword(CommonUtil.md5Password16(merchantVO.getPassword()));
        iMerchantDao.updateMerchantPassword(merchantVO);
        //更新缓存
        updateMerchantCache();
        return 1;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 13:29
     * @description: 根据商户ID获取商户
     * @param: [merchantVO]
     * @return: com.weilaizhe.common.pojo.merchant.MerchantVO
     */
    @Override
    public MerchantVO getMerchantById(MerchantVO merchantVO){
        return iMerchantDao.getMerchantById(merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 23:18
     * @description: 商户删除
     * @param: [merchantVO]
     * @return: int
     */
    @Override
    public int delete(MerchantVO merchantVO) {
        //id不能为空
        CommonUtil.integerEmptyVerify(merchantVO.getMerchantId(), CommonConst.ID_NOT_EMPTY);
        return iMerchantDao.delete(merchantVO.getMerchantId());
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 16:11
     * @description: 分页查询商户信息
     * @param: [merchantVO, pageVO]
     * @return: com.weilaizhe.common.pojo.base.PageResult<com.weilaizhe.common.pojo.merchant.MerchantVO>
     */
    @Override
    public IPage<MerchantVO> pageMerchant(MerchantVO merchantVO, PageVO pageVO){
        // mybatis plus分页查询插件，第一个参数必须是Page<T>，返回类型必须IPage<T>接收
        Page<MerchantVO> page = new Page<MerchantVO>(pageVO.getPageNo(),pageVO.getPageSize());
        return iMerchantDao.pageMerchant(page, merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-30 22:00
     * @description: 列表查询商户信息
     * @param: [merchantVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.merchant.MerchantVO>
     */
    @Override
    public List<MerchantVO> listMerchant(MerchantVO merchantVO){
        return iMerchantDao.listMerchant(merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 18:54
     * @description: 更新商户冻结类型
     * @param: [merchant]
     * @return: int
     */
    @Override
    public int updateMerchantByFreezeType(MerchantVO merchantVO){
        return iMerchantDao.updateMerchantByFreezeType(merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 17:54
     * @description: 商户登录
     * @param: [merchant]
     * @return: com.weilaizhe.common.pojo.merchant.MerchantVO
     */
    @Override
    public MerchantVO login(MerchantVO merchantVO){
        // 账号密码非空判断
        CommonUtil.paramEmptyVerify(merchantVO.getAccount(), CommonConst.ACCOUNT_NOT_EMPTY);
        CommonUtil.paramEmptyVerify(merchantVO.getPassword(), CommonConst.PASSWORD_NOT_EMPTY);
        // 密码MD5
        merchantVO.setPassword(CommonUtil.md5Password16(merchantVO.getPassword()));
        return iMerchantDao.login(merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 22:06
     * @description: 更细商户信息
     * @param: [merchantVO]
     * @return: int
     */
    @Override
    public int update(MerchantVO merchantVO){
        //id不能为空
        CommonUtil.integerEmptyVerify(merchantVO.getMerchantId(), CommonConst.ID_NOT_EMPTY);
        //判空与参数校验
        this.emptyVerifyAndParamVerify(merchantVO);
        // 配置商户交易类型
        this.updateMerchantTradeType(merchantVO);
        //更新商户
        iMerchantDao.update(merchantVO);
        //更新缓存
        updateMerchantCache();
        //获取商户id信息关联商户交易类型
        if (merchantVO.getFreezeType() == 0){
            String redisKey = MessageFormat.format(RedisKeyConst.WITHDRAW_PASSWORD_COUNT, merchantVO.getMerchantCode());
            RBucket<Integer> withdrawPassworCount = redissonClient.getBucket(redisKey);
            //商户信息更新后提现密码缓存刷新
            withdrawPassworCount.set(5);
        }
        // 清空错误次数缓存
        redissonClient.getKeys().deleteByPattern(MessageFormat.format(RedisKeyConst.LOGIN_FAIL_COUNT, merchantVO.getAccount()));
        return 1;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-27 21:10
     * @description: 商户修改
     * @param: [merchantVO]
     * @return: int
     */
    @Override
    public int updateMerchant(MerchantVO merchantVO) {
        // 公共参数校验
        this.cheakCommonMerchant(merchantVO);
        //更新商户
        iMerchantDao.updateMerchant(merchantVO);
        //更新缓存
        updateMerchantCache();
        return 1;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-31 18:07
     * @description: 申请商户
     * @param: [merchantVO]
     * @return: int
     */
    @Override
    public int insert(MerchantVO merchantVO){
        //判空与参数校验
        this.emptyVerifyAndParamVerify(merchantVO);
        // 密码MD532
        merchantVO.setPassword(CommonUtil.md5Password(merchantVO.getPassword()));
        // 生成商户编码（17位时间戳 + 3位随机数)
        merchantVO.setMerchantCode(CommonUtil.generateMerchantCode());
        // 生成秘钥
        merchantVO.setSecretKey(CommonUtil.md5Password(merchantVO.getPassword()));
        // 保存商户信息
        merchantVO.setFreezeType(0);
        iMerchantDao.insert(merchantVO);
        // 获取新增商户配置商户余额与商户交易类型
        MerchantVO merchantNew = iMerchantDao.getMerchantByCode(merchantVO);
        // 新增商户余额
        this.insertMerchantBalance(merchantNew);
        // 配置商户交易类型
        this.updateMerchantTradeType(merchantVO);
        // 商户缓存
        this.updateMerchantCache();
        return 1;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 13:44
     * @description: 密码校验
     * @param: [merchantVO]
     * @return: void
     */
    private void passwordVerify(MerchantVO merchantVO) {
        // 密码必须是6-18位数字、字母组合，字母区分大小写（MD5加密字符串校验）
        String regex;
        regex = "^([a-zA-Z0-9]{6,18})$";
        String pwd = merchantVO.getPassword();
        if(StringUtils.isEmpty(pwd) || !pwd.matches(regex)) {
            throw new DescribeException(CommonConst.MERCHANT_PASSWORD_VERIFY, 0);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-31 21:03
     * @description: 商户名称、商户账号是否使用
     * @param: [merchantVO]
     * @return: void
     */
    public void parameterIsUse(MerchantVO merchantVO){
        // 商户名称不能为空并不能大于16位
        if (StringUtils.isEmpty(merchantVO.getMerchantName()) || merchantVO.getMerchantName().length() > 16){
            throw new DescribeException("商户名称非法", 0);
        }
        // 商户编码或商户账号是否已存在
        if (iMerchantDao.getCodeOrAccountIsUse(merchantVO) > 0 ){
            throw new DescribeException(CommonConst.CODE_OR_ACCOUNT_IS_USE, -3);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-01 0:57
     * @description: 新增商户余额
     * @param: [merchantVO]
     * @return: void
     */
    public void insertMerchantBalance(MerchantVO merchantNew){
        MerchantBalanceVO merchantBalanceVO = new MerchantBalanceVO();
        merchantBalanceVO.setMerchantId(merchantNew.getMerchantId());
        merchantBalanceVO.setMerchantName(merchantNew.getMerchantName());
        merchantBalanceVO.setMerchantCode(merchantNew.getMerchantCode());
        merchantBalanceVO.setMerchantAccount(merchantNew.getAccount());
        merchantBalanceVO.setBalance(BigDecimal.ZERO);
        merchantBalanceVO.setCreator(merchantNew.getMerchantName());
        merchantBalanceVO.setMender(merchantNew.getMerchantName());
        iMerchantBalanceService.insert(merchantBalanceVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 15:45
     * @description: 配置商户交易类型
     * @param: [merchantVO, merchantNew]
     * @return: void
     */
    private void updateMerchantTradeType(MerchantVO merchantVO){
        // 绑定商户支付类型
        if (!StringUtils.isEmpty(merchantVO.getTradeTypeName())){
            String[] str = merchantVO.getTradeTypeName().split(CommonConst.COMMA);
            for (int i=0; i<str.length; i++) {
                // 根据名称查询交易类型
                TradeTypeVO tradeTypeVO = new TradeTypeVO();
                tradeTypeVO.setTradeTypeName(str[i]);
                tradeTypeVO = this.iReceiptTypeDao.getReceiptTypeByName(tradeTypeVO);
                // 配置商户交易方式
                merchantVO = this.iMerchantDao.getMerchantById(merchantVO);
                MerchantTradeTypeVO merchantTradeType = new MerchantTradeTypeVO();
                merchantTradeType.setTradeTypeCode(tradeTypeVO.getTradeTypeName());
                merchantTradeType.setTradeTypeCode(tradeTypeVO.getTradeTypeCode());
                merchantTradeType.setMerchantId(merchantVO.getMerchantId());
                merchantTradeType.setIsEnable(1);
                this.iMerchantTradeTypeDao.update(merchantTradeType);
            }
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 15:45
     * @description: 更新商户缓存
     * @param: []
     * @return: void
     */
    private void updateMerchantCache(){
        List<MerchantVO> merchantVOList = iMerchantDao.listMerchant(new MerchantVO());
        RMap<String, MerchantVO> map = redissonClient.getMap(RedisKeyConst.MERCHANT_INFO_MAP);
        map.delete();
        MerchantVO merchantInfo = RedissonUtil.getContextMerchantInfo();
        Map<String, MerchantVO> mapTemp = new HashMap<String, MerchantVO>();
        for (MerchantVO merchant: merchantVOList) {
            // redis的key根据商户编码
            mapTemp.put(String.valueOf(merchant.getMerchantCode()), merchant);
            // 如果当前浏览器登录则更新缓存
            if(merchantInfo != null && merchant.getMerchantCode().equals(merchantInfo.getMerchantCode())) {
                String token = CommonUtil.getToken(RedisKeyConst.MERCHANT_TOKEN_COOKIE);
                String redisSessionKey = MessageFormat.format(RedisKeyConst.REDIS_MERCHANT_SESSION_KEY, token);
                RBucket<MerchantVO> sessionInfo = redissonClient.getBucket(redisSessionKey);
                sessionInfo.delete();
                sessionInfo.set(merchant);
                sessionInfo.expire(30, TimeUnit.MINUTES);
            }
        }
        map.putAll(mapTemp);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 22:18
     * @description: 判空与参数校验
     * @param: [merchantVO]
     * @return: void
     */
    private void emptyVerifyAndParamVerify(MerchantVO merchantVO) {
        // 商户编码、商户账号是否使用
        this.parameterIsUse(merchantVO);
        // 账号,密码,姓名非空判断
        CommonUtil.paramEmptyVerify(merchantVO.getAccount(), CommonConst.ACCOUNT_NOT_EMPTY);
        CommonUtil.paramEmptyVerify(merchantVO.getPassword(), CommonConst.PASSWORD_NOT_EMPTY);
        // 公共参数校验
        this.cheakCommonMerchant(merchantVO);
        // 用户名必须是4-16位的字母数字或下划线
        String regex = "[a-zA-Z0-9_]{4,16}";
        String account = merchantVO.getAccount();
        if(StringUtils.isEmpty(account) || !account.matches(regex)) {
            throw new DescribeException(CommonConst.MERCHANT_NAME_VERIFY, 0);
        }
        // 查询商户密码是否存在，否则密码校验，密码MD5加密
        if (iMerchantDao.getMerchantById(merchantVO) == null){
            this.passwordVerify(merchantVO);
            merchantVO.setPassword(CommonUtil.md5Password(merchantVO.getPassword()));
        }
        // IP判断
        if (!StringUtils.isEmpty(merchantVO.getIpAllowed())) {
            // IP白名单合法校验，多个ip竖线分隔
            CommonUtil.ipAllowed(merchantVO.getIpAllowed());
        }
    }

    private void cheakCommonMerchant(MerchantVO merchantVO) {
        // 手机号码校验
        CommonUtil.checkMobileNumber(merchantVO.getTelPhone());
        // 商户备注不能超过500字
        if(!StringUtils.isEmpty(merchantVO.getRemark())){
            if (merchantVO.getRemark().length() > 500){
                throw new DescribeException(CommonConst.MERCHANT_REMARK_SIZE, 0);
            }
        }
    }

}
