package com.luobin.educenter.controller;

import com.luobin.common_utils.JwtUtils;
import com.luobin.common_utils.R;
import com.luobin.educenter.entity.UcenterMember;
import com.luobin.educenter.entity.vo.LoginByPhoneVo;
import com.luobin.educenter.entity.vo.RegisterMailVo;
import com.luobin.educenter.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author luobin
 * @since 2022-07-06
 */
@RestController
@RequestMapping("/educenter/ucenter-member")
@CrossOrigin
@Slf4j
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    /**
     * 登录
     *
     *  再下面有一个坑：使用 GetMapping 是不能和 @RequestBody 搭配到一起使用的，是会报错的
     *
     *  一定需要使用 @PostMapping
     *
     *  再前端访问的 json 中，json 的最后一句话是没有逗号的，这个也是需要注意的
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("loginByMobile")
    public R loginUser(@RequestBody LoginByPhoneVo member) {
        // 调用 service 里面的方法实现登录
        // 如果通过查询数据库，得到的用户名以及密码都是正确存在的,那么返回 token 数值，这个 token 使用基于 JWT 的形式
        String token = memberService.loginByMobile(member);

        return R.ok().data("token", token);
    }

    @PostMapping("registerByMail")
    public R register(@RequestBody RegisterMailVo registerVo) {
        memberService.registerByMail(registerVo);

        return R.ok();
    }

    /**
     * 根据 token 获取用户信息
     *
     * 在第一次用户登录的时候，会生成一个 JWT token 返回到前端中，并且将 JWT token 放到了 cookie 中，
     * 后面用户访问的时候，拿着 cookie 里面的 JWT token 访问到下面的访问，解析 JWT Token 得到用户的 id,根据用户 id 将用户信息返回，在前端进行一个显示
     */
    @ApiOperation("用户注册")
    @GetMapping("getUserInfo")
    public R getUserInfo(HttpServletRequest request) {
        log.info("前端使用 token 进行访问， request 是：{}", request);

        // 调用 JWT 工具类的方法，根据 request 对象获取 JWT token 的头信息，得到用户 id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        // 根据 token 得到的用户 id 查询数据库，得到用户的相关信息
        UcenterMember member = memberService.getById(memberId);

        log.info("根据 token 解析出来的 id 获取到的用户信息是：{}", member);
        return R.ok().data("userInfo", member);
    }

    /**
     * 查询某一天的注册人数
     *
     *  其他的微服务进行调用，需要传递参数，
     */
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        log.info("获取某一天的注册人数，传递进来的日期是：{}", day);
        Integer count = memberService.countRegisterDay(day);

        log.info("数据库查询结束，日期对应的用户数量是：{}", count);
        return R.ok().data("countRegister", count);
    }
}
