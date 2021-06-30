package com.weilaizhe.common.pojo.index;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dameizi
 * @description 订单笔数
 * @dateTime 2019-05-22 21:23
 * @className com.weilaizhe.common.pojo.index.IndexStrokeCountVO
 */
public class StrokeCountVO implements Serializable {

    private static final long serialVersionUID = -1755376217593292029L;
    /** 日期 */
    @ApiModelProperty(value="日期")
    private String x;
    /** 订单数量 */
    @ApiModelProperty(value="订单数量")
    private int y;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
