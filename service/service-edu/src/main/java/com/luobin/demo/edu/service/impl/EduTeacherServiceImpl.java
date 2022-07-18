package com.luobin.demo.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luobin.demo.edu.entity.EduTeacher;
import com.luobin.demo.edu.mapper.EduTeacherMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author luobin
 * @since 2022-04-28
 */
@Service // Service 层对象的创建
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements com.luobin.demo.edu.service.EduTeacherService {

}
