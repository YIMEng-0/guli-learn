package com.luobin.demo.edu.controller;


import com.alibaba.excel.util.StringUtils;
import com.luobin.common_utils.R;
import com.luobin.demo.edu.client.VodClient;
import com.luobin.demo.edu.entity.EduVideo;
import com.luobin.demo.edu.service.EduVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author luobin
 * @since 2022-06-18
 */
@Slf4j
@RestController
@RequestMapping("/edu/edu-video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;  // 方便远程调用服务

    // 添加小节操作，所谓的添加小节就是添加视频，视频就是小节，章节是大节，在添加小节的过程中将视频传递上去
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        log.info("添加视频是：{} ", eduVideo);
        eduVideoService.save(eduVideo);
        log.info("添加视频成功，添加成功的视频的 id 是：{}", eduVideo.getId());
        return R.ok();
    }

    // 刪除小节 删除小节就是删除 edu_video 中的一行表记录
    // 删除小节同时把小节中的视频删除：
    // 想要做到同时删除，知识删除 edu_video 中的记录是没有用的，因为这里保存的只是视频的一个 video_id(aliyun 自动生成)
    // 所以使用注册中心进行远程调用 远程调用删除视频的微服务中的代码，在当前服务中做不到删除视频，删除视频需要阿里的 视频点播服务的 SDK 才可
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        log.info("删除小节视频中视频 id 是： {}", id);

        // 先查视频对象，，然后找到视频对象中的 SourceId
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();

        // 判断是否有视频,有就删除
        if (!StringUtils.isEmpty(videoSourceId)) {
            // 远程调用vod删除视频
            log.info("开始执行远程调用 vod 删除视频, videoSourceId 是：{}", videoSourceId);

            // 下面进行熔断器的测试
            R result = vodClient.removeAlyVideo(videoSourceId);
            if (result.getCode() == 20000) {

            }else {
                log.info("删除视频失败，使用了熔断器机制");
            }
        }
        /**
         * 首先删除小节中的视频，然后删除小节，直接删除小节存在的问题就是，没有视频的 video_id ，那么就会不能删除相应的视频
         */
        eduVideoService.removeById(id);
        return R.ok();
    }

    // 修改小节 TODO
}
