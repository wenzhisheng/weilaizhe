package com.weilaizhe.common.merchantwithdraw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchantwithdraw.MerchantWithdrawVO;

/**
 * @author dameizi
 * @description 商户提现接口层
 * @dateTime 2019-04-16 13:46
 * @className com.weilaizhe.common.merchantwithdraw.service.IMerchantWithdrawService
 */
public interface IMerchantWithdrawService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 15:41
     * @description: 审核商户提现
     * @param: [merchantWithdrawVO]
     * @return: int
     */
    int update(MerchantWithdrawVO merchantWithdrawVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 15:41
     * @description: 删除商户提现
     * @param: [merchantWithdrawVO]
     * @return: int
     */
    int delete(MerchantWithdrawVO merchantWithdrawVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 15:44
     * @description: 分页查询商户提现
     * @param: [merchantWithdrawVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.merchantwithdraw.MerchantWithdrawVO>
     */
    IPage<MerchantWithdrawVO> page(MerchantWithdrawVO merchantWithdrawVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 15:04
     * @description: 申请提现
     * @param: [merchantWithdrawVO]
     * @return: int
     */
    Object insert(MerchantWithdrawVO merchantWithdrawVO);

}
