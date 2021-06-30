package com.weilaizhe.schedule.sqltask;

import com.weilaizhe.common.alipaymerchant.service.IAlipayMerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: dameizi
 * @description: 数据库任务
 * @dateTime 2019-04-09 18:20
 * @className com.weilaizhe.schedule.sqltask.SqlTask
 */
@Component
public class SqlTask {

    public static final Logger logger = LoggerFactory.getLogger(SqlTask.class);

    @Autowired
    private IAlipayMerchantService iAlipayMerchantService;
    
    @Scheduled(cron = "0 0 1 * * ?")
    public void configureTasks(){
        // 把商户金额满当天限定额度状态改变
        iAlipayMerchantService.updateStatus();
    }

}
