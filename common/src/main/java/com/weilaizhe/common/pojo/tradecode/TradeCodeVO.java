package com.weilaizhe.common.pojo.tradecode;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author: dameizi
 * @description: 交易码
 * @dateTime 2019-04-01 14:43
 * @className com.weilaizhe.common.pojo.tradecode.TradeCodeVO
 */
@ApiModel(value = "交易码", description = "交易码")
public class TradeCodeVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -4123274454207885312L;

    /** 交易码ID */
    @ApiModelProperty(value = "交易码ID")
    private BigInteger tradeCodeId;
    /** 商户Id */
    @ApiModelProperty(value = "商户Id")
    private BigInteger merchantId;
    /** 交易类型编码 */
    @ApiModelProperty(value = "交易类型编码")
    private String tradeTypeCode;
    /** 交易账号Id */
    @ApiModelProperty(value = "交易账号ID")
    private BigInteger tradeAccountId;
    /** 金额 */
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;
    /** 真实金额 */
    @ApiModelProperty(value = "真实金额")
    private BigDecimal actualAmount;
    /** 是否占用（0:未占用 1:占用） */
    @ApiModelProperty(value = "是否占用（0:未占用 1:占用）")
    private Integer isUse;
    /** 是否启用（0：禁用 1：启用） */
    @ApiModelProperty(value = "是否启用（0：禁用 1：启用）")
    private Integer isEnable;
    /** 占用时间 */
    @ApiModelProperty(value = "占用时间")
    private Date useTime;
    /** 图片名称 */
    @ApiModelProperty(value = "图片名称")
    private String pictureName;
    /** 图片地址 */
    @ApiModelProperty(value = "图片地址")
    private String picturePath;

    public BigInteger getTradeCodeId() {
        return tradeCodeId;
    }

    public void setTradeCodeId(BigInteger tradeCodeId) {
        this.tradeCodeId = tradeCodeId;
    }

    public BigInteger getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(BigInteger merchantId) {
        this.merchantId = merchantId;
    }

    public String getTradeTypeCode() {
        return tradeTypeCode;
    }

    public void setTradeTypeCode(String tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }

    public BigInteger getTradeAccountId() {
        return tradeAccountId;
    }

    public void setTradeAccountId(BigInteger tradeAccountId) {
        this.tradeAccountId = tradeAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
