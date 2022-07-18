package com.luobin.demo.edu.service;

import com.luobin.demo.edu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luobin.demo.edu.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author luobin
 * @since 2022-06-18
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);
}
