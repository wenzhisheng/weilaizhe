package com.weilaizhe.common.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.order.dao.IPayoutOrderDao;
import com.weilaizhe.common.order.service.IPayoutOrderService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
import com.weilaizhe.common.pojo.order.PayoutOrderVO;
import com.weilaizhe.common.pojo.pay.PayoutVO;
import com.weilaizhe.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Set;

/**
 * @author: dameizi
 * @description: 支出订单业务层
 * @dateTime 2019-04-04 13:55
 * @className com.weilaizhe.common.order.service.impl.PayoutOrderServiceImpl
 */
@Service
public class PayoutOrderServiceImpl implements IPayoutOrderService {

    @Autowired
    private IPayoutOrderDao iPayoutOrderDao;

    @Override
    public int repeatOrderSubmission(String out_trade_no, String auth_code) {
        PayoutOrderVO payoutOrderVO = new PayoutOrderVO();
        payoutOrderVO.setMerchantOrderNumber(out_trade_no);
        payoutOrderVO.setMerchantCode(auth_code);
        return iPayoutOrderDao.repeatOrderSubmission(payoutOrderVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 17:07
     * @description: 更新订单
     * @param: [payoutOrderVO]
     * @return: int
     */
    @Override
    public int updateOrder(PayoutOrderVO payoutOrderVO) {
        return iPayoutOrderDao.updateOrder(payoutOrderVO, payoutOrderVO.getSuffix());
    }

    @Override
    public IPage<PayoutOrderVO> pageOrder(PayoutOrderVO payoutOrderVO, PageVO pageVO) {
        //订单分表键
        Set<String> tableSet = DateUtil.startAndEnd(payoutOrderVO.getStartTime(), payoutOrderVO.getEndTime(), DateUtil.generateCurrentTableKeys());
        //mybatis plus分页
        Page<PayoutOrderVO> page = new Page<PayoutOrderVO>(pageVO.getPageNo(), pageVO.getPageSize());
        return iPayoutOrderDao.pageOrder(page, payoutOrderVO, tableSet);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-04 17:09
     * @description: 新增订单
     * @param: [payoutOrderVO, tableKey]
     * @return: int
     */
    @Override
    public int insertOrder(PayoutOrderVO payoutOrderVO, String tableKey) {
        return iPayoutOrderDao.insertOrder(payoutOrderVO, tableKey);
    }
}
