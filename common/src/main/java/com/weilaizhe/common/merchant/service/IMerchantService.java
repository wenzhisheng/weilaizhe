package com.weilaizhe.common.merchant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;

import java.util.List;

/**
 * @author: dameizi
 * @description: 商户接口层
 * @dateTime 2019-03-29 14:17
 * @className com.weilaizhe.common.exception.OtherReturn
 */
public interface IMerchantService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-06 15:05
     * @description: 应用ID与授权码合法验证
     * @param: [app_id, auth_code]
     * @return: int
     */
    int checkMerchantCode(Integer app_id, String auth_code);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 22:42
     * @description: 设置提现密码
     * @param: [merchantVO, merchantVO1]
     * @return: int
     */
    Object withdrawPassword(MerchantVO merchantVO, MerchantVO merchantInfo);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 15:31
     * @description: 验证码验证
     * @param: [merchantVO]
     * @return: int
     */
    Object smsVerify(MerchantVO merchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 13:22
     * @description: 更新商户密码
     * @param: [merchantVO]
     * @return: int
     */
    Object updateMerchantPassword(MerchantVO merchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 13:29
     * @description: 根据商户ID获取商户
     * @param: [merchantVO]
     * @return: com.weilaizhe.common.pojo.merchant.MerchantVO
     */
    MerchantVO getMerchantById(MerchantVO merchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 23:17
     * @description: 商户删除
     * @param: [merchantVO]
     * @return: int
     */
    int delete(MerchantVO merchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 22:05
     * @description: 更新商户信息
     * @param: [merchantVO]
     * @return: int
     */
    int update(MerchantVO merchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-05-27 21:10
     * @description: 商户修改
     * @param: [merchantVO]
     * @return:
     */
    int updateMerchant(MerchantVO merchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 18:53
     * @description: 更新商户冻结类型
     * @param: [merchant]
     * @return: int
     */
    int updateMerchantByFreezeType(MerchantVO merchant);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 17:54
     * @description: 登录商户
     * @param: [merchant]
     * @return: com.weilaizhe.common.pojo.merchant.MerchantVO
     */
    MerchantVO login(MerchantVO merchant);

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 16:11
     * @description: 分页查询商户信息
     * @param: [merchantVO, pageVO]
     * @return: com.weilaizhe.common.pojo.base.PageResult<com.weilaizhe.common.pojo.merchant.MerchantVO>
     */
    IPage<MerchantVO> pageMerchant(MerchantVO merchantVO, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-03-30 22:00
     * @description: 列表查询商户信息
     * @param: [merchantVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.merchant.MerchantVO>
     */
    List<MerchantVO> listMerchant(MerchantVO merchantVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-03-31 18:07
     * @description: 申请商户
     * @param: [merchantVO]
     * @return: int
     */
    int insert(MerchantVO merchantVO);

}
