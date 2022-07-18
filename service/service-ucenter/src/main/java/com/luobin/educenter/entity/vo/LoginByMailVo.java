package com.luobin.educenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;

/*************************
 *@author : YIMENG
 *@date : 2022/7/7 15:05
 *************************/

public class LoginByMailVo {
    @ApiModelProperty(value = "邮箱")
    private String mail;

    @ApiModelProperty(value = "邮箱密码")
    private String mailPassword;
}
