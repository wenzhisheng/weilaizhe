package com.weilaizhe.common.pojo.pay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: dameizi
 * @description: 微信订单
 * @dateTime 2019-04-11 14:16
 * @className com.weilaizhe.common.pojo.pay.WechatOrder
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class WechatOrderVO implements Serializable {

    private static final long serialVersionUID = -6744934712754875395L;

    /** 商品描述 */
    private String body;
    /** 商户平台ID */
    private String mch_id;
    /** 扫码支付的订单号 */
    private String product_id;
    /** 公众号id */
    private String appid;
    /** 随机字符串 */
    private String nonce_str;
    /** 签名 */
    private String sign;
    /** 场景数据 */
    private String scene_info;
    /** 异步回调api */
    private String notify_url;
    /** 支付IP */
    private String spbill_create_ip;
    /** 商品订单号 */
    private String out_trade_no;
    /** 真实金额 */
    private BigDecimal total_fee;
    /** 微信支付类型 */
    private String trade_type;
    /** 平台订单签名 */
    @XmlTransient
    private String get_sign;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getScene_info() {
        return scene_info;
    }

    public void setScene_info(String scene_info) {
        this.scene_info = scene_info;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

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

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getGet_sign() {
        return get_sign;
    }

    public void setGet_sign(String get_sign) {
        this.get_sign = get_sign;
    }
}
