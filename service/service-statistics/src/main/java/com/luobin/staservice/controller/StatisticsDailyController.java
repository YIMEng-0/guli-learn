package com.luobin.staservice.controller;

import com.luobin.common_utils.R;
import com.luobin.staservice.service.StatisticsDailyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author luobin
 * @since 2022-07-15
 */
@CrossOrigin
@RestController
@RequestMapping("/staservice/sta")
@Slf4j
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService dailyService;

    /**
     * 统计某一天的人数，将数据放到数据库中
     */
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day) {

        dailyService.registerCount(day);
        return R.ok();
    }

    /**
     * 读取数据库中的各个统计数据，按照时间范围进行最终的一个返回
     */
    @GetMapping("show-chart/{begin}/{end}/{type}")
    public R showChart(@PathVariable String begin, @PathVariable String end, @PathVariable String type) {
        Map<String, Object> map =  dailyService.getChartData(begin, end, type);

        return R.ok().data(map);
    }
}
