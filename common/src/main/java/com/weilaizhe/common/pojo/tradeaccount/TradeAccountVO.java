package com.weilaizhe.common.pojo.tradeaccount;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author: dameizi
 * @description: 交易账号
 * @dateTime 2019-04-01 14:33
 * @className com.weilaizhe.common.pojo.tradeaccount.TradeAccountVO
 */
@ApiModel(value = "交易账号", description = "交易账号")
public class TradeAccountVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 635389270341442396L;

    /** 交易账号ID */
    @ApiModelProperty(value = "交易账号ID")
    private BigInteger tradeAccountId;
    /** 商户ID */
    @ApiModelProperty(value = "商户ID")
    private BigInteger merchantId;
    /** 交易类型编码 */
    @ApiModelProperty(value = "交易类型编码")
    private String tradeTypeCode;
    /** 交易账号 */
    @ApiModelProperty(value = "交易账号")
    private String tradeAccount;
    /** 单笔限额 */
    @ApiModelProperty(value = "单笔限额")
    private String quota;
    /** 是否启用（0:禁用 1:启用） */
    @ApiModelProperty(value = "是否启用（0:禁用 1:启用）")
    private Integer isEnable = 1;
    /** 上传编码钥匙 */
    @ApiModelProperty(value = "上传编码钥匙")
    private String uploadCodeKey;
    /** 商户交易账号 */
    @ApiModelProperty(value = "商户交易账号")
    private String merchantTradeAccout;
    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;

    public BigInteger getTradeAccountId() {
        return tradeAccountId;
    }

    public void setTradeAccountId(BigInteger tradeAccountId) {
        this.tradeAccountId = tradeAccountId;
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

    public String getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getUploadCodeKey() {
        return uploadCodeKey;
    }

    public void setUploadCodeKey(String uploadCodeKey) {
        this.uploadCodeKey = uploadCodeKey;
    }

    public String getMerchantTradeAccout() {
        return merchantTradeAccout;
    }

    public void setMerchantTradeAccout(String merchantTradeAccout) {
        this.merchantTradeAccout = merchantTradeAccout;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
