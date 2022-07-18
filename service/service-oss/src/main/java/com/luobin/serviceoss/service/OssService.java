package com.luobin.serviceoss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Doraemon
 * @date 2022/5/31 11:22 上午
 * @version 1.0
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
