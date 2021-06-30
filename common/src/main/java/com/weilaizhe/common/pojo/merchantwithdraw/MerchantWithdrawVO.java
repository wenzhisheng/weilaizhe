package com.weilaizhe.common.pojo.merchantwithdraw;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: dameizi
 * @description: 商户提现
 * @dateTime 2019-04-01 15:46
 * @className com.weilaizhe.common.pojo.merchantwithdraw.MerchantWithdrawVO
 */
@ApiModel(value = "商户提现", description = "商户提现")
public class MerchantWithdrawVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 2460312097434630546L;

    /** 提现ID */
    @ApiModelProperty(value = "提现ID")
    private Integer withdrawId;
    /** 商户ID */
    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;
    /** 商户名称 */
    @ApiModelProperty(value = "商户名称")
    private String merchantName;
    /** 提现订单号 */
    @ApiModelProperty(value = "提现订单号")
    private String withdrawOrderNumber;
    /** 提现金额 */
    @ApiModelProperty(value = "提现金额")
    private BigDecimal withdrawAmount;
    /** 实际可提金额 */
    @ApiModelProperty(value = "实际可提金额")
    private BigDecimal actualAmount;
    /** 手续费 */
    @ApiModelProperty(value = "手续费")
    private BigDecimal transferCharge;
    /** 状态（0:申请中 1:已提现 2:已退回） */
    @ApiModelProperty(value = "状态（1:申请中 2:已提现 3:已退回）")
    private Integer withdrawStatus;
    /** 银行名称 */
    @ApiModelProperty(value = "银行名称")
    private String bankName;
    /** 银行开户行地址 */
    @ApiModelProperty(value = "银行开户行地址")
    private String bankDepositAddress;
    /** 银行卡号 */
    @ApiModelProperty(value = "银行卡号")
    private String bankNumber;
    /** 收款人 */
    @ApiModelProperty(value = "收款人")
    private String payee;
    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;
    /** 系统回复 */
    @ApiModelProperty(value = "系统回复")
    private String systemReply;
    /** 到账时间 */
    @ApiModelProperty(value="到账时间")
    private Date completedTime;

    /** 手机号码 */
    @ApiModelProperty(value="手机号码")
    private String telPhone;
    /** 手机验证码 */
    @ApiModelProperty(value="手机验证码")
    private String phoneSmsCode;
    /** 提现密码 */
    @ApiModelProperty(value="提现密码")
    private String withdrawPassword;

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getPhoneSmsCode() {
        return phoneSmsCode;
    }

    public void setPhoneSmsCode(String phoneSmsCode) {
        this.phoneSmsCode = phoneSmsCode;
    }

    public String getWithdrawPassword() {
        return withdrawPassword;
    }

    public void setWithdrawPassword(String withdrawPassword) {
        this.withdrawPassword = withdrawPassword;
    }

    public Integer getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(Integer withdrawId) {
        this.withdrawId = withdrawId;
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

    public String getWithdrawOrderNumber() {
        return withdrawOrderNumber;
    }

    public void setWithdrawOrderNumber(String withdrawOrderNumber) {
        this.withdrawOrderNumber = withdrawOrderNumber;
    }

    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getTransferCharge() {
        return transferCharge;
    }

    public void setTransferCharge(BigDecimal transferCharge) {
        this.transferCharge = transferCharge;
    }

    public Integer getWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(Integer withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankDepositAddress() {
        return bankDepositAddress;
    }

    public void setBankDepositAddress(String bankDepositAddress) {
        this.bankDepositAddress = bankDepositAddress;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSystemReply() {
        return systemReply;
    }

    public void setSystemReply(String systemReply) {
        this.systemReply = systemReply;
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }
}
