package com.luobin.demo.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luobin.demo.edu.entity.EduChapter;
import com.luobin.demo.edu.entity.EduVideo;
import com.luobin.demo.edu.entity.chapter.ChapterVo;
import com.luobin.demo.edu.entity.chapter.VideoVo;
import com.luobin.demo.edu.mapper.EduChapterMapper;
import com.luobin.demo.edu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luobin.demo.edu.service.EduVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author luobin
 * @since 2022-06-18
 */

/**
 *      public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService 中的
 *   implements EduChapterService 一般来说，实现一些简单的功能，EduChapterService 这个接口是一个空接口，因为在下面的 extends ServiceImpl
 *   中的 ServiceImpl 实现了所有接口中的方法，不需要单独的再次实现一次方法，直接使用 MyBatis 中定义的方法即可。
 *      有的时候，不仅仅满足简单的数据库操作，比如阿里云服务，需要我们在 implements EduChapterService 中定义自己的方法，那么在 EduChapterServiceImpl
 *   中单独继续实现我们在接口中定义的方法即可。
 *
 *
 *
 *      ServiceImpl<EduChapterMapper, EduChapter> 这个泛型中提供了两个参数：
 *   第一个是 EduChapterMapper 接口的映射文件，在 EduChapterMapper 中继承了 BaseMapper<EduTeacher> {}
 *   这个 EduChapterMapper 继承了一大堆 MyBatis-Plus 中定义的的方法，这些方法是操作数据库的方法，使用这个泛型的目的就是为了将来获取到可以使用 ORM 思想操作数据库，
 *
 *   第二个是 EduChapter 有了操作数据库的能力还不行，还需要操作的数据，在 Java 中，需要操作的数据就是 Entity 数据
 *
 *   所以 ServiceImpl 这个泛型中的两个参数，第一个参数是提供了一大堆操作数据库的方法，这个方法是实际存在的，不仅仅知只是简单的接口定义（已经实现了接口中定义的方法）
 */
@Slf4j
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 首先查询章节信息 当作是一级目录
     *
     * 然后查询章节里面的视频，视频充当的是二级目录
     *
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        log.info("courseId 从前端传递过来是：{}", courseId);

        // 1、根据课程 id 查询课程里面的所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>(); // 对 EduChapter 这张表进行的操作
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChaptersList = baseMapper.selectList(wrapperChapter);// 查询所有和章节有关的信息
        log.info("eduChaptersList {}", eduChaptersList);

        // 2、根据课程 id 查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);
        log.info("eduVideoList{}", eduVideoList);

        // 创建最终封装的数据的 finalList
        List<ChapterVo> finalList = new ArrayList<>();

        // 3、遍历查询章节 list 集合进行封装
        for (int i = 0; i < eduChaptersList.size(); i++) {
            // eduChapter 先放到 chapterVo 中，然后再放到 finalList 中
            EduChapter eduChapter = eduChaptersList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            // 将一级分了放置到 finalList 中去，中间需要一次类型的转换
            BeanUtils.copyProperties(eduChapter, chapterVo);
            finalList.add(chapterVo);

            // 4、遍历查询小节 list 集合进行封装
            // 将符合条件的二级分类也放进去
            List<VideoVo> videoVoList = new ArrayList<>();
            for (int m = 0; m < eduVideoList.size(); m++) {
                // 得到每一个二级小节
                EduVideo eduVideo = eduVideoList.get(m);
                // 注意下面的比较逻辑，视频中的 chapter_id 是章节中 id 取值；也就是一个章节的 id 下面有多种视频，多种视频的 chapter_id 值相同
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            log.info( "章节里面的小节信息是： videoVoList{}", videoVoList);
            chapterVo.setChildren(videoVoList);
        }

        return finalList;
    }
}
