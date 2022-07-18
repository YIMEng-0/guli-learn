package com.luobin.demo.edu.exceptionhandler;

import com.luobin.common_utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Doraemon
 * @date 2022/5/4 4:25 下午
 * @version 1.0
 */

/**
 * 设置发生异常之后，返回统一的结果，就像成功的时候也是返回统一的结果一样，这样子是方便程序的调试的
 * @author yimeng
 */
@ControllerAdvice
@Slf4j // 为了将日志信息添加到日志文件中
public class GlobalExceptionHandler {
    /**
     * 全局异常处理
     *
     * 所有的额异常都会到这个地方
     */
    @ExceptionHandler(Exception.class) // 执行捕捉到的异常是什么类型，可以自定义捕捉的异常类型
    @ResponseBody // 这个异常为了返回数据
    public com.luobin.common_utils.R error(Exception e) {
        e.printStackTrace();

        return R.error().message("全局异常在这里执行了...");
    }

    /**
     * 特定的异常处理
     *
     * 定义了特定的异常之后，程序会在特定的异常中捕捉，没有指定的异常会在在全局异常中捕捉
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public com.luobin.common_utils.R error(ArithmeticException e) {
        e.printStackTrace();

        return R.error().message("ArithmeticException 异常在这里执行了...");
    }

    /**
     * 自定义异常处理，不满足需求之后，自定义的异常
     */
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public com.luobin.common_utils.R error(GuliException e) {
        e.printStackTrace();

        log.error(e.getMessage());
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
