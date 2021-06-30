package com.weilaizhe.common.pojo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: dameizi
 * @description: 支付参数
 * @dateTime 2019-04-04 21:53
 * @className com.weilaizhe.common.pojo.pay.PaymentVO
 */
@ApiModel(value = "支付参数", description = "支付参数")
public class PaymentVO implements Serializable {

    private static final long serialVersionUID = -3680059763578251077L;

    /** 应用ID */
    @ApiModelProperty(value="应用ID")
    private Integer app_id;
    /** 授权码 */
    @ApiModelProperty(value="授权码")
    private String auth_code;
    /** 交易类型（通道编码） */
    @ApiModelProperty(value="交易类型（通道编码）")
    private String trade_type;
    /** 交易时间（格式为yyyy-MM-dd HH:mm:ss） */
    @ApiModelProperty(value="交易时间（格式为yyyy-MM-dd HH:mm:ss）")
    private String trade_time;
    /** 交易金额（单位为元，精确到小数点后两位） */
    @ApiModelProperty(value="交易金额（单位为元，精确到小数点后两位）")
    private BigDecimal trade_amount;
    /** 回调地址 */
    @ApiModelProperty(value="回调地址")
    private String notify_url;
    /** 商户订单号（要求32个字符内，仅支持大小写字母、数字、下划线组合，且在同一个商户号下唯一） */
    @ApiModelProperty(value="商户订单号（要求32个字符内，仅支持大小写字母、数字、下划线组合，且在同一个商户号下唯一）")
    private String out_trade_no;
    /** 商品优惠标签 */
    @ApiModelProperty(value="商品优惠标签（默认传0）")
    private String goods_tag;
    /** 商品描述 */
    @ApiModelProperty(value="商品描述")
    private String detail;
    /** 签名类型（默认为MD5，支持HMAC-SHA256） */
    @ApiModelProperty(value="签名类型（默认为MD5，支持HMAC-SHA256）")
    private String sign_type;
    /** 数据签名 */
    @ApiModelProperty(value="数据签名")
    private String sign;

    /** 平台订单号 */
    @ApiModelProperty(value="平台订单号")
    private String trade_no;
    /** 成功通知 */
    @ApiModelProperty(value="成功通知")
    private String return_url;
    /** 交易账号 */
    @ApiModelProperty(value="交易账号")
    private String trade_account;
    /** 昵称 */
    @ApiModelProperty(value="昵称")
    private String nickname;
    /** 扩展参数 */
    @ApiModelProperty(value="扩展参数")
    private String extend_params;

    public BigDecimal getTrade_amount() {
        return trade_amount;
    }

    @Override
    public String toString() {
        return "PaymentVO{" +
                "app_id=" + app_id +
                ", auth_code='" + auth_code + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", trade_time='" + trade_time + '\'' +
                ", trade_amount=" + trade_amount +
                ", notify_url='" + notify_url + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", goods_tag='" + goods_tag + '\'' +
                ", detail='" + detail + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", sign='" + sign + '\'' +
                ", trade_no='" + trade_no + '\'' +
                ", return_url='" + return_url + '\'' +
                ", trade_account='" + trade_account + '\'' +
                ", nickname='" + nickname + '\'' +
                ", extend_params='" + extend_params + '\'' +
                '}';
    }

    public void setTrade_amount(BigDecimal trade_amount) {
        this.trade_amount = trade_amount;
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

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(String trade_time) {
        this.trade_time = trade_time;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getTrade_account() {
        return trade_account;
    }

    public void setTrade_account(String trade_account) {
        this.trade_account = trade_account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getExtend_params() {
        return extend_params;
    }

    public void setExtend_params(String extend_params) {
        this.extend_params = extend_params;
    }
}
