package com.weilaizhe.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//Servlet扫描
@ServletComponentScan
//指定根路径扫描
@ComponentScan(basePackages={"com.weilaizhe.admin","com.weilaizhe.common"})
//指定dao扫描
@MapperScan(basePackages={"com.weilaizhe.admin.**.dao",
        "com.weilaizhe.common.**.dao",
        "com.baomidou.mybatisplus.samples.quickstart.mapper"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
