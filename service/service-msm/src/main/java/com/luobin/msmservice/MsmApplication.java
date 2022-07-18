package com.luobin.msmservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/*************************
 *@author : YIMENG
 *@date : 2022/6/30 11:52
 *************************/

@SpringBootApplication
@ComponentScan(basePackages = {"com.luobin"}) // 将 Swagger 里面的配置信息进行扫描，否则 Swagger 里面的配置是无法加载的 扫描这个包下面的所有相关的配置，将配置进行加载
public class MsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class, args);
    }
}
