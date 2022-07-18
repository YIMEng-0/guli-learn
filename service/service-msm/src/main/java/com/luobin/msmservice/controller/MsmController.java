package com.luobin.msmservice.controller;

import com.luobin.common_utils.R;
import com.luobin.msmservice.service.MsmService;
import com.luobin.msmservice.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/*************************
 *@author : YIMENG
 *@date : 2022/6/30 11:54
 *************************/

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/edumsm/msm")
public class MsmController {
    @Autowired
    private MsmService msmService;

    /**
     * 前端传递进来需要注册的邮箱，向邮箱中发送验证码
     */
    @GetMapping("send/{mail}")
    public R sendMsm(@PathVariable String mail) {
        String code = msmService.send(mail);
        if (code != null) {
            return R.ok().data("发送成功，验证码是：", code);
        } else {
            return R.error().message("短信发送失败");
        }
    }
}
