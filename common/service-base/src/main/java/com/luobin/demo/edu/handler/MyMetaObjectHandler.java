package com.luobin.demo.edu.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Doraemon
 * @date 2022/5/3 7:28 下午
 * @version 1.0
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 在添加以及修改字段的时候，对字段进行自动的填充
     *
     * 这里自动填充的是添加的以及创建的时间
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 在这里进行的字段的自动填充
        // 这里使用的是属性名称，也就是实体类的属性名称，不是数据库中的字段名称
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
