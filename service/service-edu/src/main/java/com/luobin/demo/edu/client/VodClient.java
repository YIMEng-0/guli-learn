package com.luobin.demo.edu.client;

import com.luobin.common_utils.R;
import com.luobin.demo.edu.client.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-vod", fallback = VodClientImpl.class) // name:需要调用的微服务的名字，被调用的服务的名字; fallback: 失败之后熔断器执行的代码
@Component // 将这个类交给 Spring 管理
public interface VodClient {
    // 定义调用方法的路径
    @DeleteMapping("/eduvod/video/removeAlyVideo/{videoId}") // 这个是调用的方法的路径
    // 调用远程的方法，就像调用本地的方法一样
    public R removeAlyVideo(@PathVariable("videoId") String videoId); // 获取路径上的参数，需要注明使用的是什么参数，也就是 @PathVariable("id")
}
