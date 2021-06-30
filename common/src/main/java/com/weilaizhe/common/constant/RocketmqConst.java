package com.weilaizhe.common.constant;

/**
 * @author: dameizi
 * @description: 消息队列常量
 * @dateTime 2019-03-26 23:57
 * @className com.weilaizhe.common.exception.OtherReturn
 */
public class RocketmqConst {

    /**消息延期级别*/
    public static final int MQ_DELAY_TIME_LEVEL = 6;

    /** 支付订单延时失效 */
    public static final String MQ_PAYMENT_DELAY_TIME_GROUPNAME = "payment_delay_time_group";
    public static final String MQ_PAYMENT_DELAY_TIME_TOPIC = "payment_delay_time_topic";
    public static final String MQ_PAYMENT_DELAY_TIME_TAG = "payment_delay_time_tag";

    /** 支付宝商户回调 */
    public static final String MQ_ALIPAY_MERCHANT_CALLBACK_GROUPNAME = "alipay_merchant_callback_group";
    public static final String MQ_ALIPAY_MERCHANT_CALLBACK_TOPIC = "alipay_merchant_callback_topic";
    public static final String MQ_ALIPAY_MERCHANT_CALLBACK_TAG = "alipay_merchant_callback_tag";

    /** 支付宝个人商户轮询**/
    public static final String MQ_ALIPAY_PERSONAL_POLL_GROUPNAME = "alipay_personal_poll_group";
    public static final String MQ_ALIPAY_PERSONAL_POLL_TOPIC = "alipay_personal_poll_topic";
    public static final String MQ_ALIPAY_PERSONAL_POLL_TAG = "alipay_personal_poll_tag";

    /** 支付宝个人商户回调 */
    public static final String MQ_ALIPAY_PERSONAL_CALLBACK_GROUPNAME = "alipay_personal_callback_group";
    public static final String MQ_ALIPAY_PERSONAL_CALLBACK_TOPIC = "alipay_personal_callback_topic";
    public static final String MQ_ALIPAY_PERSONAL_CALLBACK_TAG = "alipay_personal_callback_tag";

    /** 微信商户收款 */
    public static final String MQ_WEIXIN_MERCHANT_GROUPNAME = "weixin_merchant_group";
    public static final String MQ_WEIXIN_MERCHANT_TOPIC = "weixin_merchant_topic";
    public static final String MQ_WEIXIN_MERCHANT_TAG = "weixin_merchant_tag";

    /** 微信商户回调 */
    public static final String MQ_WEIXIN_CALLBACK_GROUPNAME = "weixin_callback_group";
    public static final String MQ_WEIXIN_CALLBACK_TOPIC = "weixin_callback_topic";
    public static final String MQ_WEIXIN_CALLBACK_TAG = "weixin_callback_tag";

    /** 微信买单 */
    public static final String MQ_WEIXIN_MAIDAN_CALLBACK_GROUPNAME = "weixin_maidan_callback_group";
    public static final String MQ_WEIXIN_MAIDAN_CALLBACK_TOPIC = "weixin_maidan_callback_topic";
    public static final String MQ_WEIXIN_MAIDAN_CALLBACK_TAG = "weixin_maidan_callback_tag";

    /** 支付宝转账 */
    public static final String MQ_ALIPAY_TRANSFER_GROUPNAME = "alipay_transfer_group";
    public static final String MQ_ALIPAY_TRANSFER_TOPIC = "alipay_transfer_topic";
    public static final String MQ_ALIPAY_TRANSFER_TAG = "alipay_transfer_tag";

}
