package com.luobin.demo.edu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author Doraemon
 * @date 2022/6/12 7:12 下午
 * @version 1.0
 */
@Data
public class SubjectData {
    /**
     * 下面的代码代表的是 Excel 中的第一列以及第二列中的数据
     */
    @ExcelProperty(index = 0) // Excel 中第一列数据
    private String oneSubjectName;

    @ExcelProperty(index = 1) // Excel 中第二列数据
    private String twoSubjectName;
}
