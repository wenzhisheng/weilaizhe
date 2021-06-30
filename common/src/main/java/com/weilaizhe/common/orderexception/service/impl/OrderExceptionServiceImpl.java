package com.weilaizhe.common.orderexception.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.orderexception.dao.IOrderExceptionDao;
import com.weilaizhe.common.orderexception.service.IOrderExceptionService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.orderexception.OrderExceptionVO;
import com.weilaizhe.common.util.CommonUtil;
import com.weilaizhe.common.util.RedissonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: dameizi
 * @description: 异常订单业务层
 * @dateTime 2019-04-04 19:29
 * @className com.weilaizhe.common.orderexception.service.impl.OrderExceptionServiceImpl
 */
@Service
public class OrderExceptionServiceImpl implements IOrderExceptionService {

    @Autowired
    private IOrderExceptionDao iOrderExceptionDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-15 23:46
     * @description: 处理异常订单
     * @param: [exceptionOrderVO]
     * @return: int
     */
    @Override
    public int update(OrderExceptionVO exceptionOrderVO) {
        // id不能为空
        CommonUtil.integerEmptyVerify(exceptionOrderVO.getExceptionOrderId(), CommonConst.ID_NOT_EMPTY);
        return iOrderExceptionDao.update(exceptionOrderVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-15 23:46
     * @description: 删除异常订单
     * @param: [exceptionOrderVO]
     * @return: int
     */
    @Override
    public int delete(OrderExceptionVO exceptionOrderVO) {
        // id不能为空
        CommonUtil.paramEmptyVerify(String.valueOf(exceptionOrderVO.getIds()), CommonConst.ID_NOT_EMPTY);
        return iOrderExceptionDao.delete(exceptionOrderVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 19:54
     * @description: 分页查询异常订单
     * @param: [orderExceptionVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.orderexception.OrderExceptionVO>
     */
    @Override
    public IPage<OrderExceptionVO> page(OrderExceptionVO orderExceptionVO, PageVO pageVO) {
        Page<OrderExceptionVO> page = new Page<OrderExceptionVO>(pageVO.getPageNo(), pageVO.getPageSize());
        return iOrderExceptionDao.page(page, orderExceptionVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 19:42
     * @description: 提交异常订单
     * @param: [orderExceptionVO]
     * @return: int
     */
    @Override
    public int insert(OrderExceptionVO orderExceptionVO) {
        MerchantVO merchantInfo = RedissonUtil.getContextMerchantInfo();
        orderExceptionVO.setMerchantId(merchantInfo.getMerchantId());
        orderExceptionVO.setMerchantName(merchantInfo.getMerchantName());
        orderExceptionVO.setTransactionStatus(0);
        return iOrderExceptionDao.insert(orderExceptionVO);
    }

}
