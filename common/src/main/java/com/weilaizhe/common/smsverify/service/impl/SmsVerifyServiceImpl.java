package com.weilaizhe.common.smsverify.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.exception.DescribeException;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.smsverify.service.ISmsVerifyService;
import com.weilaizhe.common.util.CommonUtil;
import com.weilaizhe.common.util.CurlUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: dameizi
 * @description: 短信验证业务层
 * @dateTime 2019-04-03 14:57
 * @className com.weilaizhe.common.smsverify.service.impl.SmsVerifyServiceImpl
 */
@Service
public class SmsVerifyServiceImpl implements ISmsVerifyService {

    private static final Logger logger = LogManager.getLogger(SmsVerifyServiceImpl.class);

    private static final String REQUEST_PARAM = "{0}{1}";
    private static final String REQUEST_URL = "http://dysmsapi.aliyuncs.com/?Signature=";

    @Autowired
    private RedissonClient redissonClient;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 15:29
     * @description: 发送验证码
     * @param: [smsVO, merchantVO]
     * @return: java.lang.String
     */
    @Override
    public String sendSms(String phoneNumbers, MerchantVO merchantVO) {
        //生成验证码
        String mobileCode = String.valueOf(CommonUtil.buildVerifyCode());
        logger.info(mobileCode);
        String redisKey = MessageFormat.format(RedisKeyConst.MOBILE_VERIFICATION_CODE, merchantVO.getTelPhone());
        //验证码放入缓存，五分钟有效
        RMap<String,String> mapMobileCode = redissonClient.getMap(redisKey);
        mapMobileCode.put(redisKey,mobileCode);
        mapMobileCode.expire(5, TimeUnit.MINUTES);
        //阿里云验证码连接获取
        try {
            JSONObject result = this.aliyunSms(phoneNumbers, mobileCode);
            String code = (String) result.get("Code");
            if (!CommonConst.OK.equals(code)){
                logger.error(CommonConst.VERIFY_CODE_SEND_FAIL, code);
                throw new DescribeException(CommonConst.VERIFY_CODE_SEND_FAIL, 0);
            }
        }catch (Exception e){
            throw new DescribeException(CommonConst.VERIFY_CODE_SEND_FAIL, -1);
        }
        return null;
    }

    public JSONObject aliyunSms(String phoneNumbers, String mobileCode) throws Exception {
        String accessKeyId = "LTAIGzPOo95XLzpV";
        String accessSecret = "dZOokpIGlN56fWpYGTLcTT89Sh3jce";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat version = new SimpleDateFormat("yyyy-MM-dd");
        // 这里一定要设置GMT时区
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        Map<String, String> paras = new HashMap<String, String>();
        // 1. 系统参数
        paras.put("SignatureMethod", "HMAC-SHA1");
        paras.put("SignatureNonce", UUID.randomUUID().toString());
        paras.put("AccessKeyId", accessKeyId);
        paras.put("SignatureVersion", "1.0");
        paras.put("Timestamp", df.format(new Date()));
        paras.put("Format", "JSON");
        // 2. 业务API参数
        paras.put("Action", "SendSms");
        paras.put("Version", version.format(new Date()));
        paras.put("RegionId", "cn-shanghai");
        paras.put("PhoneNumbers", phoneNumbers);
        paras.put("SignName", "weilaizhe");
        paras.put("TemplateParam", mobileCode);
        paras.put("TemplateCode", "SMS_71390007");
        paras.put("OutId", "123");
        // 3. 去除签名关键字Key
        if (paras.containsKey("Signature")){
            paras.remove("Signature");
        }
        // 4. 参数KEY排序
        java.util.TreeMap<String, String> sortParas = new java.util.TreeMap<String, String>();
        sortParas.putAll(paras);
        // 5. 构造待签名的字符串
        java.util.Iterator<String> it = sortParas.keySet().iterator();
        StringBuilder sortQueryStringTmp = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            sortQueryStringTmp.append("&").append(specialUrlEncode(key)).append("=").append(specialUrlEncode(paras.get(key)));
        }
        // 去除第一个多余的&符号
        String sortedQueryString = sortQueryStringTmp.substring(1);
        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append("GET").append("&");
        stringToSign.append(specialUrlEncode("/")).append("&");
        stringToSign.append(specialUrlEncode(sortedQueryString));
        String sign = sign(accessSecret + "&", stringToSign.toString());
        // 6. 签名最后也要做特殊URL编码
        String signature = specialUrlEncode(sign);
        // 最终打印出合法GET请求的URL
        String param = MessageFormat.format(REQUEST_PARAM, signature, sortedQueryString);
        return CurlUtil.readOtherGET(param, REQUEST_URL);

    }
    public static String specialUrlEncode(String value) throws Exception {
        return java.net.URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
    }
    public static String sign(String accessSecret, String stringToSign) throws Exception {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
        mac.init(new javax.crypto.spec.SecretKeySpec(accessSecret.getBytes("UTF-8"), "HmacSHA1"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return new sun.misc.BASE64Encoder().encode(signData);
    }

}
