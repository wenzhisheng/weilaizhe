package com.weilaizhe.common.orderfail.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.orderfail.dao.IOrderFailDao;
import com.weilaizhe.common.orderfail.service.IOrderFailService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.orderfail.OrderFailVO;
import com.weilaizhe.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: dameizi
 * @description: 失败订单业务层
 * @dateTime 2019-04-16 12:24
 * @className com.weilaizhe.common.orderfail.service.impl.OrderFailServiceImpl
 */
@Service
public class OrderFailServiceImpl implements IOrderFailService {

    @Autowired
    private IOrderFailDao iOrderFailDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:19
     * @description: 更新失败订单
     * @param: [orderFailVO]
     * @return: int
     */
    @Override
    public int update(OrderFailVO orderFailVO) {
        // id不能为空
        CommonUtil.integerEmptyVerify(orderFailVO.getFailOrderId(), CommonConst.ID_NOT_EMPTY);
        return iOrderFailDao.update(orderFailVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:18
     * @description: 删除失败订单
     * @param: [orderFailVO]
     * @return: int
     */
    @Override
    public int delete(OrderFailVO orderFailVO) {
        // id不能为空
        CommonUtil.paramEmptyVerify(String.valueOf(orderFailVO.getIds()), CommonConst.ID_NOT_EMPTY);
        return iOrderFailDao.delete(orderFailVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:19
     * @description: 分页查询失败订单
     * @param: [orderFailVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.orderfail.OrderFailVO>
     */
    @Override
    public IPage<OrderFailVO> page(OrderFailVO orderFailVO, PageVO pageVO) {
        Page<OrderFailVO> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());
        return iOrderFailDao.page(page, orderFailVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 13:18
     * @description: 新增失败订单
     * @param: [orderFailVO]
     * @return: int
     */
    @Override
    public int insert(OrderFailVO orderFailVO) {
        return iOrderFailDao.insert(orderFailVO);
    }
}
