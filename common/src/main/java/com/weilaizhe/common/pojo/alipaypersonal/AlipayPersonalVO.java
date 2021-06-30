package com.weilaizhe.common.pojo.alipaypersonal;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dameizi
 * @description 支付宝个人商户
 * @dateTime 2019-06-04 15:49
 * @className com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO
 */
@ApiModel(value = "支付宝个人商户", description = "支付宝个人商户")
public class AlipayPersonalVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 2942619095363156600L;
    /** 支付宝个人商户主键ID */
    @ApiModelProperty(value = "支付宝个人商户主键ID")
    private Integer alipayPersonalId;
    /** 商户ID */
    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;
    /** 商户名称 */
    @ApiModelProperty(value = "商户名称")
    private String merchantName;
    /** 支付宝账号 */
    @ApiModelProperty(value = "支付宝账号")
    private String alipayAccount;
    /** 支付宝个人商户编码 */
    @ApiModelProperty(value = "支付宝个人商户编码")
    private String alipayPersonalCode;
    /** 收款码地址 */
    @ApiModelProperty(value = "收款码地址")
    private String tradeCodeUrl;
    /** 登录令牌 */
    @ApiModelProperty(value = "登录令牌")
    private String loginToken;
    /** 会话信息 */
    @ApiModelProperty(value = "会话信息")
    private String sessionInfo;
    /** 是否启用（0：禁用 1启用） */
    @ApiModelProperty(value = "是否启用（0：禁用 1启用）")
    private Integer isEnable;
    /** 是否已满（0：未满 1：已满） */
    @ApiModelProperty(value = "是否已满（0：未满 1：已满）")
    private Integer isFull;
    /** 是否占用（0：未用 1：占用） */
    @ApiModelProperty(value = "是否占用（0：未用 1：占用）")
    private Integer isUse;
    /** 占用时间 */
    @ApiModelProperty(value="占用时间")
    private Date useTime;
    /** 禁用原因 */
    @ApiModelProperty(value = "禁用原因")
    private String remark;
    /** 当天总金额 */
    @ApiModelProperty(value = "当天总金额")
    private BigDecimal dailyTotalAmount;
    /** 累计收款 */
    @ApiModelProperty(value = "累计收款")
    private BigDecimal totalPayment;

    public Integer getAlipayPersonalId() {
        return alipayPersonalId;
    }

    public void setAlipayPersonalId(Integer alipayPersonalId) {
        this.alipayPersonalId = alipayPersonalId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getAlipayPersonalCode() {
        return alipayPersonalCode;
    }

    public void setAlipayPersonalCode(String alipayPersonalCode) {
        this.alipayPersonalCode = alipayPersonalCode;
    }

    public String getTradeCodeUrl() {
        return tradeCodeUrl;
    }

    public void setTradeCodeUrl(String tradeCodeUrl) {
        this.tradeCodeUrl = tradeCodeUrl;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(String sessionInfo) {
        this.sessionInfo = sessionInfo;
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

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getDailyTotalAmount() {
        return dailyTotalAmount;
    }

    public void setDailyTotalAmount(BigDecimal dailyTotalAmount) {
        this.dailyTotalAmount = dailyTotalAmount;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }
}
