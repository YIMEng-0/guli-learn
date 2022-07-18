package com.luobin.staservice.client;

import com.luobin.common_utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*************************
 *@author : YIMENG
 *@date : 2022/7/15 17:23
 *************************/

/**
 * 远程调用的方法接口，相当于将被调用的方法在这里进行注册，在这个微服务中直接进行远程的调用方法即可
 */
@Component
@FeignClient("service-ucenter") // 需要调用的服务
public interface UcenterClient {
    /**
     * 将调用方法的路由进行注册，在 service 中直接调用即可
     * @param day
     * @return
     */
    @GetMapping("/educenter/ucenter-member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
