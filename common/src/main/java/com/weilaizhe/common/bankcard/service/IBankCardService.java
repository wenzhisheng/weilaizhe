package com.weilaizhe.common.bankcard.service;

import com.weilaizhe.common.pojo.bankcard.BankCardVO;

import java.util.List;

/**
 * @author dameizi
 * @description 银行编码接口层
 * @dateTime 2019-05-30 20:37
 * @className com.weilaizhe.common.bankcard.service.IBnakCardService
 */
public interface IBankCardService {

    /**
     * @author: dameizi
     * @dateTime: 2019-05-30 20:49
     * @description: 列表查询银行编码
     * @param: [bankCardVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.bankcard.BankCardVO>
     */
    List<BankCardVO> list(BankCardVO bankCardVO);

}
