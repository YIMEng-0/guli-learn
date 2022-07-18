package com.luobin.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luobin.common_utils.JwtUtils;
import com.luobin.demo.edu.exceptionhandler.GuliException;
import com.luobin.educenter.entity.UcenterMember;
import com.luobin.educenter.entity.vo.LoginByPhoneVo;
import com.luobin.educenter.entity.vo.RegisterMailVo;
import com.luobin.educenter.mapper.UcenterMemberMapper;
import com.luobin.educenter.service.UcenterMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author luobin
 * @since 2022-07-06
 */
@Slf4j
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    /**
     * 将 Redis 进行注入，方便取出来 Redis 的数据进行再次访问服务器的判断
     */
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 实现登录功能，登录成功之后返回 token
     *
     * @param member 前端传递的用户名对象，为了方便查询数据库进行对比，对比成功，那么就可以登录
     * @return 查询数据库，用户存在，那么返回 token ，方便用户的下次登录
     */
    @Override
    public String loginByMobile(LoginByPhoneVo member) {
        log.info("传递进来的 UcenterMember 对象是：{}", member);

        // 获取用户对象的手机号和密码然后判断
        String mobile = member.getMobile();
        String password = member.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登录失败，不存在的手机号！");
        }

        // 判断手机号是不是正确的
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if (mobileMember == null) { // 数据库没有找到号码，说明手机号码有错误，直接抛异常即可
            throw new GuliException(20001, "登录失败，手机号码错误！");
        }

        // 用户名字，手机号码存在，下面直接判断密码
        /**
         * 存储到数据库中的密码是经过加密的，下面需要进一步的处理
         *  处理方式就是：用户输入的密码仅过了加密之后，再和数据库中的密码是一样的
         *
         *  学习一种加密方式：MD5,只能加密，不能解密
         */
        if (!password.equals(mobileMember.getPassword())) {
            throw new GuliException(20001, "登陆失败，登录密码错误！");
        }

        // 判断用户是不是禁用的状态
        if (mobileMember.getIsDisabled()) { // if 进去，说明用户是禁用的状态
            throw new GuliException(20001, "登录失败，用户的状态是禁用的");
        }

        // 在上面该有的判断都已经存在了，执行到这个地方说明是登录成功的状态
        // 下面生成 JWT ,了解了 token 的作用以及长得形式之后，直接使用相关的工具类将 JWT 创建出来即可
        // 使用查询出来的 mobileMember 的 id 以及 昵称，因为执行到这，这个对象是存在的
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        log.info("传递出去的用户 token 数值是：{}", jwtToken);
        log.info("=========================================================================");
        return jwtToken;
    }

    @Override
    public void registerByMail(RegisterMailVo registerMailVo) {
        log.info("传递到注册方法 registerByMail() 中的 registerMailVo 是：{}", registerMailVo);
        // 获取注册的数据
        // 注册的数据是用户在前端输入的，所以命名的时候，使用了 register
        String registerMailVoCode = registerMailVo.getCode();
        String registerMailVoMail = registerMailVo.getMail();
        String registerMailVoNickname = registerMailVo.getNickname();
        String registerMailVoPassword = registerMailVo.getPassword();

        // 非空判断
        if (StringUtils.isEmpty(registerMailVoCode) || StringUtils.isEmpty(registerMailVoMail) ||
                StringUtils.isEmpty(registerMailVoNickname) || StringUtils.isEmpty(registerMailVoPassword)) {
            throw new GuliException(20001, "登录失败，检查相关信息是否填写完整");
        }

        // 判断用户输入的验证码 和 使用用户的邮箱注册的放置到 Redis 里面的验证码是不是一致的
        String redisCode = redisTemplate.opsForValue().get(registerMailVoMail).toString();// 将存储在 Redis 中的 mail 取出来做一个判断使用
        if (!redisCode.equals(registerMailVoCode)) { // Redis 里面存储邮箱对应的验证码和用户输入的验证码不一致，
            throw new GuliException(20001, "登录失败，验证码错误！");
        }

        // 判断邮箱号是不是已经注册过了，已经注册过了，重复的注册会显示注册失败
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper();
        queryWrapper.eq("mail", registerMailVoMail);
        UcenterMember databaseMail = baseMapper.selectOne(queryWrapper);

        if (!(databaseMail == null)) { // 非空的话，说明已经注册过了
            log.info("下面将会注册失败，因为这个邮箱以前注册过了，已经注册的邮箱信息是：{}", databaseMail);
            throw new GuliException(20001, "注册失败，邮箱已经注册过！");
        }

        // 用户填写的数据是正确的，并且数据库显示以前没有注册过，将用户注册的信息，添加到数据库中
        UcenterMember member = new UcenterMember();
        BeanUtils.copyProperties(registerMailVo, member);
        member.setIsDisabled(false); // 设置用户的状态不是禁用的，可以正常使用
        member.setAvatar("www........."); // 设置默认的头像

        baseMapper.insert(member);

        log.info("注册成功！");
    }

    @Override
    public Integer countRegisterDay(String day) {
        log.info("进入 countRegisterDay() 方法");
        Integer count = baseMapper.countRegisterDay(day);

        log.info("数据库查出来的符合日期的数量是：{}", count);
        return count;
    }
}
