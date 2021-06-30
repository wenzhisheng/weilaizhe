package com.weilaizhe.common.pojo.orderexception;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: dameizi
 * @description: 异常订单
 * @dateTime 2019-04-01 15:02
 * @className com.weilaizhe.common.pojo.orderexception.OrderExceptionVO
 */
@ApiModel(value = "异常订单", description = "异常订单")
public class OrderExceptionVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 8664087941334122634L;

    /** 异常订单ID */
    @ApiModelProperty(value = "异常订单ID")
    private Integer exceptionOrderId;
    /** 商户ID */
    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;
    /** 商户名称 */
    @ApiModelProperty(value = "商户名称")
    private String merchantName;
    /** 商户订单号 */
    @ApiModelProperty(value = "商户订单号")
    private String outTradeNo;
    /** 金额 */
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;
    /** 交易状态(0:等待支付1:交易关闭 2:回调结束) */
    @ApiModelProperty(value = "交易状态(0:等待支付1:交易关闭 2:回调结束)")
    private Integer transactionStatus;
    /** 处理状态(0:未处理 1:已处理) */
    @ApiModelProperty(value = "处理状态(0:未处理 1:已处理)")
    private Integer handleStatus;
    /** 系统回复 */
    @ApiModelProperty(value = "系统回复")
    private String systemReply;
    /** 交易类型编码 */
    @ApiModelProperty(value = "交易类型编码")
    private String tradeTypeCode;
    /** 交易类型名称 */
    @ApiModelProperty(value = "交易类型名称")
    private String tradeTypeName;
    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;
    /** 交易时间 */
    @ApiModelProperty(value = "交易时间")
    private Date transactionTime;

    public Integer getExceptionOrderId() {
        return exceptionOrderId;
    }

    public void setExceptionOrderId(Integer exceptionOrderId) {
        this.exceptionOrderId = exceptionOrderId;
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

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(Integer transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getSystemReply() {
        return systemReply;
    }

    public void setSystemReply(String systemReply) {
        this.systemReply = systemReply;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

}
