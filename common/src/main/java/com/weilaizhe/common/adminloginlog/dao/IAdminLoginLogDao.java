package com.weilaizhe.common.adminloginlog.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaizhe.common.pojo.adminloginlog.AdminLoginLogVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;

/**
 * @author: dameizi
 * @description: 管理员登录日志数据层
 * @dateTime 2019-04-02 19:32
 * @className com.weilaizhe.common.adminloginlog.service.IAdminLoginLogDao
 */
public interface IAdminLoginLogDao {

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:35
     * @description: 批量删除管理员登录日志
     * @param: [ids]
     * @return: int
     */
    int batchDelete(@Param("vo") AdminLoginLogVO adminLoginLogVO);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 20:19
     * @description: 分页查询管理员登录日志
     * @param: [page, adminLoginLog]
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.weilaizhe.common.pojo.adminloginlog.AdminLoginLog>
     */
    IPage<AdminLoginLogVO> page(Page<AdminLoginLogVO> page, @Param("vo") AdminLoginLogVO adminLoginLog);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-02 19:37
     * @description: 新增管理员登录日志
     * @param: [adminLoginLog]
     * @return: int
     */
    int insert(@Param("vo") AdminLoginLogVO adminLoginLog);

}
