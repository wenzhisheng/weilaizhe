package com.weilaizhe.merchant.merchantloginlog.controller;

import com.weilaizhe.common.merchantloginlog.service.IMerchantLoginLogService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.merchantloginlog.MerchantLoginLogVO;
import com.weilaizhe.common.util.RedissonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: dameizi
 * @description: 商户登录日志
 * @dateTime 2019-04-02 19:17
 * @className com.weilaizhe.merchant.merchantloginlog.controller.MerchantLoginLogController
 */
@RestController
@RequestMapping("/loginLog")
@Api(value="MerchantLoginLogController", tags="MerchantLoginLogController", description="商户登录日志")
public class MerchantLoginLogController {

    @Autowired
    private IMerchantLoginLogService iMerchantLoginLogService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:31
     * @description: 批量删除商户登录日志
     * @param: [ids]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/batchDelete", method = RequestMethod.GET)
    @ApiOperation(value = "批量删除商户登录日志", notes = "必填参数：商户登录日志ID")
    public Object removeMerchantLoginLog(MerchantLoginLogVO merchantLoginLogVO){
        // 获取当前商户信息，保存接口只能操作当前商户
        MerchantVO merchantInfo = RedissonUtil.getContextMerchantInfo();
        merchantLoginLogVO.setMerchantAccount(merchantInfo.getAccount());
        return iMerchantLoginLogService.batchDelete(merchantLoginLogVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:13
     * @description: 分页查询商户登录日志
     * @param: [merchantLoginLogVO, pageVO]
     * @return: java.lang.Object
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询商户登录日志", notes = "必填参数：分页 开始结束时间")
    public Object pageMerchantLoginLog(MerchantLoginLogVO merchantLoginLogVO, PageVO pageVO){
        // 获取当前商户信息，保存接口只能操作当前商户
        MerchantVO merchantInfo = RedissonUtil.getContextMerchantInfo();
        merchantLoginLogVO.setMerchantAccount(merchantInfo.getAccount());
        return iMerchantLoginLogService.page(merchantLoginLogVO, pageVO);
    }

}
