package com.weilaizhe.common.pojo.balancechange;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author: dameizi
 * @description: 帐变信息
 * @dateTime 2019-04-01 16:00
 * @className com.weilaizhe.common.pojo.balancechange.BalanceChangeVO
 */
@ApiModel(value = "帐变信息", description = "帐变信息")
public class BalanceChangeVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -9198163251618696607L;

    /** 帐变ID */
    @ApiModelProperty(value = "帐变ID")
    private BigInteger balanceChangeId;
    /** 商户ID */
    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;
    /** 商户名称 */
    @ApiModelProperty(value = "商户名称")
    private String merchantName;
    /** 商户账号 */
    @ApiModelProperty(value = "商户账号")
    private String merchantAccount;
    /** 平台订单号 */
    @ApiModelProperty(value = "平台订单号")
    private String tradeNo;
    /** 商户订单号 */
    @ApiModelProperty(value = "商户订单号")
    private String outTradeNo;
    /** 账变类型(0:支付 1:支出 2:提现) */
    @ApiModelProperty(value = "账变类型(1:支付 2:支出 3:提现)")
    private Integer balanceChangeType;
    /** 旧金额 */
    @ApiModelProperty(value = "旧金额")
    private BigDecimal oldBlance;
    /** 新金额 */
    @ApiModelProperty(value = "新金额")
    private BigDecimal newBlance;
    /** 手续费 */
    @ApiModelProperty(value = "手续费")
    private BigDecimal transferCharge;
    /** 交易金额 */
    @ApiModelProperty(value = "交易金额")
    private BigDecimal transactionAmount;
    /** 交易类型 */
    @ApiModelProperty(value = "交易类型")
    private String tradeTypeCode;
    /** 交易账号 */
    @ApiModelProperty(value = "交易账号")
    private String tradeAccount;
    /** 交易名称 */
    @ApiModelProperty(value = "交易名称")
    private String tradeName;

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public BigInteger getBalanceChangeId() {
        return balanceChangeId;
    }

    public void setBalanceChangeId(BigInteger balanceChangeId) {
        this.balanceChangeId = balanceChangeId;
    }

    public String getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(String merchantAccount) {
        this.merchantAccount = merchantAccount;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getBalanceChangeType() {
        return balanceChangeType;
    }

    public void setBalanceChangeType(Integer balanceChangeType) {
        this.balanceChangeType = balanceChangeType;
    }

    public BigDecimal getOldBlance() {
        return oldBlance;
    }

    public void setOldBlance(BigDecimal oldBlance) {
        this.oldBlance = oldBlance;
    }

    public BigDecimal getNewBlance() {
        return newBlance;
    }

    public void setNewBlance(BigDecimal newBlance) {
        this.newBlance = newBlance;
    }

    public BigDecimal getTransferCharge() {
        return transferCharge;
    }

    public void setTransferCharge(BigDecimal transferCharge) {
        this.transferCharge = transferCharge;
    }

    public String getTradeTypeCode() {
        return tradeTypeCode;
    }

    public void setTradeTypeCode(String tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }

    public String getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }
}
