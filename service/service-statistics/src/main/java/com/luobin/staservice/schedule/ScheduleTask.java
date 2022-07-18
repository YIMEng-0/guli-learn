package com.luobin.staservice.schedule;

import com.luobin.staservice.service.StatisticsDailyService;
import com.luobin.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/*************************
 *@author : YIMENG
 *@date : 2022/7/16 16:26
 *************************/

/**
 * 在 Spring 应用程序中，开启一个定时任务，下面的类是和定时任务相关的
 */
@Component
public class ScheduleTask {
    @Autowired
    private StatisticsDailyService dailyService;

    /**
     * cron 表达式中指定指定的规则，服务启动的时候，按照下面的规则执行方法
     *  下面的表达式的含义就是每天的凌晨一点钟执行
     *
     *  cron 表达式的生成工具 ： https://cron.qqe2.com/ 七子表达式
     *
     *  下面只能存在 6 位，不能出现 7 位
     */

    /**
     * 在每天凌晨一点的时候，将前一天的数据进行查询添加
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task() {
        /**
         * 下面代码执行的逻辑就是：获取当前的日期，并且将得到当前日期的前一天，然后将 Sat Jul 16 17:01:00 GMT+08:00 2022 进行格式的转换
         * 转换格式使用的就是 toString 这个方法，这个方法在 DateUtils 中，专门格式化日期
         */
        dailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
