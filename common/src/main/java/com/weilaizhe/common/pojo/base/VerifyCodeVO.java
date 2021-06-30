package com.weilaizhe.common.pojo.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: dameizi
 * @description: 验证码
 * @dateTime 2019-03-29 14:20
 * @className com.weilaizhe.common.pojo.base.VerifyCodeVO
 */
//无数据属性不显示
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="验证码",description="验证码")
public class VerifyCodeVO implements Serializable {

    private static final long serialVersionUID = -5287648130810732439L;
    /** 验证码  ***/
    @ApiModelProperty(value="验证码")
    private String verifyCode;

    @Override
    public String toString() {
        return "VerifyCodeVO{" +
                "verifyCode='" + verifyCode + '\'' +
                '}';
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
