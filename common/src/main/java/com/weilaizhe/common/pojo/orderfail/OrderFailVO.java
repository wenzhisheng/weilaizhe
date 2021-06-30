package com.weilaizhe.common.pojo.orderfail;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author: dameizi
 * @description: 失败订单
 * @dateTime 2019-04-01 15:15
 * @className com.weilaizhe.common.pojo.orderfail.OrderFailVO
 */
public class OrderFailVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -9137900882414208587L;

    /** 失败订单ID */
    @ApiModelProperty(value = "失败订单ID")
    private Integer failOrderId;
    /** 商户ID */
    @ApiModelProperty(value = "商户ID")
    private Integer merchantId;
    /** 商户名称 */
    @ApiModelProperty(value = "商户名称")
    private String merchantName;
    /** 金额 */
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;
    /** 昵称 */
    @ApiModelProperty(value = "昵称")
    private String nickname;
    /** 失败状态（0：失败单 1：已补单） */
    @ApiModelProperty(value = "失败状态（0：失败单 1：已补单）")
    private Integer failOrderState;
    /** 交易账号 */
    @ApiModelProperty(value = "交易账号")
    private String tradeAccount;
    /** 交易类型编码 */
    @ApiModelProperty(value = "交易类型编码")
    private String tradeTypeCode;
    /** 交易类型 */
    @ApiModelProperty(value = "交易类型名称")
    private String tradeTypeName;

    public Integer getFailOrderId() {
        return failOrderId;
    }

    public void setFailOrderId(Integer failOrderId) {
        this.failOrderId = failOrderId;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getFailOrderState() {
        return failOrderState;
    }

    public void setFailOrderState(Integer failOrderState) {
        this.failOrderState = failOrderState;
    }

    public String getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
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
