package com.weilaizhe.common.pojo.index;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author dameizi
 * @description TODO
 * @dateTime 2019-05-22 21:22
 * @className com.weilaizhe.common.pojo.index.IndexOrderVO
 */
public class IndexOrderVO implements Serializable {

    private static final long serialVersionUID = -5500438210073607637L;
    /** 订单总数 */
    @ApiModelProperty(value="订单总数")
    private Integer totalOrder;
    /** 订单总数 */
    @ApiModelProperty(value="订单总数")
    private List<StrokeCountVO> lastFifteenDaysOrder;
    /** 今天订单数量 */
    @ApiModelProperty(value="今天订单量")
    private Integer todayOrder;

    public Integer getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Integer totalOrder) {
        this.totalOrder = totalOrder;
    }

    public List<StrokeCountVO> getLastFifteenDaysOrder() {
        return lastFifteenDaysOrder;
    }

    public void setLastFifteenDaysOrder(List<StrokeCountVO> lastFifteenDaysOrder) {
        this.lastFifteenDaysOrder = lastFifteenDaysOrder;
    }

    public Integer getTodayOrder() {
        return todayOrder;
    }

    public void setTodayOrder(Integer todayOrder) {
        this.todayOrder = todayOrder;
    }
}
