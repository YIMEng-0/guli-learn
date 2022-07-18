package com.luobin.demo.edu.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.luobin.demo.edu.entity.EduCourse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 这个 vo 类本质上就是 EduCourse 以及 EduCourseDescription 两个类中的部分字段的挑选集合
 */
@Data
public class CourseInfoVo{
    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "课程专业ID")
    private String subjectId;

    @ApiModelProperty(value = "课程专业父级ID")
    private String subjectParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    /**
     * 因为这个字段涉及到了金钱。所以使用了 BigDecimal 类型的变量
     */
    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;


    /**
     * 下面的字段涉及到了关于课程的描述信息，刚开始这个字段没有添加，在后面添加的
     */
    @ApiModelProperty(value = "课程ID")
    @TableId(value = "id", type = IdType.INPUT) // 这个数值是手动输入的，不是自动生成的
    private String id;

    /**
     * 下面是前端传递的关于课程简介的信息
     */
    @ApiModelProperty(value = "课程简介")
    private String description;
}
