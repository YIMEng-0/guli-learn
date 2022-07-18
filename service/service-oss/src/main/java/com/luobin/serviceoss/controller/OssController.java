package com.luobin.serviceoss.controller;

import com.luobin.common_utils.R;
import com.luobin.serviceoss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Doraemon
 * @date 2022/5/31 11:22 上午
 * @version 1.0
 */
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin // 防止出现跨域的问题
public class OssController {
    @Autowired
    private OssService ossService;

    /**
     * 处理上传头像的方法，将上传的文件放到 阿里云的 OSS 服务中，把 URL 放到数据库中，方便将来从阿里云中取出数据
     */
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        /**
         * 返回上传的 oss 路径
         */
        String url = ossService.uploadFileAvatar(file);

        return R.ok().data("url", url);
    }
}
