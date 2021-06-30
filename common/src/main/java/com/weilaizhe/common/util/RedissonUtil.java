package com.weilaizhe.common.util;

import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.pojo.admin.AdminVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.text.MessageFormat;

/**
 * @author dameizi
 * @description redisson工具类
 * @dateTime 2019-05-22 14:32
 * @className com.weilaizhe.common.util.RedissonUtil
 */
public class RedissonUtil {

    /** 通过上下文获取Redisson客户端 */
    private static RedissonClient redissonClient = ApplicationContextUtil.getBean("redissonClient", RedissonClient.class);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 21:28
     * @description: 校验短信
     * @param: [validCode]
     * @return: void
     */
    public static boolean verifySms(String phoneSmsCode, String oldTelPhone){
        RBucket<String> bucket = redissonClient.getBucket(MessageFormat.format(RedisKeyConst.MERCHANT_PHONE_SMS, oldTelPhone));
        String smsCode = bucket.get();
        if (smsCode != null){
            if (!smsCode.equals(phoneSmsCode)){
                return false;
            }
            return true;
        }else{
            bucket.delete();
            return false;
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-22 17:50
     * @description: 当前商户缓存信息
     * @param: []
     * @return: com.weilaizhe.common.pojo.merchant.MerchantVO
     */
    public static MerchantVO getContextMerchantInfo() {
        String Authorization = CommonUtil.getToken(RedisKeyConst.MERCHANT_TOKEN_COOKIE);
        String key = MessageFormat.format(RedisKeyConst.REDIS_MERCHANT_SESSION_KEY , Authorization);
        RBucket<MerchantVO> merchantVO = redissonClient.getBucket(key);
        return merchantVO.get();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-22 17:51
     * @description: 当前后台缓存信息
     * @param: []
     * @return: com.weilaizhe.common.pojo.admin.AdminVO
     */
    public static AdminVO getContextAdminInfo() {
        String Authorization = CommonUtil.getToken(RedisKeyConst.ADMIN_TOKEN_COOKIE);
        String key = MessageFormat.format(RedisKeyConst.REDIS_ADMIN_SESSION_KEY , Authorization);
        RBucket<AdminVO> adminVO = redissonClient.getBucket(key);
        return adminVO.get();
    }

}
