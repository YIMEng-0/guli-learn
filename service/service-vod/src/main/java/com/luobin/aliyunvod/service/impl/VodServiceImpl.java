package com.luobin.aliyunvod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.luobin.aliyunvod.service.VodService;
import com.luobin.aliyunvod.util.AliyunVodSDKUtils;
import com.luobin.aliyunvod.util.ConstantVodUtils;
import com.luobin.demo.edu.exceptionhandler.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            //accessKeyId, accessKeySecret
            //fileName：上传文件原始名称
            // 01.03.09.mp4
            String fileName = file.getOriginalFilename();
            log.info("开始上传视频文件到阿里云服务器，上传的文件名是：{}", fileName);

            //title：上传之后显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoSourceId = null;
            if (response.isSuccess()) {
                // 上传成功之后 aliyun 的 SDK 中会返回一个视频的 videoId 这个 id 就是数据库表中的 video_source_id
                videoSourceId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoSourceId = response.getVideoId();
            }
            log.info("视频上传成功，视频的 videoSourceId 是 {}", videoSourceId);
            log.info("==============================================");
            return videoSourceId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void removeVideo(String videoSourceId) {
        log.info("开始执行 VodController 中调用 removeVideo() 方法，根据视频的 videoId 删除在阿里云存储的视频");
        try {
            //初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request中设置videoId
            request.setVideoIds(videoSourceId);
            //调用删除方法
            DeleteVideoResponse response = client.getAcsResponse(request);

            log.info("阿里云 SDK 删除视频的方法执行结束，删除的响应结果是：{}", response.getRequestId());
            log.info("==============================================");
        } catch (ClientException e) {
            throw new GuliException(20001, "视频删除失败");
        }
    }

    @Override
    public void removeAllVideo(List videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //遍历数组为 1,2,3,4
            String join = StringUtils.join(videoIdList.toArray(), ",");

            //向request中设置videoId
            request.setVideoIds(join);
            //调用删除方法
            DeleteVideoResponse response = client.getAcsResponse(request);

            System.out.print("RequestId = " + response.getRequestId() + "\n");

        } catch (ClientException e) {
            throw new GuliException(20001, "视频删除失败");
        }
    }
}
