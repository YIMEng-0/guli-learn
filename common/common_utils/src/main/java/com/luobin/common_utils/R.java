package com.luobin.common_utils;

/**
 * @author Doraemon
 * @date 2022/4/29 6:35 下午
 * @version 1.0
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果的类，方便在后期进行代码的调试，统一了结果才能更好的进行调试
 */

/**
 *  @Data 创建了 get set hashCode equals toString
 */
@Data
public class R {
    /**
     * 是不是成功的
     */
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    /**
     * 返回状态码
     */
    @ApiModelProperty(value = "返回码")
    private Integer code;

    /**
     * 返回执行的数据
     */
    @ApiModelProperty(value = "返回数据")
    private String message;

    /**
     * 返回的数据使用 Map 进行封装返回
     *
     * 返回到前端中的数据封装成为一个 Map 集合返回
     */
    private Map<String, Object> data = new HashMap<>();

    /**
     * 构造方法的私有化，不能让其他的人进行这个类的创建
     */
    private R() {

    }

    /**
     * 运行成功返回的方法
     * @return r 接口调用统一的返回数据，在这里进行了统一的定义
     */
    public static R ok() {
        R r = new R();

        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("运行成功");

        return r;
    }

    /**
     * 当 api 接口调用失败的时候，统一返回的数据格式是下面的这种形式
     * @return
     */
    public static R error() {
        R r = new R();

        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("运行失败");

        return r;
    }

    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }


    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}