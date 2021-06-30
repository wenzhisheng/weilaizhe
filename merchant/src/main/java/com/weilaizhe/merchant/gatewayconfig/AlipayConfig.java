package com.weilaizhe.merchant.gatewayconfig;

/**
 * @author: dameizi
 * @description: 支付宝商户配置
 * @dateTime 2019-04-07 15:16
 * @className com.weilaizhe.merchant.gatewayconfig.AlipayConfig
 */
public class AlipayConfig {

    // 请求网关地址
    public static String url = "https://openapi.alipay.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // RSA2
    public static String SIGNTYPE = "RSA2";

}
