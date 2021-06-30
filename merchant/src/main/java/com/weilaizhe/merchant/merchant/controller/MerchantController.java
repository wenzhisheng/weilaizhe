package com.weilaizhe.merchant.merchant.controller;

import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.exception.DescribeException;
import com.weilaizhe.common.merchant.service.IMerchantService;
import com.weilaizhe.common.merchantloginlog.service.IMerchantLoginLogService;
import com.weilaizhe.common.pojo.base.LoginDTO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.merchantloginlog.MerchantLoginLogVO;
import com.weilaizhe.common.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author: dameizi
 * @description: 商户信息
 * @dateTime 2019-03-29 15:55
 * @className com.weilaizhe.merchant.merchant.controller.MerchantController
 */
@RestController
@RequestMapping("/user")
@Api(value="MerchantController", tags="MerchantController", description="商户信息")
public class MerchantController {

    private final static Logger logger = LogManager.getLogger(MerchantController.class);

    // 当测试authTest时候，把genSecretTest生成的secret值赋值给它
    private static final String SECRET = "QFJWSIK3PZXRBUGM";

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IMerchantService iMerchantService;
    @Autowired
    private IMerchantLoginLogService iMerchantLoginLogService;

    /**
     * @author: dameizi
     * @dateTime: 2019-05-28 20:27
     * @description: 获取当前商户
     * @param: [merchantVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/getMerchant" , method = {RequestMethod.GET})
    @ApiOperation(value="获取当前商户",notes="无需参数")
    public Object getMerchant(MerchantVO merchantVO){
        MerchantVO merchantInfo = RedissonUtil.getContextMerchantInfo();
        if (merchantInfo == null){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.LOGIN_CACHE_FAIL);
        }
        merchantVO.setMerchantId(merchantInfo.getMerchantId());
        return iMerchantService.getMerchantById(merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 22:38
     * @description: 设置提现密码
     * @param: [merchantVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/withdrawPassword" , method = {RequestMethod.POST})
    @ApiOperation(value="设置提现密码",notes="必填参数，提现密码，手机验证码，如果第一次设置不需要原来的提现密码，如果是修提现密码，需要填写原支付密码")
    public Object modifyWithdrawPassword(@RequestBody MerchantVO merchantVO){
        MerchantVO merchantInfo = RedissonUtil.getContextMerchantInfo();
        return iMerchantService.withdrawPassword(merchantVO,merchantInfo);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 14:01
     * @description: 更新密保
     * @param: [merchantVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/updateEncrypted" , method = {RequestMethod.POST})
    @ApiOperation(value="更新密保",notes="必填参数：新密保问题、密保答案、短信验证码")
    public Object updateEncrypted(@RequestBody MerchantVO merchantVO){
        merchantVO.setMerchantId(RedissonUtil.getContextMerchantInfo().getMerchantId());
        return iMerchantService.smsVerify(merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 21:59
     * @description: 密保验证
     * @param: [merchantVO]
     * @return: java.lang.Object
     */
    @ResponseBody
    @RequestMapping(value = "/encryptedVerify" , method = {RequestMethod.POST})
    @ApiOperation(value="密保验证",notes="必填参数：新手机号码、密保答案")
    public Object encryptedVerify(@RequestBody @ApiParam(required=true) MerchantVO merchantVO){
        merchantVO.setMerchantId(RedissonUtil.getContextMerchantInfo().getMerchantId());
        MerchantVO merchantVO1 = iMerchantService.getMerchantById(merchantVO);
        if (StringUtils.isEmpty(merchantVO.getPasswordProtectQuestion()) ||
                StringUtils.isEmpty(merchantVO1.getPasswordProtectQuestion())){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.SETTING_PASSWORD_PROTECT_QNSWER);
        }
        //对比密保答案是否正确
        if (!merchantVO1.getPasswordProtectAnswer().equals(merchantVO.getPasswordProtectAnswer())){
            return MessageFormat.format(CommonConst.ERROR, CommonConst.PASSWORD_PROTECT_QNSWER_ERROR);
        }
        //校验手机格式
        CommonUtil.checkMobileNumber(merchantVO.getTelPhone());
        //更新手机号码
        return iMerchantService.smsVerify(merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 21:51
     * @description: 短信验证
     * @param: [merchantVO]
     * @return: java.lang.Object
     */
    @ResponseBody
    @RequestMapping(value = "/smsVerify" , method = RequestMethod.POST)
    @ApiOperation(value="短信验证",notes="入参：短信验证码、新手机号码")
    public Object smsVerify(@RequestBody @ApiParam(required=true) MerchantVO merchantVO){
        MerchantVO merchantInfo = RedissonUtil.getContextMerchantInfo();
        merchantVO.setMerchantId(merchantInfo.getMerchantId());
        CommonUtil.checkMobileNumber(merchantVO.getTelPhone());
        //根据商户编码更新手机号码
        return iMerchantService.smsVerify(merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 14:01
     * @description: 更新密码
     * @param: [merchantVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ApiOperation(value="更新商户密码",notes="必填字段：新密码、确认密码、原密码")
    public Object updateMerchantPassWordById(@RequestBody MerchantVO merchantVO){
        merchantVO.setMerchantId(RedissonUtil.getContextMerchantInfo().getMerchantId());
        return iMerchantService.updateMerchantPassword(merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 22:00
     * @description: 更新商户信息
     * @param: [merchant, request]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value="更新商户信息",notes="必填参数：主键")
    public Object updateMerchant(@RequestBody MerchantVO merchantVO){
        MerchantVO merchantInfo = RedissonUtil.getContextMerchantInfo();
        merchantVO.setMerchantId(merchantInfo.getMerchantId());
        return iMerchantService.updateMerchant(merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 21:51
     * @description: 退出登录
     * @param: [request, response]
     * @return: java.lang.Object
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value="退出登录",notes="必填字段：Authorization")
    public Object logout(HttpServletRequest request) {
        String token = CommonUtil.getToken(RedisKeyConst.MERCHANT_TOKEN_COOKIE);
        String redisSessionKey = MessageFormat.format(RedisKeyConst.REDIS_MERCHANT_SESSION_KEY, token);
        RBucket<MerchantVO> sessionInfo =  redissonClient.getBucket(redisSessionKey);
        sessionInfo.delete();
        // 清除 redis 缓存
        if(RedissonUtil.getContextMerchantInfo() != null){
            String redisTokenKey = MessageFormat.format(RedisKeyConst.REDIS_MERCHANT_TOKEN, RedissonUtil.getContextMerchantInfo().getAccount());
            redissonClient.getKeys().delete(redisTokenKey);
        }
        return CommonConst.LOGOUT_SUCCESS;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 17:42
     * @description: 商户登录
     * @param: [merchantDTO, request, response]
     * @return: java.lang.Object
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value="商户登录",notes="商户登录: 账号必填，密码必填，验证码必填，Authorization必填")
    public Object login(HttpServletRequest request, @RequestBody LoginDTO loginDTO){
        // DTO转为VO
        MerchantVO merchant = POJOConvertUtil.convertPojo(loginDTO, MerchantVO.class);
        // 查询登录信息
        MerchantVO user = iMerchantService.login(merchant);
        // 登录次数与账号密码判断
        this.loginAndAccountPassword(user, loginDTO);
        // 记录商户登录日志
        this.insertMerchantLoginLog(user, request);
        // redisson管理会话与sessionid
        return this.sessionManagement(user, request);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:04
     * @description: 登录次数与账号密码判断
     * @param: [user, loginVO]
     * @return: java.lang.Object
     */
    public Object loginAndAccountPassword(MerchantVO user, LoginDTO loginDTO){
        if(user != null){
            if (user.getFreezeType() == 1){
                throw new DescribeException(CommonConst.ACCOUNT_LOGIN_FREEZE, 0);
            }
            long loginFail;
            String redisKey = user.getAccount();
            loginDTO.setPassword(CommonUtil.md5Password16(loginDTO.getPassword()));
            if (!user.getPassword().equals(loginDTO.getPassword())){
                loginFail = redissonClient.getAtomicLong(MessageFormat.format(RedisKeyConst.LOGIN_FAIL_COUNT,redisKey)).incrementAndGet();
                if (loginFail == CommonConst.NUMBER_5){
                    // 更新商户冻结类型
                    user.setFreezeType(1);
                    iMerchantService.updateMerchantByFreezeType(user);
                }
                return MessageFormat.format(CommonConst.ERROR, CommonConst.ACCOUNT_PASSWORD_ERROR);
            }else{
                if (user.getFreezeType().equals(1)){
                    throw new DescribeException(CommonConst.ACCOUNT_LOGIN_FREEZE, 0);
                }
                // 登录错误次数不超过五次清除缓存
                redissonClient.getKeys().deleteByPattern(MessageFormat.format(RedisKeyConst.LOGIN_FAIL_COUNT,redisKey));
            }
        }else{
            throw new DescribeException(CommonConst.ACCOUNT_PASSWORD_ERROR, 0);
        }
        return null;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:51
     * @description: 记录商户登录日志
     * @param: [user, request]
     * @return: java.lang.Object
     */
    public Object insertMerchantLoginLog(MerchantVO user, HttpServletRequest request){
        MerchantLoginLogVO merchantLoginLogVO = new MerchantLoginLogVO();
        // 商户账号
        merchantLoginLogVO.setMerchantAccount(user.getAccount());
        // 登录ip
        merchantLoginLogVO.setLoginIp(CommonUtil.getIpAddr(request));
        // ip转换物理地址
        IpAccessCityUtil ipAccessCity = new IpAccessCityUtil();
        try {
            String ipAddr = MessageFormat.format("ip={0}", CommonUtil.getIpAddr(request));
            merchantLoginLogVO.setLoginRealAddress(ipAccessCity.getAddresses(ipAddr, "utf-8"));
        } catch (Exception e) {
            logger.error(CommonConst.GET_IP_ADDRESS_FAIL, e);
        }
        return iMerchantLoginLogService.insert(merchantLoginLogVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:54
     * @description: redisson管理会话与sessionid
     * @param: [merchant, session]
     * @return: java.lang.Object
     */
    private Object sessionManagement(MerchantVO merchantVO, HttpServletRequest request) {
        merchantVO.setPassword(null);
        // redisson管理会话
        String sessionId = request.getHeader(CommonConst.AUTHORIZATION);
        String redisSessionKey = MessageFormat.format(RedisKeyConst.REDIS_MERCHANT_SESSION_KEY, sessionId);
        RBucket<MerchantVO> sessionInfo =  redissonClient.getBucket(redisSessionKey);
        sessionInfo.set(merchantVO);
        sessionInfo.expire(30, TimeUnit.MINUTES);
        // 写入redis全局会话共享 用于单一用户登录  重要依据
        String token = MessageFormat.format(RedisKeyConst.REDIS_MERCHANT_TOKEN, merchantVO.getAccount());
        RBucket<String> tokenBuck = redissonClient.getBucket(token);
        tokenBuck.set(sessionId);
        logger.info(CommonConst.LOGIN_SUCCESS, merchantVO.getAccount());
        return merchantVO;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-14 15:15
     * @description: 谷歌动态口令
     * @param: [securityCode]
     * @return: boolean
     */
    @ResponseBody
    @ApiOperation(value = "获取动态口令")
    @RequestMapping(value = "/authenticator", method = RequestMethod.GET)
    public Object authenticator(HttpServletRequest request, HttpServletResponse response, String securityCode){
        return GoogleAuthenticator.getAuthenticator(request, response, securityCode, SECRET);
    }

    /*public static void main(String[] args) {
        String secret = GoogleAuthenticator.generateSecretKey();
        // 把这个qrcode生成二维码，用google身份验证器扫描二维码就能添加成功
        String qrcode = GoogleAuthenticator.getQRBarcode("merchant", SECRET);
        String qrBarcodeURL = GoogleAuthenticator.getQRBarcodeURL("merchant", "b.weilaipay.com", SECRET);
        System.out.println("二维码地址："+ qrBarcodeURL);
        System.out.println("qrcode:" + qrcode + ",key:" + SECRET);
        String key = "683774";
        boolean authcode = GoogleAuthenticator.authcode(key, SECRET);
        System.out.println("检查code是否正确？" + authcode);
    }*/

}
