package com.luobin.educenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*************************
 *@author : YIMENG
 *@date : 2022/7/7 15:20
 *************************/
@Data
public class LoginByPhoneVo {
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;
}
