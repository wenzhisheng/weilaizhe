package com.weilaizhe.common.constant;

/**
 * @author: dameizi
 * @description: 公共RedisKey
 * @dateTime 2019-03-29 23:56
 * @className com.weilaizhe.common.exception.OtherReturn
 */
public class RedisKeyConst {

    /** 请求参数KEY */
    public static final String PAYMENY_KEY = "paymeny_key";

    /** 订单缓存KEY */
    public static final String ORDER_KEY = "order_key";

    /** 订单商户缓存KEY */
    public static final String MERCHANT_KEY = "merchant_key";

    /** 平台订单缓存 */
    public static final String TRADE_NO ="trade_no_{0}";

    /** session会话存放 */
    public static final String REDIS_ADMIN_SESSION_KEY = "redis_admin_session_key_{0}";
    public static final String REDIS_MERCHANT_SESSION_KEY = "redis_merchant_session_key_{0}";

    /** redis单账号登录比对标识 */
    public static final String REDIS_MERCHANT_TOKEN = "redis_merchant_token_{0}";
    public static final String REDIS_ADMIN_TOKEN = "redis_admin_token_{0}";

    /** 用户浏览器存放的TOKEN */
    public static final String ADMIN_TOKEN_COOKIE = "admin_token_cookie";
    public static final String MERCHANT_TOKEN_COOKIE = "merchant_token_cookie";

    /** 商户手机短信 */
    public static final String MERCHANT_PHONE_SMS = "merchant_phone_sms_{0}";

    /** 存放交易类型 */
    public static final String TRADETYPE_INFO_MAP ="tradetype_info_map";

    /** 存放商户信息 */
    public static final String MERCHANT_INFO_MAP ="merchant_info_map";

    /** 存放商户信息 */
    public static final String ALIPAY_PERSONAL_MERCHANT ="alipay_personal_merchant_{0}";

    /** 手机验证码缓存 */
    public static final String MOBILE_VERIFICATION_CODE = "mobile_verification_code_{0}";

    /** 登录失败记录 */
    public static final String LOGIN_FAIL_COUNT = "login_fail_count_{0}";

    /** 记录提现密码错误次数 */
    public static final String WITHDRAW_PASSWORD_COUNT = "withdraw_password_count_{0}";

    /** 用户IP校验 */
    public static final String REDIS_USER_IP = "redis_user_ip";

    /** 订单号缓存临时判断 */
    public static final String ORDER_TEMP_CACHE = "order_temp_cache";

}
