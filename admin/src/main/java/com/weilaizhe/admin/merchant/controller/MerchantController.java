package com.weilaizhe.admin.merchant.controller;

import com.weilaizhe.common.merchant.service.IMerchantService;
import com.weilaizhe.common.pojo.base.PageVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: dameizi
 * @description: 商户管理
 * @dateTime 2019-04-05 23:03
 * @className com.weilaizhe.admin.merchant.controller.MerchantController
 */
@RestController
@RequestMapping("/merchant")
@Api(value = "MerchantController", tags = "MerchantController", description = "商户管理")
public class MerchantController {

    @Autowired
    private IMerchantService iMerchantService;

    /**
     * @author: dameizi
     * @dateTime: 2019-03-30 22:00
     * @description: 列表查询商户信息
     * @param: [merchant]
     * @return: java.lang.Object
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value="列表查询商户",notes="无需参数")
    public Object listMerchant(MerchantVO merchant){
        return iMerchantService.listMerchant(merchant);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 16:11
     * @description: 分页查询商户信息
     * @param: [merchant, pageVO]
     * @return: java.lang.Object
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value="分页查询商户信息",notes="分页查询商户信息")
    public Object pageMerchant(MerchantVO merchant, PageVO pageVO){
        return iMerchantService.pageMerchant(merchant, pageVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 23:12
     * @description: 商户删除
     * @param: [merchant]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ApiOperation(value="根据id删除商户信息",notes="根据id删除商户信息：主键，商户id必填")
    public Object deleteMerchantById(@RequestBody MerchantVO merchantVO){
        return iMerchantService.delete(merchantVO);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-05-22 23:07
     * @description: 商户配置
     * @param: [merchant]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value="更新商户信息", notes="必填参数：ID")
    public Object updateMerchant(@RequestBody MerchantVO merchant){
        return iMerchantService.update(merchant);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-31 18:03
     * @description: 商户申请
     * @param: [merchantVO]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value="申请商户",notes="必填参数：Authorization")
    public Object insert(@RequestBody MerchantVO merchantVO){
        return iMerchantService.insert(merchantVO);
    }

}
