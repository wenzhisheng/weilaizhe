package com.weilaizhe.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.weilaizhe.common.annotation.OtherReturn;
import com.weilaizhe.common.bankcard.service.IBankCardService;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.exception.DescribeException;
import com.weilaizhe.common.exception.ResultUtil;
import com.weilaizhe.common.pojo.bankcard.BankCardVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.pay.PaymentVO;
import com.weilaizhe.common.staticparam.sms.SmsConfig;
import com.weilaizhe.common.util.BuildValidateCodeUtil;
import com.weilaizhe.common.util.CommonUtil;
import com.weilaizhe.common.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.weilaizhe.common.util.CommonUtil.StringFilter;

/**
 * @author: dameizi
 * @description: 公共服务
 * @dateTime 2019-03-29 13:30
 * @className com.weilaizhe.common.exception.OtherReturn
 */
@RestController
@RequestMapping("/common")
@Api(value="CommonController", tags="CommonController", description = "公共服务")
public class CommonController {

    private static Logger logger = LogManager.getLogger(CommonController.class);

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IBankCardService iBankCardService;

    /**
     * @author: dameizi
     * @dateTime: 2019-06-03 20:49
     * @description: 获取数据签名
     * @param: [paymentVO, merchantVO]
     * @return: java.lang.Object
     */
    @CrossOrigin
    @GetMapping(value = "/getSign")
    @ApiOperation(value="获取数据签名", notes="加密参数")
    public Object getSign(PaymentVO paymentVO) {
        MerchantVO merchantVO = new MerchantVO();
        merchantVO.setSecretKey(paymentVO.getExtend_params());
        return SignUtil.dataSignature(paymentVO, merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-30 20:55
     * @description: 列表查询银行编码
     * @param: [bankCardVO]
     * @return: java.lang.Object
     */
    @GetMapping(value = "/listBank")
    @ApiOperation(value="列表查询银行编码", notes="必填字段：Authorization")
    public Object listBank(BankCardVO bankCardVO) {
        return iBankCardService.list(bankCardVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-29 0:43
     * @description: 发送短信验证码
     * @param: [PhoneNumbers]
     * @return: java.lang.Object
     */
    @OtherReturn
    @GetMapping(value = "/sendSms")
    @ApiOperation(value="发送短信验证码", notes="必填字段：Authorization，手机号码")
    public Object sendSms(String PhoneNumbers){
        // 如果成功发送放入缓存，并15分钟内有效
        RBucket<String> bucket = redissonClient.getBucket(MessageFormat.format(RedisKeyConst.MERCHANT_PHONE_SMS, PhoneNumbers));
        if (bucket != null && bucket.get() != null){
            return ResultUtil.error(-1,"短信15分钟有效,请勿重复发送");
        }
        // 参数校验
        if (StringUtils.isEmpty(PhoneNumbers)){
            return ResultUtil.error(-1,CommonConst.PARAM_EXCEPTION);
        }else{
            CommonUtil.checkMobileNumber(PhoneNumbers);
        }
        // 组装验证码
        JSONObject jsonObject = new JSONObject();
        String centent = String.valueOf(CommonUtil.buildVerifyCode());
        jsonObject.put(SmsConfig.code, centent);
        DefaultProfile profile = DefaultProfile.getProfile(SmsConfig.RegionId, SmsConfig.AccessKeyID, SmsConfig.AccessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(SmsConfig.Domain);
        request.setVersion(SmsConfig.Version);
        request.setAction(SmsConfig.Action);
        request.putQueryParameter(SmsConfig.REGIONID, SmsConfig.RegionId);
        request.putQueryParameter(SmsConfig.PHONENUMBERS, PhoneNumbers);
        request.putQueryParameter(SmsConfig.SIGNNAME, SmsConfig.SignName);
        request.putQueryParameter(SmsConfig.TEMPLATECODE, SmsConfig.TemplateCode);
        request.putQueryParameter(SmsConfig.TEMPLATEPARAM, JSONObject.toJSONString(jsonObject));
        CommonResponse response;
        try {
            response = client.getCommonResponse(request);
        } catch (ServerException e) {
            logger.error("短信发送失败{}", e);
            return ResultUtil.error(-1, CommonConst.SMS_SENG_FAIL);
        } catch (ClientException e) {
            logger.error("短信发送失败{}", e);
            return ResultUtil.error(-1, CommonConst.SMS_SENG_FAIL);
        }
        Object message = JSONObject.parseObject(response.getData()).get("Message");
        if (CommonConst.OK.equals(message)){
            // 如果成功发送放入缓存，并15分钟内有效
            RBucket<String> cache = redissonClient.getBucket(MessageFormat.format(RedisKeyConst.MERCHANT_PHONE_SMS, PhoneNumbers));
            cache.set(centent);
            cache.expire(15, TimeUnit.MINUTES);
            return ResultUtil.success(message);
        }
        logger.error("短信发送成功，但{}", message);
        return ResultUtil.error(-1, CommonConst.SMS_SENG_FAIL);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 14:39
     * @description: 生成秘钥
     * @param: []
     * @return: java.lang.Object
     */
    @CrossOrigin
    @OtherReturn
    @ResponseBody
    @GetMapping("/generateSecretKey")
    @ApiOperation(value="生成秘钥",notes="必填字段：Authorization")
    public Object generateSecretKey(){
        // 随机生成udid再MD5加密得到32小写秘钥
        return CommonUtil.md5Password(UUID.randomUUID().toString());
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 14:24
     * @description: 获取验证码
     * @param: [request, response]
     * @return: com.weilaizhe.common.pojo.base.VerifyCodeVO
     */
    @OtherReturn
    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getVerifyCode")
    public Object getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            //设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            //设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            response.setHeader(CommonConst.AUTHORIZATION, request.getSession().getId());
            BuildValidateCodeUtil buildValidateCode = new BuildValidateCodeUtil();
            //输出验证码图片方法
            return buildValidateCode.getRandcode(request, response);
        } catch (Exception e) {
            logger.error("验证码获取失败", e);
        }
        return null;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-21 15:15
     * @description: 文件上传
     * @param: [file, request]
     * @return: java.lang.String
     */
    @OtherReturn
    @ResponseBody
    @ApiOperation(value = "文件上传", notes = "必传参数：file, token")
    @RequestMapping(value = "/uploadFile", produces = "text/plain; charset=utf-8", method = RequestMethod.POST)
    public Object uploadFile(@RequestParam(value = "file") MultipartFile file) {
        String path = "D://log/upload/picture/";
        String fileName = StringFilter(file.getOriginalFilename());
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        if(file.getSize()>1024*1024*5){
            throw new DescribeException("单个文件/图片大小不能超过5M!", 0);
        }
        String name = UUID.randomUUID().toString()+"." + suffix;
        String downLoadPath = MessageFormat.format("{0}{1}{2}",path, File.separator , name);
        // 根据用户ID新建目录
        File filePath = new File(path);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        try {
            // 使用springboot方法文件上传
            File targetFile = new File(path, name);
            file.transferTo(targetFile);
        } catch (Exception e) {
            logger.error("文件上传失败::{}", e);
            throw new DescribeException("文件上传失败", -1);
        }
        return downLoadPath;
    }

}
