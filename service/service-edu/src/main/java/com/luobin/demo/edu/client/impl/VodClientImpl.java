package com.luobin.demo.edu.client.impl;

import com.luobin.common_utils.R;
import com.luobin.demo.edu.client.VodClient;
import org.springframework.stereotype.Component;

@Component
public class VodClientImpl implements VodClient {
    /**
     * 下面这个方法是基于熔断器实现的方法，当进行了熔断操作之后下面的方法会执行
     * @param videoId
     * @return
     */
    @Override
    public R removeAlyVideo(String videoId) {

        return R.error().message("删除视频失败了，这里使用了熔断机制！");
    }
}
