package com.weilaizhe.common.pojo.wechatmerchant;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: dameizi
 * @description: 微信商户
 * @dateTime 2019-04-01 23:39
 * @className com.weilaizhe.common.pojo.wechatmerchant.WeixinMerchantVO
 */
@ApiModel(value = "微信商户", description = "微信商户")
public class WechatMerchantVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -8366587713840689895L;

    /** 微信商户ID */
    @ApiModelProperty(value = "微信商户ID")
    private Integer wechatMerchantId;
    /** 商户ID */
    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;
    /** 商户名称 */
    @ApiModelProperty(value = "商户名称")
    private String merchantName;
    /** 微信商户名称 */
    @ApiModelProperty(value = "微信商户名称")
    private String wechatMerchantName;
    /** 微信商户号 */
    @ApiModelProperty(value = "微信商户号")
    private String wechatMerchantCode;
    /** 微信商户秘钥 */
    @ApiModelProperty(value = "微信商户秘钥")
    private String wechatMerchantSecret;
    /** 微信商户支付地址 */
    @ApiModelProperty(value = "微信商户支付地址")
    private String wechatMerchantPayUrl;
    /** 微信回调地址 */
    @ApiModelProperty(value = "微信回调地址")
    private String wechatCallbackUrl;
    /** 微信应用标识 */
    @ApiModelProperty(value = "微信应用标识")
    private String wechatAppid;
    /** 是否启用H5（0：禁用 1：启用） */
    @ApiModelProperty(value = "是否启用H5（0：禁用 1：启用）")
    private Integer isEnableH5;
    /** 是否启用（0：禁用 1：启用） */
    @ApiModelProperty(value = "是否启用（0：禁用 1：启用）")
    private Integer isEnable;
    /** 是否已满（0：未满 1：已满） */
    @ApiModelProperty(value = "是否已满（0：未满 1：已满）")
    private Integer isFull;
    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;
    /** 累计收款 */
    @ApiModelProperty(value = "累计收款")
    private BigDecimal totalPayment;
    /** 当天总金额 */
    @ApiModelProperty(value = "当天总金额")
    private BigDecimal dailyTotalAmount;
    /** 商品名称(多个用竖线分隔|) */
    @ApiModelProperty(value = "商品名称(多个用竖线分隔|)")
    private String goodsName;

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public BigDecimal getDailyTotalAmount() {
        return dailyTotalAmount;
    }

    public void setDailyTotalAmount(BigDecimal dailyTotalAmount) {
        this.dailyTotalAmount = dailyTotalAmount;
    }

    public Integer getIsFull() {
        return isFull;
    }

    public void setIsFull(Integer isFull) {
        this.isFull = isFull;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getWechatMerchantId() {
        return wechatMerchantId;
    }

    public void setWechatMerchantId(Integer wechatMerchantId) {
        this.wechatMerchantId = wechatMerchantId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getWechatMerchantName() {
        return wechatMerchantName;
    }

    public void setWechatMerchantName(String wechatMerchantName) {
        this.wechatMerchantName = wechatMerchantName;
    }

    public String getWechatMerchantCode() {
        return wechatMerchantCode;
    }

    public void setWechatMerchantCode(String wechatMerchantCode) {
        this.wechatMerchantCode = wechatMerchantCode;
    }

    public String getWechatMerchantSecret() {
        return wechatMerchantSecret;
    }

    public void setWechatMerchantSecret(String wechatMerchantSecret) {
        this.wechatMerchantSecret = wechatMerchantSecret;
    }

    public String getWechatMerchantPayUrl() {
        return wechatMerchantPayUrl;
    }

    public void setWechatMerchantPayUrl(String wechatMerchantPayUrl) {
        this.wechatMerchantPayUrl = wechatMerchantPayUrl;
    }

    public String getWechatCallbackUrl() {
        return wechatCallbackUrl;
    }

    public void setWechatCallbackUrl(String wechatCallbackUrl) {
        this.wechatCallbackUrl = wechatCallbackUrl;
    }

    public String getWechatAppid() {
        return wechatAppid;
    }

    public void setWechatAppid(String wechatAppid) {
        this.wechatAppid = wechatAppid;
    }

    public Integer getIsEnableH5() {
        return isEnableH5;
    }

    public void setIsEnableH5(Integer isEnableH5) {
        this.isEnableH5 = isEnableH5;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
