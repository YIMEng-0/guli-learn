package com.luobin.order.service.impl;

import com.luobin.order.entity.Order;
import com.luobin.order.mapper.OrderMapper;
import com.luobin.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author luobin
 * @since 2022-07-08
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
