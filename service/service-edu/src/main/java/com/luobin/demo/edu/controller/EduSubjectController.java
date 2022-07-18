package com.luobin.demo.edu.controller;


import com.luobin.common_utils.R;
import com.luobin.demo.edu.entity.subject.OneSubject;
import com.luobin.demo.edu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author luobin
 * @since 2022-06-03
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 添加课程的分类，前段传递 excel 的分类信息，将分类信息存储在数据库中
     */
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) { // 得到上传的文件
        // 因为在 saveSubject() 中没有使用 Spring 管理，不能将 eduSubjectService 注入进去，所以直接将这服务传递过去
        // 将 eduSubjectService 传递过去之后，可以调用相关的方法，进行存储以及查询功能
        eduSubjectService.saveSubject(file, eduSubjectService);

        return R.ok();
    }

    /**
     *   前端请求 返回 Subject 的分类信息、没有必要返回所有的 Subject 定义的所有字段的值，
     * 只是返回了 Subject 中的 id 以及 parent_id 以及二级分类信息，
     * 所以将返回的数据封装成为了 OneSubject 数据
     *
     *      实现步骤：
     *          1、返回的格式需要和前端的数据格式是一一对应的；前端拿到的 json 数据也是需要拥有等级的信息存在的
     *          2、将数据封装
     *              1、对返回数据创建对应的实体类 两个实体类；一级以及二级实体类
     *              2、在两个实体类之间表示关系（一个一级分类中多个二级类）
     * @return
     */
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        // 注意：下面的 List 集合中返回的数据是 一级分类
        // 因为在一级分类中 代码中表示了一级分类一级二级分类。可以将两种分类都囊括进去
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();

        return R.ok().data("allSubjectClassList", list);
    }
}