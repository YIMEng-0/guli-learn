package com.luobin.demo.edu.service;

import com.luobin.demo.edu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luobin.demo.edu.entity.chapter.ChapterVo;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author luobin
 * @since 2022-06-18
 */

/**
 *      EduChapterService 继承了所有 IService 的所有方法，在 IService<EduChapter> 中使用了泛型，相当于这个 EduChapterService 中能处理
 *  的数据只有 EduChapter ，处理其他的数据类型需要其他的 ****Service
 *
 *      既然这个接口继承了所有的 IService 中的所有方法，那么有的简单的操作数据库的方法，直接使用 IService 里面的方法即可，复杂的 service 方法是需要自己
 *  重写的
 *
 *     注意区分接口以及继承之间的关系，接口就是定义了一些没有实现的方法，继承就是得到父类中没有的方法，本质上都是为了完成某些功能，并且为了减少一定的代码量
 * 从而设计的接口以及父类；
 *
 *     在类中或者接口中使用了泛型，那么这个类或者接口只能处理具体类型的数据，其他类型的数据都使用不了
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);
}
