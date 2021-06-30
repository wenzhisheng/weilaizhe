package com.weilaizhe.payment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description 域名解析
 * @dateTime 2019-06-07 16:38
 * @className com.weilaizhe.payment.pojo.CloudflareVO
 */
@ApiModel(value = "域名解析", description = "域名解析")
public class AddDomainResultVO implements Serializable {

    private static final long serialVersionUID = 7494057948782598443L;
    /** 全球API键 */
    @ApiModelProperty(value="全球API键")
    private String globalAPIKey;
    /** 原始证书键 */
    @ApiModelProperty(value="原始证书键")
    private String originCAKey;
    /** 账号邮箱 */
    @ApiModelProperty(value="账号邮箱")
    private String emailAddress;
    /** 地区ID */
    @ApiModelProperty(value="地区ID")
    private String zoneID;
    /** 账号ID */
    @ApiModelProperty(value="账号ID")
    private String accountID;
    /** 缓冲时间 */
    @ApiModelProperty(value="缓冲时间")
    private Integer bufferTime;

    public Integer getBufferTime() {
        return bufferTime;
    }

    public void setBufferTime(Integer bufferTime) {
        this.bufferTime = bufferTime;
    }

    public String getGlobalAPIKey() {
        return globalAPIKey;
    }

    public void setGlobalAPIKey(String globalAPIKey) {
        this.globalAPIKey = globalAPIKey;
    }

    public String getOriginCAKey() {
        return originCAKey;
    }

    public void setOriginCAKey(String originCAKey) {
        this.originCAKey = originCAKey;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getZoneID() {
        return zoneID;
    }

    public void setZoneID(String zoneID) {
        this.zoneID = zoneID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
}
