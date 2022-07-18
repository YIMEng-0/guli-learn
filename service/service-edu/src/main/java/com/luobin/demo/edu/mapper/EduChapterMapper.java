package com.luobin.demo.edu.mapper;

import com.luobin.demo.edu.entity.EduChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author luobin
 * @since 2022-06-18
 */

/**
 *    extends BaseMapper 继承了一个接口，那么就继承了接口中所有的方法
 *  接口的方法是可以操作数据库的，但是需要指定操作的数据库的哪儿张表，所以使用了泛型 BaseMapper<EduChapter>
 *
 *      EduChapterMapper 这个接口继承了 BaseMapper 中的所有方法，由于自己也是接口，那么相当于拥有了所有的 BaseMapper 中的所有方法
 */
public interface EduChapterMapper extends BaseMapper<EduChapter> {

}
