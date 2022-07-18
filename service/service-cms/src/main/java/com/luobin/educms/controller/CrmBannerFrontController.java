package com.luobin.educms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luobin.common_utils.R;
import com.luobin.educms.entity.CrmBanner;
import com.luobin.educms.service.CrmBannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 首页 banner表 前端控制器
 *
 * @author luobin
 * @since 2022-06-28
 */

/**
 * CrmBannerFrontController 这个是后用户进行的 banner 的查看
 */
@Slf4j
@RestController
@RequestMapping("/educms/bannerfront") // GET /educms/bannerfront/getHotBanner
@CrossOrigin
public class CrmBannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    /**
     * 查询所有的 banner
     *
     *      @Cacheable(key = "selectIndexList", value = "banner")
     *   在这里将查询的数据放到缓存中，第一次查询的时候，走数据库，后面查询的时候，直接使用缓存的数据，提升查询的效率
     */
    @Cacheable(key = "'selectIndexList'", value = "banner") // 将页面首部的轮流播放幻灯片假如到缓存中
    @GetMapping("getHotBanner")
    public R getAllBanner() {
        log.info("开始查询数据库 crm_banner 表中所有的 banner 用于前台的数据显示");


        // 根据 id 进行降序排列，显示排列之后前两条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        // 使用 last 方法进行 SQL 语句的拼接
        wrapper.last("limit 2");

        List<CrmBanner> list = bannerService.list(wrapper);
        log.info("查询出来的 list 数据是：{}", list);


        log.info("=============================================================================");
        return R.ok().data("list", list);
    }
}
