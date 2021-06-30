package com.weilaizhe.merchant.wechatmerchant.controller;

import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.wechatmerchant.WechatMerchantVO;
import com.weilaizhe.common.util.RedissonUtil;
import com.weilaizhe.common.wechatmerchant.service.IWechatMerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: dameizi
 * @description: 微信商户
 * @dateTime 2019-04-10 23:36
 * @className com.weilaizhe.merchant.wechatmerchant.controller.WechatMerchantController
 */
@RestController
@RequestMapping("/wechatMerchant")
@Api(value = "Wechat MerchantController", tags = "WechatMerchantController", description = "微信商户")
public class WechatMerchantController {

    @Autowired
    private IWechatMerchantService iWechatMerchantService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:20
     * @description: 分页查询微信商户
     * @param: [wechatMerchantVO, pageVO]
     * @return: java.lang.Object
     */
    @GetMapping("/page")
    @ApiOperation(value="分页查询微信商户",notes="必填字段：token")
    public Object page(WechatMerchantVO wechatMerchantVO, PageVO pageVO){
        wechatMerchantVO.setMerchantId(RedissonUtil.getContextMerchantInfo().getMerchantId());
        return iWechatMerchantService.page(wechatMerchantVO, pageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:20
     * @description: 删除微信商户
     * @param: [wechatMerchantVO]
     * @return: java.lang.Object
     */
    @GetMapping("/delete")
    @ApiOperation(value="删除微信商户",notes="必填字段：id")
    public Object delete(WechatMerchantVO wechatMerchantVO){
        return iWechatMerchantService.delete(wechatMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:19
     * @description: 更新微信商户
     * @param: [wechatMerchantVO]
     * @return: java.lang.Object
     */
    @PostMapping("/update")
    @ApiOperation(value="更新微信商户",notes="必填字段：id")
    public Object update(@RequestBody WechatMerchantVO wechatMerchantVO){
        return iWechatMerchantService.update(wechatMerchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 22:18
     * @description: 新增微信商户
     * @param: [wechatMerchantVO]
     * @return: java.lang.Object
     */
    @PostMapping("/insert")
    @ApiOperation(value="新增微信商户",notes="必填字段：token")
    public Object insert(@RequestBody WechatMerchantVO wechatMerchantVO){
        MerchantVO merchantInfo = RedissonUtil.getContextMerchantInfo();
        wechatMerchantVO.setMerchantId(merchantInfo.getMerchantId());
        wechatMerchantVO.setMerchantName(merchantInfo.getMerchantName());
        return iWechatMerchantService.insert(wechatMerchantVO);
    }

}
