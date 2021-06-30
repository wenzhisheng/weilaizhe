package com.weilaizhe.admin.adminloginlog.controller;

import com.weilaizhe.common.adminloginlog.service.IAdminLoginLogService;
import com.weilaizhe.common.pojo.adminloginlog.AdminLoginLogVO;
import com.weilaizhe.common.pojo.base.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: dameizi
 * @description: 管理员登录日志控制层
 * @dateTime 2019-04-02 19:17
 * @className com.weilaizhe.merchant.adminloginlog.controller.AdminLoginLogController
 */
@RestController
@RequestMapping("/loginLog")
@Api(value="AdminLoginLogController", tags="AdminLoginLogController", description="管理员登录日志")
public class AdminLoginLogController {

    @Autowired
    private IAdminLoginLogService iAdminLoginLogService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:31
     * @description: 批量删除管理员登录日志
     * @param: [ids]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "/batchDelete", method = RequestMethod.GET)
    @ApiOperation(value = "批量删除管理员登录日志", notes = "必填参数：管理员登录日志ID")
    public Object batchDelete(AdminLoginLogVO adminLoginLog){
        return iAdminLoginLogService.batchDelete(adminLoginLog);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:13
     * @description: 分页查询管理员登录日志
     * @param: [merchantLoginLogVO, pageVO]
     * @return: java.lang.Object
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询管理员登录日志", notes = "必填参数：分页 开始结束时间")
    public Object page(AdminLoginLogVO adminLoginLog, PageVO pageVO){
        return iAdminLoginLogService.page(adminLoginLog, pageVO);
    }

}
