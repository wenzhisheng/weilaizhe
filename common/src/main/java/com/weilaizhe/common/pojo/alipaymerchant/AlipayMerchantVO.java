package com.weilaizhe.common.pojo.alipaymerchant;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author: dameizi
 * @description: 支付宝商户
 * @dateTime 2019-04-01 23:31
 * @className com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO
 */
@ApiModel(value = "支付宝商户", description = "支付宝商户")
public class AlipayMerchantVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 764653504423078957L;

    /** 支付宝商户ID */
    @ApiModelProperty(value = "支付宝商户ID")
    private Integer alipayMerchantId;
    /** 商户ID */
    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;
    /** 商户名称 */
    @ApiModelProperty(value = "商户名称")
    private String merchantName;
    /** 支付宝商户名 */
    @ApiModelProperty(value = "支付宝商户名")
    private String alipayMerchantName;
    /** 支付宝商户号 */
    @ApiModelProperty(value = "支付宝商户号")
    private String alipayMerchantCode;
    /** 支付宝应用标识 */
    @ApiModelProperty(value = "支付宝应用标识")
    private String alipayAppid;
    /** 支付宝商户秘钥 */
    @ApiModelProperty(value = "支付宝商户秘钥")
    private String alipayPrivateKey;
    /** 支付宝商户公钥 */
    @ApiModelProperty(value = "支付宝商户公钥")
    private String alipayPublicKey;
    /** 支付宝回调地址 */
    @ApiModelProperty(value = "支付宝回调地址")
    private String alipayCallbackUrl;
    /** 支付宝成功跳转地址 */
    @ApiModelProperty(value = "支付宝成功跳转地址")
    private String alipayReturnUrl;
    /** 是否启用（0：禁用 1启用） */
    @ApiModelProperty(value = "是否启用（0：禁用 1启用）")
    private Integer isEnable;
    /** 是否已满（0：未满 1：已满） */
    @ApiModelProperty(value = "是否已满（0：未满 1：已满）")
    private Integer isFull;
    /** 当天总金额 */
    @ApiModelProperty(value = "当天总金额")
    private BigDecimal dailyTotalAmount;
    /** 备注信息 */
    @ApiModelProperty(value = "备注信息")
    private String remark;
    /** 累计收款 */
    @ApiModelProperty(value = "累计收款")
    private BigDecimal totalPayment;
    /** 当天限额 */
    @ApiModelProperty(value = "当天限额")
    private BigDecimal dailyLimit;

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(BigDecimal dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public Integer getAlipayMerchantId() {
        return alipayMerchantId;
    }

    public void setAlipayMerchantId(Integer alipayMerchantId) {
        this.alipayMerchantId = alipayMerchantId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getAlipayMerchantName() {
        return alipayMerchantName;
    }

    public void setAlipayMerchantName(String alipayMerchantName) {
        this.alipayMerchantName = alipayMerchantName;
    }

    public String getAlipayMerchantCode() {
        return alipayMerchantCode;
    }

    public void setAlipayMerchantCode(String alipayMerchantCode) {
        this.alipayMerchantCode = alipayMerchantCode;
    }

    public String getAlipayAppid() {
        return alipayAppid;
    }

    public void setAlipayAppid(String alipayAppid) {
        this.alipayAppid = alipayAppid;
    }

    public String getAlipayPrivateKey() {
        return alipayPrivateKey;
    }

    public void setAlipayPrivateKey(String alipayPrivateKey) {
        this.alipayPrivateKey = alipayPrivateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getAlipayCallbackUrl() {
        return alipayCallbackUrl;
    }

    public void setAlipayCallbackUrl(String alipayCallbackUrl) {
        this.alipayCallbackUrl = alipayCallbackUrl;
    }

    public String getAlipayReturnUrl() {
        return alipayReturnUrl;
    }

    public void setAlipayReturnUrl(String alipayReturnUrl) {
        this.alipayReturnUrl = alipayReturnUrl;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getIsFull() {
        return isFull;
    }

    public void setIsFull(Integer isFull) {
        this.isFull = isFull;
    }

    public BigDecimal getDailyTotalAmount() {
        return dailyTotalAmount;
    }

    public void setDailyTotalAmount(BigDecimal dailyTotalAmount) {
        this.dailyTotalAmount = dailyTotalAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
