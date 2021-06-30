package com.weilaizhe.common.pojo.index;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author dameizi
 * @description TODO
 * @dateTime 2019-05-22 21:04
 * @className com.weilaizhe.common.pojo.index.IndexBalanceVO
 */
public class IndexMoneyVO implements Serializable {

    private static final long serialVersionUID = 6847669864400653495L;
    /** 今日收款总额 */
    @ApiModelProperty(value="今日收款总额")
    private BigDecimal todayReceiptMoney;
    /** 支付获利 */
    @ApiModelProperty(value="支付获利")
    private BigDecimal paymentProfit;
    /** 总获利（支付、支出、提现） */
    @ApiModelProperty(value="总获利（支付、支出、提现）")
    private BigDecimal totalProfit;
    /** 当前余额 */
    @ApiModelProperty(value="当前余额")
    private BigDecimal currentBlance;
    /** 累计收款 */
    @ApiModelProperty(value="累计收款")
    private BigDecimal totalMoney;

    public BigDecimal getTodayReceiptMoney() {
        return todayReceiptMoney;
    }

    public void setTodayReceiptMoney(BigDecimal todayReceiptMoney) {
        this.todayReceiptMoney = todayReceiptMoney;
    }

    public BigDecimal getPaymentProfit() {
        return paymentProfit;
    }

    public void setPaymentProfit(BigDecimal paymentProfit) {
        this.paymentProfit = paymentProfit;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getCurrentBlance() {
        return currentBlance;
    }

    public void setCurrentBlance(BigDecimal currentBlance) {
        this.currentBlance = currentBlance;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}
