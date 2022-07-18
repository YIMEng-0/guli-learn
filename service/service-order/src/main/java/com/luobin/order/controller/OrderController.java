package com.luobin.order.controller;


import com.luobin.common_utils.R;
import com.luobin.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author luobin
 * @since 2022-07-08
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/eduorder/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 生成订单的方法
     *   在生成订单的时候，需要传递课程的 id ，知道了是为什么课程创建的订单
     *
     */
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId) {

        return R.ok();
    }
}