package com.weilaizhe.common.adminloginlog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.weilaizhe.common.pojo.adminloginlog.AdminLoginLogVO;
import com.weilaizhe.common.pojo.base.PageVO;

/**
 * @author: dameizi
 * @description: 管理员登录日志接口层
 * @dateTime 2019-04-02 19:32
 * @className com.weilaizhe.common.adminloginlog.service.IAdminLoginLogService
 */
public interface IAdminLoginLogService {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:35
     * @description: 批量删除管理员登录日志
     * @param: [adminLoginLogVO]
     * @return: int
     */
    int batchDelete(AdminLoginLogVO adminLoginLogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 21:00
     * @description: 分页查询管理员登录日志
     * @param: [adminLoginLog, pageVO]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.adminloginlog.AdminLoginLog>
     */
    IPage<AdminLoginLogVO> page(AdminLoginLogVO adminLoginLog, PageVO pageVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:37
     * @description: 新增管理员登录日志
     * @param: [adminLoginLog]
     * @return: int
     */
    int insert(AdminLoginLogVO adminLoginLog);

}
