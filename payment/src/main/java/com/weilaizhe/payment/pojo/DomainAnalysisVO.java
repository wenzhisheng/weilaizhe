package com.weilaizhe.payment.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description 域名解析
 * @dateTime 2019-06-07 20:58
 * @className com.weilaizhe.payment.pojo.DoaminAnalysisVO
 */
@ApiModel(value = "域名解析", description = "域名解析")
public class DomainAnalysisVO implements Serializable {

    private static final long serialVersionUID = -7628662959920991955L;
    /** 账号邮箱 */
    @ApiModelProperty(value="账号邮箱")
    private String emailAddress;
    /** 全球API键 */
    @ApiModelProperty(value="全球API键")
    private String globalAPIKey;
    /** 账号ID */
    @ApiModelProperty(value="账号ID")
    private String accountID;
    /** 记录类型 */
    @ApiModelProperty(value="记录类型")
    private String type;
    /** 服务器IP */
    @ApiModelProperty(value="服务器IP")
    private String content;
    /** 状态：active, pending, initializing, moved, deleted, deactivated */
    @ApiModelProperty(value="状态：active, pending, initializing, moved, deleted, deactivated")
    private String status;
    /** 生存时间(必须在120到2147483647秒之间，或1为自动) */
    @ApiModelProperty(value="生存时间(必须在120到2147483647秒之间，或1为自动)")
    private Integer ttl = 1;
    /** 优先权 */
    @ApiModelProperty(value="优先权")
    private int priority = 10;
    /** 代理方式(默认flase) */
    @ApiModelProperty(value="代理方式(默认flase)")
    private String proxied = "true";
    /** 缓冲时间 */
    @ApiModelProperty(value="缓冲时间")
    private Integer bufferTime;
    /** https开启 */
    @ApiModelProperty(value="https开启（默认开启on,off）")
    private String value = "on";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getGlobalAPIKey() {
        return globalAPIKey;
    }

    public void setGlobalAPIKey(String globalAPIKey) {
        this.globalAPIKey = globalAPIKey;
    }

    public Integer getBufferTime() {
        return bufferTime;
    }

    public void setBufferTime(Integer bufferTime) {
        this.bufferTime = bufferTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getProxied() {
        return proxied;
    }

    public void setProxied(String proxied) {
        this.proxied = proxied;
    }
}
