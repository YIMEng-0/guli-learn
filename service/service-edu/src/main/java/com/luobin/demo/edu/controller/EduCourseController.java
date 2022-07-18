package com.luobin.demo.edu.controller;


import com.luobin.common_utils.R;
import com.luobin.demo.edu.entity.vo.CourseInfoVo;
import com.luobin.demo.edu.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author luobin
 * @since 2022-06-18
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    /**
     * controller 中注入 service
     */
    @Autowired
    EduCourseService courseService;

    /**
     * 添加课程基本信息的方法
     *  @RequestBody CourseInfoVo courseInfoVo
     *      这一句代码的含义是：前端发送的请求中存在数据，这个数据按照请求体的方式发送请求，请求体中的数据和后端 CourseInfoVo
     *      的字段是一一对应的，也就是将前端的数据封装在了 CourseInfoVo 这个类型中，接下来后端可以处理这个类型的数据即可，
     *      可以将数据放置到数据库中
     */
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        // 前端需要 id 进行页面的跳转使用，所以在这个地方将 id 进行一个返回处理
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    // 根据课程 id 查询课程的基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);

        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    /**
     * 修改课程信息
     *
     * 从前端中将需要修改的信息传递到后端，后端取到数据进行数据库的修改操作
     */
    @PostMapping("updateCourseId")
    public R updateCourseId(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);

        return R.ok();
    }















}