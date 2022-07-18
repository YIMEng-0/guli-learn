package com.luobin.educenter.service;

import com.luobin.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luobin.educenter.entity.vo.LoginByPhoneVo;
import com.luobin.educenter.entity.vo.RegisterMailVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author luobin
 * @since 2022-07-06
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    String loginByMobile(LoginByPhoneVo member);

    void registerByMail(RegisterMailVo registerVo);

    Integer countRegisterDay(String day);
}
