package com.luobin.demo.edu.controller;

/**
 * @author Doraemon
 * @date 2022/5/11 11:56 上午
 * @version 1.0
 */

import com.luobin.common_utils.R;
import org.junit.Test;
import org.springframework.web.bind.annotation.*;

/**
 * 创建 下面的 controller 用来用户的登录以及返回用户的信息
 *
 * RestController 目的是为了将这个类的实例由 Spring 管理，并且可以返回数据 json
 * RequestMapping 前端发送请求的路径
 */

@RestController
@RequestMapping("/eduservice/user/")
@CrossOrigin // 解决跨域问题
public class EduLoginController {
    /**
     * 用户的登录
     */
    @PostMapping("login")
    public R login() {

        return R.ok().data("token","admin");
    }

    /**
     * 用户登录之后 信息的返回
     *
     * @return
     */
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "[admin]").data("avatar", "https://img.wxcha.com/m00/f3/7c/a4b0cd2f15dff9011d1460fb9ae05eee.jpg");
    }
}
