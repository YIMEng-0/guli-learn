package com.luobin.aliyunvod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.luobin.aliyunvod.service.VodService;
import com.luobin.aliyunvod.util.AliyunVodSDKUtils;
import com.luobin.aliyunvod.util.ConstantVodUtils;
import com.luobin.common_utils.R;
import com.luobin.demo.edu.exceptionhandler.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    @PostMapping("uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file) {
        log.info("上传到 controller 需要上传的 视频 的文件是：{}", file);
        String VideoId = vodService.uploadVideo(file);
        return R.ok().data("VideoId", VideoId);
    }

    /**
     * 根据视频 videoSourceId 将视频删除
     * @param videoSourceId 数据库中视频的 videoSourceId
     * @return
     */
    @DeleteMapping("/removeAlyVideo/{videoSourceId}")
    public R removeVideo(@PathVariable String videoSourceId) {
        log.info("/eduvod/video/removeAlyVideo/{videoSourceId} 这个接口中传递的 videoSourceId 是： {}", videoSourceId);

        vodService.removeVideo(videoSourceId);
        return R.ok().message("视频删除成功");
    }

    // 删除多个阿里云视频
    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam List videoIdList) {
        vodService.removeAllVideo(videoIdList);
        return R.ok();
    }

    // 根据视频id获得视频凭证
    @GetMapping("getPlayAuth/{videoSourceId}")
    public R getPlayAuth(@PathVariable String videoSourceId) {
        try {
            //创建初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request对象中设置视频id
            request.setVideoId(videoSourceId);

            //调用方法获得凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"视频playAuth获取失败");
        }
    }
}
