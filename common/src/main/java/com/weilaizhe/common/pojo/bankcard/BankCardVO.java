package com.weilaizhe.common.pojo.bankcard;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author dameizi
 * @description 银行号
 * @dateTime 2019-04-16 17:38
 * @className com.weilaizhe.common.pojo.bankcard.BankCardVO
 */
@ApiModel(value = "银行号", description = "银行号")
public class BankCardVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -1604929361211273751L;

    /** 银行卡ID */
    @ApiModelProperty(value = "银行卡ID")
    private BigInteger bankCardId;
    /** 银行编码 */
    @ApiModelProperty(value = "银行编码")
    private String bankCode;
    /** 银行名称 */
    @ApiModelProperty(value = "银行名称")
    private String bankName;
    /** 是否借记卡（0：信用卡 1：借记卡） */
    @ApiModelProperty(value = "是否借记卡（0：信用卡 1：借记卡）")
    private Integer isDebit;
    /** 银行排序 */
    @ApiModelProperty(value = "银行排序")
    private Integer bankSort;

    public Integer getBankSort() {
        return bankSort;
    }

    public void setBankSort(Integer bankSort) {
        this.bankSort = bankSort;
    }

    public BigInteger getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(BigInteger bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getIsDebit() {
        return isDebit;
    }

    public void setIsDebit(Integer isDebit) {
        this.isDebit = isDebit;
    }
}
