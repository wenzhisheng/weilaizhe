package com.weilaizhe.common.pojo.tradetype;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: dameizi
 * @description: 交易类型
 * @dateTime 2019-04-01 12:08
 * @className com.weilaizhe.common.pojo.TradeTypeVO
 */
@ApiModel(value = "交易类型", description = "交易类型")
public class TradeTypeVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -4432489858188063748L;

    /** 交易类型ID */
    @ApiModelProperty(value = "交易类型ID")
    private Integer tradeTypeId;
    /** 交易类型编码 */
    @ApiModelProperty(value = "交易类型编码")
    private String tradeTypeCode;
    /** 交易类型名称 */
    @ApiModelProperty(value = "交易类型名称")
    private String tradeTypeName;
    /** 交易类型图标 */
    @ApiModelProperty(value = "交易类型图标")
    private String tradeTypeIcon;
    /** 交易类型排序 */
    @ApiModelProperty(value = "交易类型排序")
    private Integer tradeTypeSort;
    /** 是否推荐（0：不推荐 1：推荐） */
    @ApiModelProperty(value = "是否推荐（0：不推荐 1：推荐）")
    private Integer isRecommend;
    /** 是否启用（0：禁用 1：启用） */
    @ApiModelProperty(value = "是否启用（0：禁用 1：启用）")
    private Integer isEnable;
    /** 单笔限额 */
    @ApiModelProperty(value = "单笔限额")
    private String singleLimit;
    /** 当天限额 */
    @ApiModelProperty(value = "当天限额")
    private BigDecimal dailyLimit;
    /** 系统类型名称 */
    @ApiModelProperty(value = "系统类型名称")
    private String systemTypeName;

    /** 总订单 **/
    @ApiModelProperty(value="总订单")
    private String sumOrder;
    /** 累计金额 **/
    @ApiModelProperty(value="累计金额")
    private String sumMoney;
    /** 成功率 **/
    @ApiModelProperty(value="成功率")
    private String successRatio = "60";

    public Integer getTradeTypeId() {
        return tradeTypeId;
    }

    public void setTradeTypeId(Integer tradeTypeId) {
        this.tradeTypeId = tradeTypeId;
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

    public String getTradeTypeIcon() {
        return tradeTypeIcon;
    }

    public void setTradeTypeIcon(String tradeTypeIcon) {
        this.tradeTypeIcon = tradeTypeIcon;
    }

    public Integer getTradeTypeSort() {
        return tradeTypeSort;
    }

    public void setTradeTypeSort(Integer tradeTypeSort) {
        this.tradeTypeSort = tradeTypeSort;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
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

    public String getSystemTypeName() {
        return systemTypeName;
    }

    public void setSystemTypeName(String systemTypeName) {
        this.systemTypeName = systemTypeName;
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
}
