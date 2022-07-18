package com.luobin.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@EnableFeignClients // 表示这个服务会调用其他的服务
@SpringBootApplication // 申明这是一个 SpringBoot 应用
@ComponentScan("com.luobin") // Spring 扫描相关的配置信息
@MapperScan("com.luobin.educms.mapper") // 可以单独写配置类进行添加，这里文件少，直接在此处添加即可
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
