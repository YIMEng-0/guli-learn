package com.luobin.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/*************************
 *@author : YIMENG
 *@date : 2022/7/8 16:08
 *************************/

@SpringBootApplication
@ComponentScan(basePackages = {"com.luobin"}) // 扫描组件
@MapperScan("com.luobin.order.mapper")        // 扫描 mapper 否则会报错
@EnableDiscoveryClient // 可以打开远程调用
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
