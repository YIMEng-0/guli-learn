package com.luobin.demo.edu.entity.chapter;

import lombok.Data;

/**
 *      vo 表示的是 view object
 *  表示的也就是前端向着后端传递的数据的封装，将数据封装成为了 vo 对象数据
 */
@Data
public class VideoVo {
    private String id;
    private String title;
}
