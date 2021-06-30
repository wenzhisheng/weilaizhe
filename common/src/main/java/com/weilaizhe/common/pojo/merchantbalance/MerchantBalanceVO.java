package com.weilaizhe.common.pojo.merchantbalance;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: dameizi
 * @description: 商户余额
 * @dateTime 2019-03-31 21:59
 * @className com.weilaizhe.common.pojo.merchantbalance.MerchantBalanceVO
 */
@ApiModel(value="商户余额",description="商户余额")
public class MerchantBalanceVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 7565247890958030974L;
    /** 商户余额Id */
    @ApiModelProperty(value="商户余额Id")
    private Integer balanceId;
    /** 商户Id */
    @ApiModelProperty(value="商户Id")
    private Integer merchantId;
    /** 商户编码 */
    @ApiModelProperty(value="商户编码")
    private String merchantCode;
    /** 商户名称 */
    @ApiModelProperty(value="商户名称")
    private String merchantName;
    /** 商户账号 */
    @ApiModelProperty(value="商户账号")
    private String merchantAccount;
    /** 余额 */
    @ApiModelProperty(value="余额")
    private BigDecimal balance;
    /** 支付 */
    @ApiModelProperty(value="支付")
    private BigDecimal payment;
    /** 支出 */
    @ApiModelProperty(value="支出")
    private BigDecimal payout;
    /** 手续费 */
    @ApiModelProperty(value="手续费")
    private BigDecimal transferCharge;

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getPayout() {
        return payout;
    }

    public void setPayout(BigDecimal payout) {
        this.payout = payout;
    }

    public BigDecimal getTransferCharge() {
        return transferCharge;
    }

    public void setTransferCharge(BigDecimal transferCharge) {
        this.transferCharge = transferCharge;
    }

    public Integer getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Integer balanceId) {
        this.balanceId = balanceId;
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

    public String getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(String merchantAccount) {
        this.merchantAccount = merchantAccount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
