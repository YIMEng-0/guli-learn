package com.luobin.demo.edu.controller.bannerfront;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luobin.common_utils.R;
import com.luobin.demo.edu.entity.EduCourse;
import com.luobin.demo.edu.entity.EduTeacher;
import com.luobin.demo.edu.service.EduCourseService;
import com.luobin.demo.edu.service.EduTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {
    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @GetMapping("index")
    public R index() {
        // 得到前 8 个热门的课程
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");
        final List<EduCourse> eduCourseList = courseService.list(wrapperCourse);

        // 查询热度排名前 4 的名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        final List<EduTeacher> eduTeacherList = teacherService.list(wrapperTeacher);

        log.info("根据 id 查询的讲师热度排名前 四 的是：{}", eduTeacherList);
        log.info("根据 id 查询的课程热度排名前 八 的是：{}", eduCourseList);

        log.info("=============================================================================");
        return R.ok().data("eduCourseList", eduTeacherList).data("eduTeacherList", eduTeacherList);
    }
}
