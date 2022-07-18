package com.luobin.educenter.entity.vo;

/*************************
 *@author : YIMENG
 *@date : 2022/7/6 22:13
 *************************/

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 使用邮箱进行登录
 */
@Data
public class RegisterMailVo {
    @ApiModelProperty(value = "邮箱")
    private String mail;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "昵称")
    private String nickname;
}
