package com.weilaizhe.common.pojo.merchant;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: dameizi
 * @description: 商户信息
 * @dateTime 2019-03-29 14:26
 * @className com.weilaizhe.common.pojo.merchant.MerchantVO
 */
@ApiModel(value="商户信息",description="商户信息")
public class MerchantVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -2384110528181282647L;
    /** 商户ID */
    @ApiModelProperty(value="商户ID")
    private Integer merchantId;
    /** 商户名称 */
    @ApiModelProperty(value="商户名称")
    private String merchantName;
    /** 商户编码 */
    @ApiModelProperty(value="商户编码")
    private String merchantCode;
    /** 商户账号 */
    @ApiModelProperty(value="商户账号")
    private String account;
    /** 商户密码 */
    @ApiModelProperty(value="商户密码")
    private String password;
    /** 旧密码 */
    @ApiModelProperty(value="旧密码")
    private String oldPassword;
    /** 确认密码 */
    @ApiModelProperty(value="确认密码")
    private String confirmPassword;
    /** 提现密码 */
    @ApiModelProperty(value="提现密码")
    private String withdrawPassword;
    /** 提现密码 */
    @ApiModelProperty(value="新提现密码")
    private String newWithdrawPassword;
    /** 秘钥 */
    @ApiModelProperty(value="秘钥")
    private String secretKey;
    /** 手机号码 */
    @ApiModelProperty(value="手机号码")
    private String telPhone;
    /** 旧手机号码 */
    @ApiModelProperty(value="旧手机号码")
    private String oldTelPhone;
    /** 邮箱 */
    @ApiModelProperty(value="邮箱")
    private String email;
    /** 是否启用（0：禁用 1：启用） ,默认启用*/
    @ApiModelProperty(value="是否启用（0：禁用 1：启用） ,默认启用")
    private Integer isEnable;
    /** 密保问题 */
    @ApiModelProperty(value="密保问题")
    private String passwordProtectQuestion;
    /** 密保答案 */
    @ApiModelProperty(value="密保答案")
    private String passwordProtectAnswer;
    /** 手续费类型（0：按比率 1：按笔数） */
    @ApiModelProperty(value="手续费类型（0：按比率 1：按笔数） ,默认按笔数")
    private Integer transferChargeType;
    /** 支付手续费 */
    @ApiModelProperty(value="支付手续费")
    private BigDecimal paymentTransferCharge;
    /** 支出手续费 */
    @ApiModelProperty(value="支出手续费")
    private BigDecimal payoutTransferCharge;
    /** 提现手续费 */
    @ApiModelProperty(value="提现手续费")
    private BigDecimal withdrawTransferCharge;
    /** 订单延迟时间（1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h） */
    @ApiModelProperty(value="订单延迟时间（1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h）")
    private Integer orderDelayTime;
    /** IP白名单（多个以“|”竖线分割） */
    @ApiModelProperty(value="IP白名单（多个以“|”竖线分割）")
    private String ipAllowed;
    /** 冻结类型（0：未冻结 1：登录冻结 2：支付冻结） */
    @ApiModelProperty(value="冻结类型（0：未冻结 1：登录冻结 2：支付冻结）")
    private Integer freezeType;
    /** 备注 */
    @ApiModelProperty(value="备注")
    private String remark;
    /** 手机验证码 */
    @ApiModelProperty(value="手机验证码")
    private String phoneSmsCode;
    /** 交易类型 */
    @ApiModelProperty(value="交易类型")
    private String tradeType;
    /** 交易类型名称（多个以英文逗号“,”分隔） */
    @ApiModelProperty(value="交易类型名称（多个以英文逗号“,”分隔）")
    private String tradeTypeName;

    /** 余额 */
    @ApiModelProperty(value="余额")
    private BigDecimal balance;
    /** Token */
    @ApiModelProperty(value="token")
    private String token;
    /** 验证码 */
    @ApiModelProperty(value="验证码")
    private String verifyCode;

    public String getOldTelPhone() {
        return oldTelPhone;
    }

    public void setOldTelPhone(String oldTelPhone) {
        this.oldTelPhone = oldTelPhone;
    }

    public Integer getOrderDelayTime() {
        return orderDelayTime;
    }

    public void setOrderDelayTime(Integer orderDelayTime) {
        this.orderDelayTime = orderDelayTime;
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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getWithdrawPassword() {
        return withdrawPassword;
    }

    public void setWithdrawPassword(String withdrawPassword) {
        this.withdrawPassword = withdrawPassword;
    }

    public String getNewWithdrawPassword() {
        return newWithdrawPassword;
    }

    public void setNewWithdrawPassword(String newWithdrawPassword) {
        this.newWithdrawPassword = newWithdrawPassword;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getPasswordProtectQuestion() {
        return passwordProtectQuestion;
    }

    public void setPasswordProtectQuestion(String passwordProtectQuestion) {
        this.passwordProtectQuestion = passwordProtectQuestion;
    }

    public String getPasswordProtectAnswer() {
        return passwordProtectAnswer;
    }

    public void setPasswordProtectAnswer(String passwordProtectAnswer) {
        this.passwordProtectAnswer = passwordProtectAnswer;
    }

    public Integer getTransferChargeType() {
        return transferChargeType;
    }

    public void setTransferChargeType(Integer transferChargeType) {
        this.transferChargeType = transferChargeType;
    }

    public BigDecimal getPaymentTransferCharge() {
        return paymentTransferCharge;
    }

    public void setPaymentTransferCharge(BigDecimal paymentTransferCharge) {
        this.paymentTransferCharge = paymentTransferCharge;
    }

    public BigDecimal getPayoutTransferCharge() {
        return payoutTransferCharge;
    }

    public void setPayoutTransferCharge(BigDecimal payoutTransferCharge) {
        this.payoutTransferCharge = payoutTransferCharge;
    }

    public BigDecimal getWithdrawTransferCharge() {
        return withdrawTransferCharge;
    }

    public void setWithdrawTransferCharge(BigDecimal withdrawTransferCharge) {
        this.withdrawTransferCharge = withdrawTransferCharge;
    }

    public String getIpAllowed() {
        return ipAllowed;
    }

    public void setIpAllowed(String ipAllowed) {
        this.ipAllowed = ipAllowed;
    }

    public Integer getFreezeType() {
        return freezeType;
    }

    public void setFreezeType(Integer freezeType) {
        this.freezeType = freezeType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhoneSmsCode() {
        return phoneSmsCode;
    }

    public void setPhoneSmsCode(String phoneSmsCode) {
        this.phoneSmsCode = phoneSmsCode;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeTypeName() {
        return tradeTypeName;
    }

    public void setTradeTypeName(String tradeTypeName) {
        this.tradeTypeName = tradeTypeName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
