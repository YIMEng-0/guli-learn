package com.luobin.serviceoss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Doraemon
 * @date 2022/5/31 11:04 上午
 * @version 1.0
 */

@Component
public class ConstPropertiesUtils implements InitializingBean {
    /**
     * 读取配置文件
     */
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    /**
     * 定义静态常量，使得外面的代码可以使用这个六中的变量
     */
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    /**
     *  实现了 Spring 中的 InitializingBean 接口，在类的实例放置到 IOC 中之后，执行下面的方法，可以取出来原本
     * 不可以取出来的信息
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
