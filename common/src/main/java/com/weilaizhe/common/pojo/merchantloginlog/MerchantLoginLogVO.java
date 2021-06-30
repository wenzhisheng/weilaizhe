package com.weilaizhe.common.pojo.merchantloginlog;

import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author: dameizi
 * @description: 商户登录日志
 * @dateTime 2019-04-01 13:00
 * @className com.weilaizhe.common.pojo.merchantloginlog.merchantloginlog
 */
@ApiModel(value = "商户登录日志", description = "商户登录日志")
public class MerchantLoginLogVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 5202084967990964620L;

    /** 登录日志ID */
    @ApiModelProperty(value = "登录日志ID")
    private BigInteger loginLogId;
    /** 商户账号 */
    @ApiModelProperty(value = "商户账号")
    private String merchantAccount;
    /** 登录IP */
    @ApiModelProperty(value = "登录IP")
    private String loginIp;
    /** 登录真实地址 */
    @ApiModelProperty(value = "登录真实地址")
    private String loginRealAddress;
    /** 登录时间 */
    @ApiModelProperty(value = "登录时间")
    private Date loginTime;

    public BigInteger getLoginLogId() {
        return loginLogId;
    }

    public void setLoginLogId(BigInteger loginLogId) {
        this.loginLogId = loginLogId;
    }

    public String getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(String merchantAccount) {
        this.merchantAccount = merchantAccount;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginRealAddress() {
        return loginRealAddress;
    }

    public void setLoginRealAddress(String loginRealAddress) {
        this.loginRealAddress = loginRealAddress;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
