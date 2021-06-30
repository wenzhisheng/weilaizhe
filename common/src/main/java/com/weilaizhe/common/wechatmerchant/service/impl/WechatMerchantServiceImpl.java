package com.weilaizhe.common.wechatmerchant.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.exception.DescribeException;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.tradetype.TradeTypeVO;
import com.weilaizhe.common.pojo.wechatmerchant.WechatMerchantVO;
import com.weilaizhe.common.util.CommonUtil;
import com.weilaizhe.common.wechatmerchant.dao.IWechatMerchantDao;
import com.weilaizhe.common.wechatmerchant.service.IWechatMerchantService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: dameizi
 * @description: 微信商户业务层
 * @dateTime 2019-04-10 23:10
 * @className com.weilaizhe.common.wechatmerchant.service.impl.WechatMerchantServiceImpl
 */
@Service
public class WechatMerchantServiceImpl implements IWechatMerchantService {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IWechatMerchantDao iWechatMerchantDao;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:22
     * @description: 分页查询微信商户
     * @param: [wechatMerchantVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.wechatmerchant.WechatMerchantVO>
     */
    @Override
    public IPage<WechatMerchantVO> page(WechatMerchantVO wechatMerchantVO, PageVO pageVO) {
        Page<WechatMerchantVO> page = new Page<WechatMerchantVO>(pageVO.getPageNo(), pageVO.getPageSize());
        return iWechatMerchantDao.pageWechatMerchant(page, wechatMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:22
     * @description: 删除微信商户
     * @param: [wechatMerchantVO]
     * @return: int
     */
    @Override
    public int delete(WechatMerchantVO wechatMerchantVO) {
        return iWechatMerchantDao.delete(wechatMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 15:24
     * @description: 批量绑定商户
     * @param: [wechatMerchantVO]
     * @return: int
     */
    @Override
    public int batchBinding(WechatMerchantVO wechatMerchantVO) {
        // 参数校验
        CommonUtil.paramEmptyVerify(String.valueOf(wechatMerchantVO.getIds()), CommonConst.ID_NOT_EMPTY);
        CommonUtil.integerEmptyVerify(wechatMerchantVO.getMerchantId(), CommonConst.MERCHANT_ID_NOT_EMPTY);
        // 判断是否启用或已绑定
        List<WechatMerchantVO> voList = iWechatMerchantDao.batchList(wechatMerchantVO);
        for(int i=0; i<voList.size(); i++){
            if(voList.get(i).getMerchantId() != null && voList.get(i).getIsEnable() == 0){
                throw new DescribeException("绑定过或禁用不能操作", 0);
            }
        }
        return iWechatMerchantDao.batchBinding(wechatMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-23 18:44
     * @description: 解绑
     * @param: [wechatMerchantVO]
     * @return: int
     */
    @Override
    public int unbind(WechatMerchantVO wechatMerchantVO) {
        CommonUtil.integerEmptyVerify(wechatMerchantVO.getWechatMerchantId(), CommonConst.ID_NOT_EMPTY);
        return iWechatMerchantDao.unbind(wechatMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:22
     * @description: 微信商户
     * @param: [wechatMerchantVO]
     * @return: int
     */
    @Override
    public int update(WechatMerchantVO wechatMerchantVO) {
        // 参数校验
        CommonUtil.integerEmptyVerify(wechatMerchantVO.getWechatMerchantId(), CommonConst.ID_NOT_EMPTY);
        CommonUtil.paramEmptyVerify(wechatMerchantVO.getWechatMerchantCode(), CommonConst.MERCHANT_CODE_ERROR);
        // 商户编码不能重复
        if (iWechatMerchantDao.getWechatMerchantByCode(wechatMerchantVO) != null){
            throw new DescribeException(CommonConst.MERCHANT_CODE_ERROR, -3);
        }
        return iWechatMerchantDao.update(wechatMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:22
     * @description: 新增微信商户
     * @param: [wechatMerchantVO]
     * @return: int
     */
    @Override
    public int insert(WechatMerchantVO wechatMerchantVO) {
        CommonUtil.paramEmptyVerify(wechatMerchantVO.getWechatMerchantCode(), CommonConst.MERCHANT_CODE_ERROR);
        // 商户编码不能重复
        if (iWechatMerchantDao.getWechatMerchantByCode(wechatMerchantVO) != null){
            throw new DescribeException(CommonConst.MERCHANT_CODE_ERROR, -3);
        }
        wechatMerchantVO.setDailyTotalAmount(BigDecimal.ZERO);
        return iWechatMerchantDao.insert(wechatMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-10 23:15
     * @description: 获取微信商户号（启用的未满的限额内的且最空闲的）
     * @param: [app_id, tradeType]
     * @return: com.weilaizhe.common.pojo.wechatmerchant.WechatMerchantVO
     */
    @Override
    public WechatMerchantVO getIdleWechatMerchant(Integer app_id, String tradeType) {
        // 查询缓存根据限额获取到支付账号
        RMap<Object, Object> map = redissonClient.getMap(RedisKeyConst.TRADETYPE_INFO_MAP);
        TradeTypeVO tradeTypeVO = (TradeTypeVO)map.get(tradeType);
        return iWechatMerchantDao.getIdleAlipayMerchant(app_id, tradeTypeVO.getDailyLimit());
    }
}
