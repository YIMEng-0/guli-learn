package com.luobin.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*************************
 *@author : YIMENG
 *@date : 2022/7/18 10:51
 *************************/

@SpringBootApplication
@EnableDiscoveryClient // 服务发现客户端
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
