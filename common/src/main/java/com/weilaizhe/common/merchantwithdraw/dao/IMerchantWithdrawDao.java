package com.weilaizhe.common.merchantwithdraw.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.pojo.merchantwithdraw.MerchantWithdrawVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author dameizi
 * @description 商户提现数据层
 * @dateTime 2019-04-16 13:45
 * @className com.weilaizhe.common.merchantwithdraw.dao.IMerchantWithdrawDao
 */
public interface IMerchantWithdrawDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 15:41
     * @description: 审核商户提现
     * @param: [merchantWithdrawVO]
     * @return: int
     */
    int update(@Param("vo") MerchantWithdrawVO merchantWithdrawVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 15:41
     * @description: 删除商户提现
     * @param: [merchantWithdrawVO]
     * @return: int
     */
    int delete(@Param("vo") MerchantWithdrawVO merchantWithdrawVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 15:40
     * @description: 分页查询商户提现
     * @param: [page, merchantWithdrawVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.merchantwithdraw.MerchantWithdrawVO>
     */
    IPage<MerchantWithdrawVO> page(Page<MerchantWithdrawVO> page, @Param("vo") MerchantWithdrawVO merchantWithdrawVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-16 15:04
     * @description: 申请提现
     * @param: [merchantWithdrawVO]
     * @return: int
     */
    int insert(@Param("vo") MerchantWithdrawVO merchantWithdrawVO);

}
