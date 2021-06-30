package com.weilaizhe.common.pojo.order;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author: dameizi
 * @description: 支出订单
 * @dateTime 2019-04-04 14:53
 * @className com.weilaizhe.common.pojo.order.PayoutsOrderVO
 */
@ApiModel(value = "支出订单", description = "支出订单")
public class PayoutOrderVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -5435546439010363971L;

    /** 订单ID */
    @ApiModelProperty(value = "订单ID")
    private BigInteger orderId;
    /** 商户编码 */
    @ApiModelProperty(value = "商户编码")
    private String merchantCode;
    /** 商户名称 */
    @ApiModelProperty(value = "商户名称")
    private String merchantName;
    /** 平台订单号 */
    @ApiModelProperty(value = "平台订单号")
    private String platformOrderNumber;
    /** 商户订单号 */
    @ApiModelProperty(value = "商户订单号")
    private String merchantOrderNumber;
    /** 持卡人姓名 */
    @ApiModelProperty(value = "持卡人姓名")
    private String cardholderName;
    /** 银行编码 */
    @ApiModelProperty(value = "银行编码")
    private String bankCode;
    /** 银行卡号 */
    @ApiModelProperty(value = "银行卡号")
    private String bankNumber;
    /** 交易状态（0:等待代付 1:代付成功 2:代付超时 3:代付失败） */
    @ApiModelProperty(value = "交易状态（0:等待代付 1:代付成功 2:代付超时 3:代付失败）")
    private Integer tradeStatus;
    /** 支出金额 */
    @ApiModelProperty(value = "支出金额")
    private BigDecimal payout_amount;
    /** 支付时间 */
    @ApiModelProperty(value = "支付时间")
    private Date paymentTime;

    /** 金额备注 */
    @ApiModelProperty(value="金额备注")
    private String amountNote;
    /** 最大金额 */
    @ApiModelProperty(value="最大金额")
    private BigDecimal amountMax;
    /** 最小金额 */
    @ApiModelProperty(value="最小金额")
    private BigDecimal amountMin;
    /** 订单查询状态 */
    @ApiModelProperty(value="订单查询状态")
    private Integer orderStatus;
    /** 成功总额 */
    @ApiModelProperty(value="成功总额")
    private Long successTotal;
    /** 失败总额 */
    @ApiModelProperty(value="失败总额")
    private Long failTotal;
    /** 成功率 */
    @ApiModelProperty(value="成功率")
    private String successRatio;

    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPlatformOrderNumber() {
        return platformOrderNumber;
    }

    public void setPlatformOrderNumber(String platformOrderNumber) {
        this.platformOrderNumber = platformOrderNumber;
    }

    public String getMerchantOrderNumber() {
        return merchantOrderNumber;
    }

    public void setMerchantOrderNumber(String merchantOrderNumber) {
        this.merchantOrderNumber = merchantOrderNumber;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public BigDecimal getPayout_amount() {
        return payout_amount;
    }

    public void setPayout_amount(BigDecimal payout_amount) {
        this.payout_amount = payout_amount;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getAmountNote() {
        return amountNote;
    }

    public void setAmountNote(String amountNote) {
        this.amountNote = amountNote;
    }

    public BigDecimal getAmountMax() {
        return amountMax;
    }

    public void setAmountMax(BigDecimal amountMax) {
        this.amountMax = amountMax;
    }

    public BigDecimal getAmountMin() {
        return amountMin;
    }

    public void setAmountMin(BigDecimal amountMin) {
        this.amountMin = amountMin;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getSuccessTotal() {
        return successTotal;
    }

    public void setSuccessTotal(Long successTotal) {
        this.successTotal = successTotal;
    }

    public Long getFailTotal() {
        return failTotal;
    }

    public void setFailTotal(Long failTotal) {
        this.failTotal = failTotal;
    }

    public String getSuccessRatio() {
        return successRatio;
    }

    public void setSuccessRatio(String successRatio) {
        this.successRatio = successRatio;
    }
}
