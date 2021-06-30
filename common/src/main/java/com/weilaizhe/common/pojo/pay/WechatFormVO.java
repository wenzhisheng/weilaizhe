package com.weilaizhe.common.pojo.pay;

import java.math.BigDecimal;

/**
 * @author: dameizi
 * @description: 微信商户表单
 * @dateTime 2019-04-11 13:35
 * @className com.weilaizhe.common.pojo.pay.WechatMerchantForm
 */
public class WechatFormVO {

    /** 订单号 */
    private String out_trade_no;
    /** 支付金额 */
    private BigDecimal total_fee;
    /** 客户端IP */
    private String clientIp;
    /** 签名 */
    private String get_sign;
    /** 支付方式 */
    private String tradeType;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public BigDecimal getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(BigDecimal total_fee) {
        this.total_fee = total_fee;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getGet_sign() {
        return get_sign;
    }

    public void setGet_sign(String get_sign) {
        this.get_sign = get_sign;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}
