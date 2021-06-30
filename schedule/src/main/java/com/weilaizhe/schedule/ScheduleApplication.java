package com.weilaizhe.schedule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// Servlet扫描
@ServletComponentScan
// 指定根路径扫描
@ComponentScan(basePackages={"com.weilaizhe.schedule","com.weilaizhe.common"})
// 指定dao扫描
@MapperScan(basePackages={"com.weilaizhe.schedule.**.dao",
        "com.weilaizhe.common.**.dao",
        "com.baomidou.mybatisplus.samples.quickstart.mapper"})
// 开启定时任务
@EnableScheduling
public class ScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleApplication.class, args);
    }

}
