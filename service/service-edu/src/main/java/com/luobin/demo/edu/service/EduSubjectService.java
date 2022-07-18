package com.luobin.demo.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luobin.demo.edu.entity.EduSubject;
import com.luobin.demo.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author luobin
 * @since 2022-06-03
 */
public interface EduSubjectService extends IService<EduSubject> {
    // usage 表示的是调用关系
    // implementation 表示的是实现关系

    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
