package com.weilaizhe.common.tradetype.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.exception.DescribeException;
import com.weilaizhe.common.merchant.dao.IMerchantDao;
import com.weilaizhe.common.merchanttradetype.dao.IMerchantTradeTypeDao;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.merchanttradetype.MerchantTradeTypeVO;
import com.weilaizhe.common.pojo.tradetype.TradeTypeVO;
import com.weilaizhe.common.tradetype.dao.ITradeTypeDao;
import com.weilaizhe.common.tradetype.service.ITradeTypeService;
import com.weilaizhe.common.util.CommonUtil;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: dameizi
 * @description: 交易类型业务层
 * @dateTime 2019-04-01 18:02
 * @className com.weilaizhe.common.tradetype.service.impl.TradeTypeServiceImpl
 */
@Service
public class TradeTypeServiceImpl implements ITradeTypeService {

    @Autowired
    private ITradeTypeDao iTradeTypeDao;
    @Autowired
    private IMerchantDao iMerchantDao;
    @Autowired
    private IMerchantTradeTypeDao iMerchantTradeTypeDao;
    @Autowired
    private RedissonClient redissonClient;

    /**
     * @author: dameizi
     * @dateTime: 2019-06-01 16:37
     * @description: 查询交易类型列表
     * @param: [tradeTypeVO]
     * @return: java.util.List<com.weilaizhe.common.pojo.tradetype.TradeTypeVO>
     */
    @Override
    public List<TradeTypeVO> listTradeType(TradeTypeVO tradeTypeVO) {
        return iTradeTypeDao.listTradeType(tradeTypeVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-03 13:03
     * @description: 分页查询交易类型
     * @param: [receiptTypeVO, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.receipttype.ReceiptTypeVO>
     */
    @Override
    public IPage<TradeTypeVO> pageTradeType(TradeTypeVO receiptTypeVO, PageVO pageVO){
        // mybatis plus分页查询插件，第一个参数必须是Page<T>，返回类型必须IPage<T>接收
        Page<TradeTypeVO> page = new Page<TradeTypeVO>(pageVO.getPageNo(),pageVO.getPageSize());
        return iTradeTypeDao.pageTradeType(page, receiptTypeVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-08 23:33
     * @description: 列表查询交易类型名称
     * @param: []
     * @return: java.util.List<java.lang.String>
     */
    @Override
    public List<String> listTradeTypeName(){
        return iTradeTypeDao.listTradeTypeName();
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 23:29
     * @description: 删除交易类型
     * @param: [receiptTypeVO]
     * @return: int
     */
    @Override
    public int delete(TradeTypeVO tradeTypeVO){
        //id不能为空
        CommonUtil.integerEmptyVerify(tradeTypeVO.getTradeTypeId(), CommonConst.ID_NOT_EMPTY);
        return iTradeTypeDao.delete(tradeTypeVO.getTradeTypeId());
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 17:12
     * @description: 更新交易类型
     * @param: [receiptTypeVO, file]
     * @return: int
     */
    @Override
    public int update(TradeTypeVO tradeTypeVO, MultipartFile file){
        // id不能为空
        CommonUtil.integerEmptyVerify(tradeTypeVO.getTradeTypeId(), CommonConst.ID_NOT_EMPTY);
        // 上传图片
        if (file != null){
            tradeTypeVO.setTradeTypeIcon(CommonUtil.uploadFile(file));
        }
        // 参数校验
        this.paramVerify(tradeTypeVO);
        // 修改数据
        iTradeTypeDao.update(tradeTypeVO);
        // 把交易类型存入缓存
        updateCacheTradeType();
        return 1;
    }


    /**
     * @author: dameizi
     * @dateTime: 2019-04-01 22:12
     * @description: 新增交易类型
     * @param: [receiptTypeVO, file]
     * @return: int
     */
    @Override
    public int insert(MultipartFile file, TradeTypeVO tradeTypeVO) {
        // 上传图片
        if (file != null){
            tradeTypeVO.setTradeTypeIcon(CommonUtil.uploadFile(file));
        }
        // 参数校验
        paramVerify(tradeTypeVO);
        // 保存数据
        iTradeTypeDao.insert(tradeTypeVO);
        // 把交易类型存入缓存
        updateCacheTradeType();
        // 初始化商户交易类型
        initMerchantTradeType(tradeTypeVO);
        return 1;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-30 16:13
     * @description: 初始化商户交易类型
     * @param: [tradeTypeVO]
     * @return: void
     */
    private void initMerchantTradeType(TradeTypeVO tradeTypeVO) {
        List<MerchantVO> merchantVOList = iMerchantDao.listMerchant(new MerchantVO());
        for (int i=0; i<merchantVOList.size(); i++){
            MerchantTradeTypeVO merchantTradeTypeVO = new MerchantTradeTypeVO();
            merchantTradeTypeVO.setMerchantId(merchantVOList.get(i).getMerchantId());
            merchantTradeTypeVO.setTradeTypeCode(tradeTypeVO.getTradeTypeCode());
            merchantTradeTypeVO.setTradeTypeName(tradeTypeVO.getTradeTypeName());
            merchantTradeTypeVO.setIsEnable(0);
            iMerchantTradeTypeDao.insert(merchantTradeTypeVO);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-21 20:51
     * @description: 参数校验
     * @param: [tradeTypeVO]
     * @return: void
     */
    private void paramVerify(TradeTypeVO tradeTypeVO) {
        // 交易类型编码不能重复
        TradeTypeVO typeVO1 = new TradeTypeVO();
        typeVO1.setTradeTypeId(tradeTypeVO.getTradeTypeId());
        typeVO1.setTradeTypeCode(tradeTypeVO.getTradeTypeCode());
        if (iTradeTypeDao.getTradeTypeRepeat(typeVO1) != null){
            throw new DescribeException("交易类型编码已存在", -3);
        }
        if (!tradeTypeVO.getTradeTypeCode().matches("^[A-Za-z0-9][A-Za-z0-9_]{2,12}")){
            throw new DescribeException("交易类型编码必须大小写字母数字或下划线组合且2-12位", 0);
        }
        // 交易类型名称不能重复
        TradeTypeVO typeVO2 = new TradeTypeVO();
        typeVO2.setTradeTypeId(tradeTypeVO.getTradeTypeId());
        typeVO2.setTradeTypeName(tradeTypeVO.getTradeTypeName());
        if (iTradeTypeDao.getTradeTypeRepeat(typeVO2) != null){
            throw new DescribeException("交易类型名称已存在", -3);
        }
        CommonUtil.paramEmptyVerify(tradeTypeVO.getTradeTypeName(), "交易类型名称不能为空");
        CommonUtil.integerEmptyVerify(tradeTypeVO.getIsRecommend(), "是否推荐不能为空");
        CommonUtil.integerEmptyVerify(tradeTypeVO.getIsEnable(), "是否启用不能为空");
        CommonUtil.paramEmptyVerify(tradeTypeVO.getSingleLimit(), "单笔限额不能为空");
        CommonUtil.paramEmptyVerify(String.valueOf(tradeTypeVO.getDailyLimit()), "当天限额不能为空");
        CommonUtil.paramEmptyVerify(tradeTypeVO.getSystemTypeName(), "平台交易类型名不能为空");
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-08 23:41
     * @description: 交易类型存入缓存
     * @param: []
     * @return: void
     */
    private void updateCacheTradeType(){
        List<TradeTypeVO> tradeTypeVO = iTradeTypeDao.listTradeType(new TradeTypeVO());
        RMap tradeTypeMap = redissonClient.getMap(RedisKeyConst.TRADETYPE_INFO_MAP);
        tradeTypeMap.delete();
        Map<String, TradeTypeVO> mapTemp = new HashMap<String, TradeTypeVO>();
        for (TradeTypeVO tradeType: tradeTypeVO) {
            //key是根据交易类型编码
            mapTemp.put(tradeType.getTradeTypeCode(), tradeType);
        }
        tradeTypeMap.putAll(mapTemp);
    }

}
