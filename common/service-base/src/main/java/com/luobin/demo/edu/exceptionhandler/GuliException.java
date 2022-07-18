package com.luobin.demo.edu.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Doraemon
 * @date 2022/5/4 7:56 下午
 * @version 1.0
 */
@Data // get set hashCode equals
@AllArgsConstructor // 有参数的构造方法
@NoArgsConstructor // 无参数的构造方法
public class GuliException extends RuntimeException{
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String msg;
}
