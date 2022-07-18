package com.luobin.demo.edu.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {
    private String id;
    private String title;

    /**
     * 在一个章节中存在多个小节，也就是多个小节的视屏，使用以及分类将二级分类给包裹起来实现
     */
    private List<VideoVo> children = new ArrayList<>();
}
