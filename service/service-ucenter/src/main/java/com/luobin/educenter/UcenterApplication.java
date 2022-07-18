package com.luobin.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/*************************
 *@author : YIMENG
 *@date : 2022/7/6 16:59
 *************************/
@ComponentScan("com.luobin")
@SpringBootApplication
@MapperScan(basePackages = {"com.luobin.educenter.mapper"})
@EnableDiscoveryClient // 这个注解说明了这个服务是可以被调用的
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
