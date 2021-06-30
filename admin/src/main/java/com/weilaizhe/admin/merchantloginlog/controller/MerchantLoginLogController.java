package com.weilaizhe.admin.merchantloginlog.controller;

import com.weilaizhe.common.merchantloginlog.service.IMerchantLoginLogService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchantloginlog.MerchantLoginLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: dameizi
 * @description: 商户登录日志
 * @dateTime 2019-04-02 19:17
 * @className com.weilaizhe.admin.merchantloginlog.controller.MerchantLoginLogController
 */
@RestController
@RequestMapping("/logAll")
@Api(value="MerchantLoginLogController", tags="MerchantLoginLog", description="商户登录日志")
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
        return iMerchantLoginLogService.page(merchantLoginLogVO, pageVO);
    }

}
