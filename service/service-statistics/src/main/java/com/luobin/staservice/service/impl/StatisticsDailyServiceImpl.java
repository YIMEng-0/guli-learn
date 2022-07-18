package com.luobin.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luobin.common_utils.R;
import com.luobin.staservice.client.UcenterClient;
import com.luobin.staservice.entity.StatisticsDaily;
import com.luobin.staservice.mapper.StatisticsDailyMapper;
import com.luobin.staservice.service.StatisticsDailyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author luobin
 * @since 2022-07-15
 */
@Service
@Slf4j
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    // 将远程调用的服务注入进来
    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 得到某一天当中的注册的人数
     */
    @Override
    public void registerCount(String day) {
        /**
         * 完善程序，在添加记录之前，删除相同日期的记录，然后添加
         *
         * 这样子可以使得统计的数据是正确的，
         *
         * 程序只能存在一条日期的统计数据
         */
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        log.info("执行远程调用前，调用传递的参数是：{}", day);
        // 远程调用 UCenter 中的方法
        R registerR = ucenterClient.countRegister(day);

        Integer countRegister = (Integer) registerR.getData().get("countRegister");
        log.info("远程调用执行结束，获取到的每日的注册人数的数量是：{}", countRegister);

        // 将获取到的数据添加到数据库中
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister);

        sta.setLoginNum(RandomUtils.nextInt(100, 200));
        sta.setCourseNum(RandomUtils.nextInt(100, 200));
        sta.setLoginNum(RandomUtils.nextInt(100, 200));
        sta.setVideoViewNum(RandomUtils.nextInt(100, 200));
        sta.setDateCalculated(day);

        log.info("远程调用得到的数据组成的对象是：{}", sta);
        baseMapper.insert(sta);
    }

    /**
     * 按照前端的请求，请求的开始时间，结束时间，请求数据的类型，将数据从数据库中读取出来，返回
     * @param begin 请求开始时间
     * @param end 请求结束时间
     * @param type 请求的数据类型
     * @return 返回封装好的数据
     */
    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {
        log.info("======begin:{}", begin);
        log.info("========end:{}", end);
        log.info("=======type:{}", type);

        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
        // select 方法表示的是选中 type 以及 date_calculated 列进行的一个查询操作
        // 相当远 select register_num, date_calculated from statistic_daily where date_calculated between begin and edd
        dayQueryWrapper.select(type, "date_calculated");
        dayQueryWrapper.between("date_calculated", begin, end);

        List<StatisticsDaily> dayList = baseMapper.selectList(dayQueryWrapper);

        // map 作为最终返回结果
        Map<String, Object> map = new HashMap<>();

        // 一个日期对应着 四种类型的数据，将日期数据以及日期数据对应的相关注册人数，登录数据，视频播放数据。课程数据进行封装
        List<Integer> dataList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();

        for (int i = 0; i < dayList.size(); i++) {
            StatisticsDaily daily = dayList.get(i);

            dateList.add(daily.getDateCalculated());

            switch (type) {
                case "register_num" :
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num" :
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num" :
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num" :
                    dataList.add(daily.getCourseNum());
                    break;
            }
        }

        map.put("dataList", dataList);
        map.put("dateList", dateList);
        log.info("按照日期，类型将数据查询完毕，返回 Map 是：{}", map);

        return map;
    }
}
