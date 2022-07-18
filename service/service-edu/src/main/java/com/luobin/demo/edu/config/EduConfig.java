package com.luobin.demo.edu.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Doraemon
 * @date 2022/4/28 3:54 下午
 * @version 1.0
 */
@Configuration
@MapperScan("com.luobin.demo.edu.mapper") // 扫描 mybatis 的 mapper 文件
public class EduConfig {

    /**
     * 逻辑删除的插件
     * @return
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * 这个插件的配置都是在 config 文件中配置的，所谓的配置文件就是包含配置信息的类进行实例化
     * 这个类创建出来的对象被放置到 Spring 中进行管理，可以调用相关的功能
     *
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
