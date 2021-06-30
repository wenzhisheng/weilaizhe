package com.weilaizhe.merchant.gatewayconfig;

/**
 * @author: dameizi
 * @description: 微信商户配置
 * @dateTime 2019-04-07 15:16
 * @className com.weilaizhe.merchant.gatewayconfig.WeixinConfig
 */
public class WechatConfig {

    //商户号
    public static final String MCHID = "";
    //公众号APPID
    public static final String APPID   = "";
    //微信回调URL
    public static final String NOTIFYURL = "http://xxx.com/merchant/callback/callbackgtq";
    //扫码支付类型
    public static final String TRADETYPE = "NATIVE";
    //H5支付类型
    public static final String H5TYPE= "MWEB";
    //商户秘钥
    public static final String KEY ="nGXMpWffT9OlTssAjKXUvAmsfMuHv8JC";
    //平台约定秘钥
    public static final String GETKEY ="FcK0ThXG4wyWPmlF64ey96GT0R77Mm";
    //回调URL
    public static final String CALLBACK = "http://pay.wlaipay.com/api/callbackwx";
    //微信请求URL
    public static final String POSTWXURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //微信请求URL
    public static final String POSTWXCBURL = "https://api.mch.weixin.qq.com/pay/closeorder";
    //支付地址
    public static final String PAYURL = "http://fp5588.com";

}
