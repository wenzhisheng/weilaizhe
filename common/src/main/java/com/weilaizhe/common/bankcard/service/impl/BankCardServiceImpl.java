package com.weilaizhe.common.bankcard.service.impl;

import com.weilaizhe.common.bankcard.dao.IBankCardDao;
import com.weilaizhe.common.bankcard.service.IBankCardService;
import com.weilaizhe.common.pojo.bankcard.BankCardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dameizi
 * @description 银行编码服务层
 * @dateTime 2019-05-30 20:38
 * @className com.weilaizhe.common.bankcard.service.impl.BnakCardServiceImpl
 */
@Service
public class BankCardServiceImpl implements IBankCardService {

    @Autowired
    private IBankCardDao iBnakCardDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-05-30 20:50
     * @description: 列表查询银行编码
     * @param: [bankCardVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.bankcard.BankCardVO>
     */
    @Override
    public List<BankCardVO> list(BankCardVO bankCardVO) {
        return iBnakCardDao.list(bankCardVO);
    }

}
