package com.weilaizhe.common.merchant.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: dameizi
 * @description: 商户数据层
 * @dateTime 2019-03-29 14:17
 * @className com.weilaizhe.common.exception.OtherReturn
 */
public interface IMerchantDao {

	/**
	 * @author: dameizi
	 * @dateTime: 2019-04-06 15:07
	 * @description: 应用ID与授权码合法验证
	 * @param: [app_id, auth_code]
	 * @return: int
	 */
	int checkMerchantCode(@Param("vo") MerchantVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-04-04 12:41
	 * @description: 更新提现密码
	 * @param: [merchantVO]
	 * @return: int
	 */
	int updateWithdrawPassWord(@Param("vo") MerchantVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-04-03 15:31
	 * @description: 验证码验证
	 * @param: [merchantVO]
	 * @return: int
	 */
	int smsVerify(@Param("vo") MerchantVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-04-03 13:22
	 * @description: 更新商户密码
	 * @param: [merchantVO]
	 * @return: int
	 */
	int updateMerchantPassword(@Param("vo") MerchantVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-04-03 13:29
	 * @description: 根据商户ID获取商户
	 * @param: [merchantVO]
	 * @return: com.weilaizhe.common.pojo.merchant.MerchantVO
	 */
	MerchantVO getMerchantById(@Param("vo") MerchantVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-04-02 23:17
	 * @description: 商户删除
	 * @param: [merchantVO]
	 * @return: int
	 */
	int delete(@Param("merchantId") Integer merchantId);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-04-02 22:36
	 * @description: 更新商户信息
	 * @param: [merchantVO]
	 * @return: int
	 */
	int update(@Param("vo") MerchantVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-05-27 21:21
	 * @description: 商户修改
	 * @param: [merchantVO]
	 * @return: int
	 */
	int updateMerchant(@Param("vo") MerchantVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-04-02 18:56
	 * @description: 更新商户冻结类型
	 * @param: [merchantVO]
	 * @return: int
	 */
	int updateMerchantByFreezeType(@Param("vo") MerchantVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-04-02 17:54
	 * @description: 登录商户
	 * @param: [merchant]
	 * @return: com.weilaizhe.common.pojo.merchant.MerchantVO
	 */
	MerchantVO login(@Param("vo") MerchantVO merchant);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-03-31 18:07
	 * @description: 申请商户
	 * @param: [merchantVO]
	 * @return: int
	 */
	int insert(@Param("vo") MerchantVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-03-29 16:11
	 * @description: 分页查询商户信息
	 * @param: [merchantVO, pageVO]
	 * @return: com.weilaizhe.common.pojo.base.PageResult<com.weilaizhe.common.pojo.merchant.MerchantVO>
	 */
	IPage<MerchantVO> pageMerchant(Page<MerchantVO> page, @Param("vo") MerchantVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-03-30 22:00
	 * @description: 列表查询商户信息
	 * @param: [merchantVO]
	 * @return: java.util.List<com.weilaizhe.common.pojo.merchant.MerchantVO>
	 */
	List<MerchantVO> listMerchant(@Param("vo") MerchantVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-03-31 19:18
	 * @description: 账号和编码是否已被使用
	 * @param: [merchantVO]
	 * @return: int
	 */
	int getCodeOrAccountIsUse(@Param("vo") MerchantVO merchantVO);

	/**
	 * @author: dameizi
	 * @dateTime: 2019-03-31 21:14
	 * @description: 获取最大Id
	 * @param: []
	 * @return: int
	 */
	int getMaxId();

	/**
	 * @author: dameizi
	 * @dateTime: 2019-03-31 21:56
	 * @description: 根据商户编码获取商户信息
	 * @param: [merchantVO]
	 * @return: com.weilaizhe.common.pojo.merchant.MerchantVO
	 */
	MerchantVO getMerchantByCode(@Param("vo") MerchantVO merchantVO);
	
}