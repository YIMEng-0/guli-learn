package com.luobin.order.service.impl;

import com.luobin.order.entity.PayLog;
import com.luobin.order.mapper.PayLogMapper;
import com.luobin.order.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author luobin
 * @since 2022-07-08
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
