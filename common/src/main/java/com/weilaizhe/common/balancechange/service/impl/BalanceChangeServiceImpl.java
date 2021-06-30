package com.weilaizhe.common.balancechange.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.balancechange.dao.IBalanceChangeDao;
import com.weilaizhe.common.balancechange.service.IBalanceChangeService;
import com.weilaizhe.common.exception.DescribeException;
import com.weilaizhe.common.merchantbalance.dao.IMerchantBalanceDao;
import com.weilaizhe.common.pojo.balancechange.BalanceChangeVO;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.merchantbalance.MerchantBalanceVO;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
import com.weilaizhe.common.util.DateUtil;
import com.weilaizhe.common.util.OfficeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: dameizi
 * @description: 帐变业务层
 * @dateTime 2019-04-07 21:37
 * @className com.weilaizhe.common.balancechange.service.impl.BalanceChangeServiceImpl
 */
@Service
@Transactional
public class BalanceChangeServiceImpl implements IBalanceChangeService {

    @Autowired
    private IBalanceChangeDao iBalanceChangeDao;
    @Autowired
    private IMerchantBalanceDao iMerchantBalanceDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 22:00
     * @description: 导出资金明细
     * @param: [response, balanceChangeVO]
     * @return: java.lang.Object
     */
    @Override
    public void exportExcel(BalanceChangeVO balanceChangeVO, HttpServletResponse response) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        List<BalanceChangeVO> listVO = iBalanceChangeDao.list(balanceChangeVO);
        if (listVO.size() > 0){
            for (int i=0; i<listVO.size(); i++){
                // 获取资金明细Map
                dataList.add(getBalanceChangeMap(listVO, i));
            }
            String excelName = MessageFormat.format("{0}{1}", listVO.get(0).getMerchantName(), "资金明细");
            String sheetName = MessageFormat.format("{0}{1}", "帐变", DateUtil.getYyyyMMdd());
            String[] headers = {"编号","商户账号","商户名称","平台订单号","商户订单号","帐变类型","原始金额","结算金额","手续费","交易金额","交易类型","交易账号","交易名称","创建时间"};
            OfficeUtil.exportExcel(response, dataList, excelName, sheetName, headers);
        }else{
            throw new DescribeException("无数据可导出", 0);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 21:01
     * @description: 列表获取资金明细
     * @param: [balanceChangeVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.balancechange.BalanceChangeVO>
     */
    @Override
    public List<BalanceChangeVO> list(BalanceChangeVO balanceChangeVO) {
        return iBalanceChangeDao.list(balanceChangeVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 17:04
     * @description: 分页查询资金明细
     * @param: [balanceChangeVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.balancechange.BalanceChangeVO>
     */
    @Override
    public IPage<BalanceChangeVO> page(BalanceChangeVO balanceChangeVO, PageVO pageVO) {
        Page<BalanceChangeVO> page = new Page<>(pageVO.getPageNo(), pageVO.getPageSize());
        return iBalanceChangeDao.page(page, balanceChangeVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 17:04
     * @description: 支付产生帐变
     * @param: [payOrder, merchantVO]
     * @return: int
     */
    @Override
    public int insertPayment(PaymentOrderVO payOrder, MerchantVO merchantVO) {
        // 获取商户余额
        MerchantBalanceVO merchantBalance = new MerchantBalanceVO();
        merchantBalance.setMerchantId(merchantVO.getMerchantId());
        MerchantBalanceVO merchantBalanceVO = iMerchantBalanceDao.getMerchantBalance(merchantBalance);
        BigDecimal paymentAmount = payOrder.getTotalAmount();
        // 写入帐变
        BalanceChangeVO balanceChangeVO = new BalanceChangeVO();
        balanceChangeVO.setMerchantId(merchantVO.getMerchantId());
        balanceChangeVO.setMerchantName(merchantVO.getMerchantName());
        balanceChangeVO.setMerchantAccount(merchantVO.getAccount());
        balanceChangeVO.setTradeNo(payOrder.getTradeNo());
        balanceChangeVO.setOutTradeNo(payOrder.getOutTradeNo());
        balanceChangeVO.setBalanceChangeType(1);
        balanceChangeVO.setOldBlance(merchantBalanceVO.getBalance());
        // 手续费类型（0：按比率 1：按笔数）
        if (merchantVO.getTransferChargeType() == 0){
            BigDecimal transferCharge = paymentAmount.multiply(merchantVO.getPaymentTransferCharge());
            balanceChangeVO.setTransferCharge(transferCharge);
            BigDecimal newBalance = merchantBalanceVO.getBalance()
                    .add(paymentAmount)
                    .subtract(transferCharge)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            balanceChangeVO.setNewBlance(newBalance);
            // 商户余额
            merchantBalanceVO.setBalance(newBalance);
            merchantBalanceVO.setTransferCharge(merchantBalanceVO.getTransferCharge().add(transferCharge));
        }else{
            balanceChangeVO.setTransferCharge(merchantVO.getPaymentTransferCharge());
            BigDecimal newBalance = merchantBalanceVO.getBalance()
                    .add(paymentAmount)
                    .subtract(merchantVO.getPaymentTransferCharge())
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            balanceChangeVO.setNewBlance(newBalance);
            // 商户余额
            merchantBalanceVO.setBalance(newBalance);
            merchantBalanceVO.setTransferCharge(merchantBalanceVO.getTransferCharge().add(merchantVO.getPaymentTransferCharge()));
        }
        balanceChangeVO.setTransactionAmount(paymentAmount);
        balanceChangeVO.setTradeTypeCode(payOrder.getTradeTypeCode());
        balanceChangeVO.setTradeAccount(payOrder.getTradeAccount());
        balanceChangeVO.setTradeName(payOrder.getTradeTypeName());
        iBalanceChangeDao.insertPayment(balanceChangeVO);
        // 更新商户余额
        return iMerchantBalanceDao.update(merchantBalanceVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 22:05
     * @description: 获取资金明细Map
     * @param: [list, i]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    private Map<String, Object> getBalanceChangeMap(List<BalanceChangeVO> list, int i) {
        Map<String, Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("balanceChangeId", list.get(i).getBalanceChangeId());
        linkedHashMap.put("merchantAccount", list.get(i).getMerchantAccount());
        linkedHashMap.put("merchantName", list.get(i).getMerchantName());
        linkedHashMap.put("tradeNo", list.get(i).getTradeNo());
        linkedHashMap.put("outTradeNo", list.get(i).getOutTradeNo());
        linkedHashMap.put("balanceChangeType", list.get(i).getBalanceChangeType());
        linkedHashMap.put("oldBlance", list.get(i).getOldBlance());
        linkedHashMap.put("newBlance", list.get(i).getNewBlance());
        linkedHashMap.put("transferCharge", list.get(i).getTransferCharge());
        linkedHashMap.put("transactionAmount", list.get(i).getTransactionAmount());
        linkedHashMap.put("tradeTypeCode", list.get(i).getTradeTypeCode());
        linkedHashMap.put("tradeAccount", list.get(i).getTradeAccount());
        linkedHashMap.put("tradeName", list.get(i).getTradeName());
        linkedHashMap.put("gmtCreate", list.get(i).getGmtCreate());
        return linkedHashMap;
    }

}
