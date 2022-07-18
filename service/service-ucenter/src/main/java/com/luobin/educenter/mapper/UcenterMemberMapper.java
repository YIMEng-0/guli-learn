package com.luobin.educenter.mapper;

import com.luobin.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author luobin
 * @since 2022-07-06
 */

public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    Integer countRegisterDay(String day);
}
