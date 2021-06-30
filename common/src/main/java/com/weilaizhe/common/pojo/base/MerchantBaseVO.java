package com.weilaizhe.common.pojo.base;

import com.weilaizhe.common.util.RedissonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author dameizi
 * @description 商户基础信息
 * @dateTime 2019-06-06 23:19
 * @className com.weilaizhe.common.pojo.base.MerchantBaseVO
 */
@ApiModel(value="商户基础信息",description="基础信息")
public class MerchantBaseVO extends BaseVO {

    /** 当前登录人 */
    @ApiModelProperty(value="当前登录人", hidden=true)
    private String currentUser = RedissonUtil.getContextMerchantInfo() == null ? null : RedissonUtil.getContextMerchantInfo().getAccount();

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}
