package com.weilaizhe.common.pojo.base;

import com.weilaizhe.common.util.DateUtil;
import com.weilaizhe.common.util.RedissonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @dateTime 2019-03-26 23:49
 * @author: dameizi
 * @description: 基础信息
 */
@ApiModel(value="基础信息",description="基础信息")
public class BaseVO implements Serializable {

    private static final long serialVersionUID = 6453314448843993576L;

    /** 创建者 */
    @ApiModelProperty(value="创建者", hidden=true)
    private String creator;
    /** 修改者 */
    @ApiModelProperty(value="修改者", hidden=true)
    private String mender;
    /** 创建时间 */
    @ApiModelProperty(value="创建时间", hidden=true)
    private Date gmtCreate;
    /** 修改时间 */
    @ApiModelProperty(value="修改时间", hidden=true)
    private Date gmtModified;
    /** 开始时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss" ,iso=DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(value="开始日期 yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /** 结束时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss",iso=DateTimeFormat.ISO.DATE_TIME)
    @ApiModelProperty(value="结束日期 yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /** 批量操作数组 */
    @ApiModelProperty(value = "批量操作数组", hidden=true)
    private Integer[] ids;
    /** 分表表后缀 */
    @ApiModelProperty(value="分表表后缀", hidden=true)
    private String suffix = DateUtil.dateToString(new Date(),"yyyyMM");

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getMender() {
        return mender;
    }

    public void setMender(String mender) {
        this.mender = mender;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
