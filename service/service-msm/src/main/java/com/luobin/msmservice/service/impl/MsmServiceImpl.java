package com.luobin.msmservice.service.impl;

import com.luobin.msmservice.service.MsmService;
import com.luobin.msmservice.utils.MailUtil163;
import com.luobin.msmservice.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/*************************
 *@author : YIMENG
 *@date : 2022/6/30 11:56
 *************************/
@Slf4j
@Service
public class MsmServiceImpl implements MsmService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String send(String mail) {
        log.info("传递到 send 方法的 mail 是：{}", mail);
        String code = RandomUtil.getFourBitRandom();
        boolean flag = MailUtil163.sendMail(mail, "验证邮件", "您的验证码是：" + "[" +  code +  "]" + " , 10 分钟内有效！");
        if (flag) {
            log.info("向 {} 发送验证码成功", mail);
            log.info("发送成功的验证码是：{}，这个验证码将会放到 Redis 中，key 是 邮箱号， value 是验证码", code);
            redisTemplate.opsForValue().set(mail, code, 10, TimeUnit.MINUTES);
            log.info("将 mail 以及 验证码放置到 redis 成功，有效时间 10 分钟");
            return code;
        } else {
            log.error("验证码发送失败！");
            return null;
        }
    }
}
