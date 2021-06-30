package com.weilaizhe.common.pojo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: dameizi
 * @description: 支付参数
 * @dateTime 2019-04-04 21:54
 * @className com.weilaizhe.common.pojo.pay.PayoutVO
 */
@ApiModel(value = "支付参数", description = "支出参数")
public class PayoutVO implements Serializable {

    private static final long serialVersionUID = -488478888456303355L;

    /** 应用ID */
    @ApiModelProperty(value="应用ID")
    private Integer app_id;
    /** 授权码 */
    @ApiModelProperty(value="授权码")
    private String auth_code;
    /** 持卡人姓名 */
    @ApiModelProperty(value="持卡人姓名")
    private String cardholder_name;
    /** 银行卡号 */
    @ApiModelProperty(value="银行卡号")
    private String bank_number;
    /** 银行编码 */
    @ApiModelProperty(value="银行编码")
    private String bank_code;
    /** 总金额（单位为元，精确到小数点后两位） */
    @ApiModelProperty(value="总金额（单位为元，精确到小数点后两位）")
    private BigDecimal total_amount;
    /** 交易时间（格式为yyyyMMddHHmmss） */
    @ApiModelProperty(value="交易时间（格式为yyyyMMddHHmmss）")
    private String trade_time;
    /** 商户订单号（要求32个字符内，仅支持大小写字母、数字、下划线组合，且在同一个商户号下唯一） */
    @ApiModelProperty(value="商户订单号（要求32个字符内，仅支持大小写字母、数字、下划线组合，且在同一个商户号下唯一）")
    private String out_trade_no;
    /** 签名类型（默认为MD5，支持HMAC-SHA256） */
    @ApiModelProperty(value="签名类型（默认为MD5，支持HMAC-SHA256）")
    private String sign_type;
    /** 数据签名 */
    @ApiModelProperty(value="数据签名")
    private String sign;

    @Override
    public String toString() {
        return "PayoutVO{" +
                "app_id=" + app_id +
                ", auth_code='" + auth_code + '\'' +
                ", cardholder_name='" + cardholder_name + '\'' +
                ", bank_number='" + bank_number + '\'' +
                ", bank_code='" + bank_code + '\'' +
                ", total_amount=" + total_amount +
                ", trade_time='" + trade_time + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public Integer getApp_id() {
        return app_id;
    }

    public void setApp_id(Integer app_id) {
        this.app_id = app_id;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public String getCardholder_name() {
        return cardholder_name;
    }

    public void setCardholder_name(String cardholder_name) {
        this.cardholder_name = cardholder_name;
    }

    public String getBank_number() {
        return bank_number;
    }

    public void setBank_number(String bank_number) {
        this.bank_number = bank_number;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public String getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(String trade_time) {
        this.trade_time = trade_time;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
