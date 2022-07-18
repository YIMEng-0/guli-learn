package com.luobin.demo.edu.service.impl;

import com.luobin.demo.edu.entity.EduCourse;
import com.luobin.demo.edu.entity.EduCourseDescription;
import com.luobin.demo.edu.entity.vo.CourseInfoVo;
import com.luobin.demo.edu.exceptionhandler.GuliException;
import com.luobin.demo.edu.mapper.EduCourseMapper;
import com.luobin.demo.edu.service.EduCourseDescriptionService;
import com.luobin.demo.edu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author luobin
 * @since 2022-06-18
 */
@Slf4j
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    // 在自己的 service 中，只是存在自己的 baseMapper ,下面的代码是在 EduCourseServiceImpl 中的，
    // 是不能把 courseInfoVo 里面的课程描述信息直接存储在 EduCourseDescription 表中的，需要使用其自身的 service 才可以向着数据库中存放数据
    // 需要将数据放置到相关的数据库表中的时候，就要将数据库表所对应的服务注入进来，注入进来的服务的 baseMapper 具有直接使用 ORM 修改数据库中表的资格
    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    /**
     * 将前端传递的参数放到 service 层进行处理，在这里会和数据库打交道，使得最终的数据被放置到数据库中
     *
     * 下面的 usage 表示其他的类有调用这个方法
     * implements 表示实现了某个接口
     * @param courseInfoVo
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        log.info("传递进来的参数是：{}", courseInfoVo);
        // 1、向着课程表中添加相关的信息
        // baseMapper 是因为这个类 extends 了 ServiceImpl ，在 ServiceImpl 中存在 baseMapper 这个字段，就直接继承使用了
        // 同时也获取到了一些对于数据的基本操作的功能，在 ServiceImpl 中都有

        // 传递进来的参数 courseInfoVo 是不能直接使用的，所以需要类型的转换
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        log.info("传出去的 eduCourse 的数值是： {}", eduCourse);
        int inset = baseMapper.insert(eduCourse);
        if (inset <= 0) {
            log.info("添加课程失败,所在的类是： {}", this.getClass().getName()); // 打印出来出错的类名字，方便定位错误
            throw new GuliException(20001, "课程信息添加失败");
        }

        // 获取添加成功之后的课程的 id
        String courseId = eduCourse.getId();

        // 2、想课程描述表中添加相关的信息
        // 课程描述表单独存放的原因就是，描述信息太大，绑定在一起的话。查到的数据量是比较大的，会降低效率，所以分开
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        log.info("传出去的 eduCourseDescription 数据是 ： {}", eduCourseDescription);
        eduCourseDescription.setId(courseId); // 手动设置描述的 id 就是课程的 id , 因为这两张表示一一对应的关系

        // 上面的 baseMapper 只能存储 EduCourse 类型数据，存储 eduCourseDescription 的数据，需要将 eduCourseDescription 的 service
        // 注入进来使用
        // 使用的是 eduCourseDescriptionService 的方法，这个方法在底层也是使用自己的 baseMapper 操作数据库的
        eduCourseDescriptionService.save(eduCourseDescription);

        return courseId;
    }

    /**
     * 根据课程 id 获取到课程的相关信息 同时获取到课程的描述信息，将来将获取到的数据返回到前端处理
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        // 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        // 查询描述表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(courseDescription, courseInfoVo);

        return courseInfoVo;
    }

    /**
     * 根据前端传递的 CourseInfoVo 将课程进行修改
     * @param courseInfoVo
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 1、修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);

        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }

        // 2、修改课程描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);

        // 将前端传递进来的数值修改到数据库中
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }
}
