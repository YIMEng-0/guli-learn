package com.luobin.demo.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Doraemon
 * @date 2022/5/3 4:47 下午
 * @version 1.0
 */
@Data
public class TeachQueryVo {
    /**
     *      @ApiModelProperty()  用于方法，字段；
     *      表示对model属性的说明或者数据操作更改;
     *
     *      在 swagger 自动生成的界面中，在 description 中会有下面的 value ,作为一个描述信息
     *
     *  目前使用到的：
     *      value–字段说明
     *      example–举例说明
     *
     *      name–重写属性名字
     *      dataType–重写属性类型
     *      hidden–隐藏
     */
    @ApiModelProperty(value = "教室名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1 是高级讲师，头衔 2 是首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
