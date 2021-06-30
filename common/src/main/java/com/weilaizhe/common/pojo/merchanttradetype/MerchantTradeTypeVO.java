package com.weilaizhe.common.pojo.merchanttradetype;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author: dameizi
 * @description: 商户交易类型
 * @dateTime 2019-04-01 11:57
 * @className com.weilaizhe.common.pojo.merchanttradetype.MerchantReceiptType
 */
@ApiModel(value = "商户交易类型", description = "商户交易类型")
public class MerchantTradeTypeVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 40837843157323931L;

    /** 商户交易类型ID */
    @ApiModelProperty(value = "商户交易类型ID")
    private Integer merchantTradeTypeId;
    /** 商户ID */
    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;
    /** 交易类型编码 */
    @ApiModelProperty(value = "交易类型编码")
    private String tradeTypeCode;
    /** 交易类型名称 */
    @ApiModelProperty(value = "交易类型名称")
    private String tradeTypeName;
    /** 是否启用（0：禁用 1启用） */
    @ApiModelProperty(value = "是否启用（0：禁用 1启用）")
    private Integer isEnable;

    /** 交易类型图标 */
    @ApiModelProperty(value = "交易类型图标")
    private String tradeTypeIcon;
    /** 单笔限额 */
    @ApiModelProperty(value = "单笔限额")
    private String singleLimit;
    /** 当天限额 */
    @ApiModelProperty(value = "当天限额")
    private BigDecimal dailyLimit;
    /** 总订单 **/
    @ApiModelProperty(value="总订单")
    private String sumOrder;
    /** 累计金额 **/
    @ApiModelProperty(value="累计金额")
    private String sumMoney;
    /** 成功率 **/
    @ApiModelProperty(value="成功率")
    private String successRatio = "60";

    public String getTradeTypeIcon() {
        return tradeTypeIcon;
    }

    public void setTradeTypeIcon(String tradeTypeIcon) {
        this.tradeTypeIcon = tradeTypeIcon;
    }

    public String getSingleLimit() {
        return singleLimit;
    }

    public void setSingleLimit(String singleLimit) {
        this.singleLimit = singleLimit;
    }

    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(BigDecimal dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public String getSumOrder() {
        return sumOrder;
    }

    public void setSumOrder(String sumOrder) {
        this.sumOrder = sumOrder;
    }

    public String getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(String sumMoney) {
        this.sumMoney = sumMoney;
    }

    public String getSuccessRatio() {
        return successRatio;
    }

    public void setSuccessRatio(String successRatio) {
        this.successRatio = successRatio;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getMerchantTradeTypeId() {
        return merchantTradeTypeId;
    }

    public void setMerchantTradeTypeId(Integer merchantTradeTypeId) {
        this.merchantTradeTypeId = merchantTradeTypeId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
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
}
