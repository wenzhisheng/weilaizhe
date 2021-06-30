package com.weilaizhe.common.pojo.adminloginlog;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.weilaizhe.common.pojo.base.AdminBaseVO;
import com.weilaizhe.common.pojo.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author: dameizi
 * @description:  管理员登录日志
 * @dateTime 2019-04-01 13:11
 * @className com.weilaizhe.common.pojo.adminloginlog.AdminLoginLog
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "管理员登录日志", description = "管理员登录日志")
public class AdminLoginLogVO extends AdminBaseVO implements Serializable {

    private static final long serialVersionUID = 8232541932059438165L;

    /** 管理员登录日志Id */
    @ApiModelProperty(value = "登录日志ID")
    private BigInteger loginLogId;
    /** 管理员账号 */
    @ApiModelProperty(value = "管理员账号")
    private String adminAccount;
    /** 登录IP */
    @ApiModelProperty(value = "登录IP")
    private String loginIp;
    /** 登录真实地址 */
    @ApiModelProperty(value = "登录真实地址")
    private String loginRealAddress;
    /** 登录时间 */
    @ApiModelProperty(value = "登录时间")
    private Date loginTime;

    @Override
    public String toString() {
        return "AdminLoginLogVO{" +
                "loginLogId=" + loginLogId +
                ", adminAccount='" + adminAccount + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", loginRealAddress='" + loginRealAddress + '\'' +
                ", loginTime=" + loginTime +
                '}';
    }

    public BigInteger getLoginLogId() {
        return loginLogId;
    }

    public void setLoginLogId(BigInteger loginLogId) {
        this.loginLogId = loginLogId;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
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
