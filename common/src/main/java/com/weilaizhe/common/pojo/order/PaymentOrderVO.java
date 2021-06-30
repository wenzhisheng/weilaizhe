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
 * @description: 支付订单
 * @dateTime 2019-04-01 13:24
 * @className com.weilaizhe.common.pojo.order.OrderVO
 */
@ApiModel(value = "支付订单", description = "支付订单")
public class PaymentOrderVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 3098662044299894198L;

    /** 订单ID */
    @ApiModelProperty(value = "订单ID")
    private BigInteger orderId;
    /** 商户ID */
    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;
    /** 商户名称 */
    @ApiModelProperty(value = "商户名称")
    private String merchantName;
    /** 平台订单号 */
    @ApiModelProperty(value = "平台订单号")
    private String tradeNo;
    /** 商户订单号 */
    @ApiModelProperty(value = "商户订单号")
    private String outTradeNo;
    /** 交易类型编码 */
    @ApiModelProperty(value = "交易类型编码")
    private String tradeTypeCode;
    /** 交易类型名称 */
    @ApiModelProperty(value = "交易类型名称")
    private String tradeTypeName;
    /** 交易账号 */
    @ApiModelProperty(value = "交易账号")
    private String tradeAccount;
    /** 回调地址 */
    @ApiModelProperty(value = "回调地址")
    private String notifyUrl;
    /** 成功通知 */
    @ApiModelProperty(value = "成功通知")
    private String returnUrl;
    /** 交易状态（0:等待支付 1:支付成功 2:支付超时 3:支付失败） */
    @ApiModelProperty(value = "交易状态（0:等待支付 1:支付成功 2:支付超时 3:支付失败）")
    private Integer tradeStatus;
    /** 回调状态（0:等待回调 1:回调成功 2:回调结束 3:回调失败） */
    @ApiModelProperty(value = "回调状态（0:等待回调 1:回调成功 2:回调结束 3:回调失败）")
    private Integer callbackStatus;
    /** 交易金额 */
    @ApiModelProperty(value = "交易金额")
    private BigDecimal tradeAmount;
    /** 支付金额 */
    @ApiModelProperty(value = "支付金额")
    private BigDecimal totalAmount;
    /** 支付时间 */
    @ApiModelProperty(value = "支付时间")
    private Date tradeTime;
    /** 回调时间 */
    @ApiModelProperty(value = "回调时间")
    private Date callbackTime;

    /** 金额备注 */
    @ApiModelProperty(value="金额备注")
    private String amountNote;
    /** 最大金额 */
    @ApiModelProperty(value="最大金额")
    private BigDecimal amountMax;
    /** 最小金额 */
    @ApiModelProperty(value="最小金额")
    private BigDecimal amountMin;
    /** 订单状态查询 */
    @ApiModelProperty(value="订单状态查询")
    private Integer orderStatus;
    /** 订单状态列表 */
    @ApiModelProperty(value="订单状态列表")
    private Integer orderStatus2;
    /** 成功总额 */
    @ApiModelProperty(value="成功总额")
    private Long successTotal;
    /** 失败总额 */
    @ApiModelProperty(value="失败总额")
    private Long failTotal;
    /** 成功率 */
    @ApiModelProperty(value="成功率")
    private String successRatio;

    public Integer getOrderStatus2() {
        return orderStatus2;
    }

    public void setOrderStatus2(Integer orderStatus2) {
        this.orderStatus2 = orderStatus2;
    }

    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
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

    public String getTradeTypeCode() {
        return tradeTypeCode;
    }

    public void setTradeTypeCode(String tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }

    public String getTradeTypeName() {
        return tradeTypeName;
    }

    public void setTradeTypeName(String tradeTypeName) {
        this.tradeTypeName = tradeTypeName;
    }

    public String getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public Integer getCallbackStatus() {
        return callbackStatus;
    }

    public void setCallbackStatus(Integer callbackStatus) {
        this.callbackStatus = callbackStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public Date getCallbackTime() {
        return callbackTime;
    }

    public void setCallbackTime(Date callbackTime) {
        this.callbackTime = callbackTime;
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
