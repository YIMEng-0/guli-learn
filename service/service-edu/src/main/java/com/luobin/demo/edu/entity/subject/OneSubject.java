package com.luobin.demo.edu.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubject {
    private String id;
    private String title;

    // 在一个一级分类中存在多个二级分类
    // 使用了 ArrayList 集合，说明了在在一个一级分类中存在多个二级分类，将分类的信息保存即可
    private List<TwoSubject> children = new ArrayList<>();
}
