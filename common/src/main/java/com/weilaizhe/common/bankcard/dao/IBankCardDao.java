package com.weilaizhe.common.bankcard.dao;

import com.weilaizhe.common.pojo.bankcard.BankCardVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dameizi
 * @description TODO
 * @dateTime 2019-05-30 20:37
 * @className com.weilaizhe.common.bankcard.dao.IBnakCardDao
 */
public interface IBankCardDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-05-30 20:48
     * @description: 列表查询银行编码
     * @param: [bankCardVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.bankcard.BankCardVO>
     */
    List<BankCardVO> list(@Param("vo") BankCardVO bankCardVO);

}
