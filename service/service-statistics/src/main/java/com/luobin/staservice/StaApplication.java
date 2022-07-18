package com.luobin.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/*************************
 *@author : YIMENG
 *@date : 2022/7/15 15:58
 *************************/

@SpringBootApplication
@ComponentScan(basePackages = "com.luobin")
@MapperScan(value = "com.luobin.staservice.mapper")
@EnableDiscoveryClient // 可以调用其他的服务
@EnableFeignClients
@EnableScheduling // 开启定时任务
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }
}
