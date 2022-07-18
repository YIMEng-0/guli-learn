package com.luobin.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luobin.common_utils.R;
import com.luobin.educms.entity.CrmBanner;
import com.luobin.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 首页banner表 前端控制器
 * @author luobin
 * @since 2022-06-28
 */

/**
 * CrmBannerAdminController 这个是后台的管理人员进行的 banner 移动窗口图片的增删改查
 */
@Slf4j
@RestController // ResponseBody + Controller 将返回的结果转换为 json 字符串
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class CrmBannerAdminController {
    @Autowired
    CrmBannerService bannerService;
    /**
     * 前台请求，实现简单的分冶查询功能
     */
    @ApiOperation(value = "按照分页进行查询 banner ")
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {
        log.info("分页查询 banner 的 page ：{}", page);
        log.info("分页查询 banner 的 limit ：{}", limit);

        // 下面使用的 Page 是 MyBatis - Plus 中的类，使用 spring - framework 中的类是不对的
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        bannerService.page(pageBanner, null);

        log.info(" banner 查询结果是：{}", pageBanner.getRecords());
        log.info("=============================================================================");
        return R.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    /**
     * 添加 banner
     */
    @ApiOperation(value = "传递 banner 对象 添加 banner 到数据库")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        log.info("添加 banner 到数据库，前端传递的 banner 对象是：{}", crmBanner);
        bannerService.save(crmBanner);

        log.info("=============================================================================");
        return R.ok();
    }

    /**
     * 根据 id 将数据删除
     * @param id
     * @return
     */
    @ApiOperation(value = "根据 id 删除 Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        log.info("根据 id 删除 Banner, 当前传递的 banner 的 id 是：{}", id);
        bannerService.removeById(id);

        log.info("=============================================================================");
        return R.ok();
    }

    /**
     * 根据 id 将数据修改
     * @param banner
     * @return
     */
    @ApiOperation(value = "根据 id 修改 Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        log.info("根据 id 修改 Banner, 前端传递的 banner 对象是：", banner);
        bannerService.updateById(banner);

        log.info("=============================================================================");
        return R.ok();
    }

    /**
     * 获取 banner
     */
    @ApiOperation(value = "根据 id 获取 banner")
    @GetMapping("get/{id}")
    public R getBanner(@PathVariable String id) {
        log.info("根据 id 获取 banner, 前端传递的 id 是：{}", id);
        CrmBanner banner = bannerService.getById(id);

        log.info("=============================================================================");
        return R.ok().data("item", banner);
    }
}
