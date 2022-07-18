package com.luobin.demo.edu.controller;


import com.luobin.common_utils.R;
import com.luobin.demo.edu.entity.chapter.ChapterVo;
import com.luobin.demo.edu.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author luobin
 * @since 2022-06-18
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    EduChapterService eduChapterService;

    /**
     *  返回课程的大纲列表
     *  返回的课程大纲数据应该是，视频的一级分类的名字以及二级分类的名字
     */
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        // 添加的泛型是 chapterVo 因为这里面存在一级章节以及二级章节
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);

        return R.ok().data("allChapterVideo", list);
    }
}
