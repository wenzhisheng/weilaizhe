package com.weilaizhe.common.pojo.pay;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author: dameizi
 * @description: 微信商户返回
 * @dateTime 2019-04-11 15:46
 * @className com.weilaizhe.common.pojo.pay.WechatResultVO
 */
@XmlRootElement(name="xml")
public class WechatResultVO implements Serializable {

    private static final long serialVersionUID = -7236605492800188099L;

    /** 交易类型 */
    private String trade_type;
    /** 会话ID */
    private String prepay_id;
    /** 支付跳转链接 */
    private String mweb_url;
    /** 微信二维码 */
    private String code_url;

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getMweb_url() {
        return mweb_url;
    }

    public void setMweb_url(String mweb_url) {
        this.mweb_url = mweb_url;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }
}
