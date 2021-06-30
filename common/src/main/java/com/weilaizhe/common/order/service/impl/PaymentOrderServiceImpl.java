package com.weilaizhe.common.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.order.dao.IPaymentOrderDao;
import com.weilaizhe.common.order.service.IPaymentOrderService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
import com.weilaizhe.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Set;

/**
 * @author: dameizi
 * @description: 支付订单业务层
 * @dateTime 2019-04-04 13:55
 * @className com.weilaizhe.common.order.service.impl.PaymentOrderServiceImpl
 */
@Service
@Transactional
public class PaymentOrderServiceImpl implements IPaymentOrderService {

    @Autowired
    private IPaymentOrderDao iPaymentOrderDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-10 0:28
     * @description: 获取订单
     * @param: [paymentOrderVO, tableKey]
     * @return: com.weilaizhe.common.pojo.order.PaymentOrderVO
     */
    @Override
    public PaymentOrderVO getOrder(PaymentOrderVO paymentOrderVO, String tableKey) {
        return iPaymentOrderDao.getPaymentOrder(paymentOrderVO, tableKey);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-06 17:33
     * @description: 商户订单重复排查
     * @param: [out_trade_no]
     * @return: int
     */
    @Override
    public int repeatOrderSubmission(String out_trade_no, Integer app_id) {
        PaymentOrderVO paymentOrderVO = new PaymentOrderVO();
        paymentOrderVO.setOutTradeNo(out_trade_no);
        paymentOrderVO.setMerchantId(Integer.valueOf(app_id));
        return iPaymentOrderDao.repeatOrderSubmission(paymentOrderVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:44
     * @description: 更新订单
     * @param: [paymentOrderVO]
     * @return: int
     */
    @Override
    public int updateOrder(PaymentOrderVO paymentOrderVO, String tableKey) {
        return iPaymentOrderDao.updateOrder(paymentOrderVO, tableKey);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:06
     * @description: 订单分页查询
     * @param: [orderVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.order.PaymentOrderVO>
     */
    @Override
    public IPage<PaymentOrderVO> pageOrder(PaymentOrderVO paymentOrderVO, PageVO pageVO) {
        //订单分表键
        Set<String> tableSet = DateUtil.startAndEnd(paymentOrderVO.getStartTime(), paymentOrderVO.getEndTime(), DateUtil.generateCurrentTableKeys());
        //mybatis plus分页
        Page<PaymentOrderVO> page = new Page<PaymentOrderVO>(pageVO.getPageNo(), pageVO.getPageSize());
        return iPaymentOrderDao.pageOrder(page, paymentOrderVO, tableSet);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 16:09
     * @description: 新增订单
     * @param: [paymentOrderVO, tableKey]
     * @return: int
     */
    @Override
    public int insertOrder(PaymentOrderVO paymentOrderVO, String tableKey) {
        return iPaymentOrderDao.insertOrder(paymentOrderVO, tableKey);
    }


}
