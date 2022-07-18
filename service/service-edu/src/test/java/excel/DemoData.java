package excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author Doraemon
 * @date 2022/6/3 4:20 下午
 * @version 1.0
 */
@Data // get set hashCode equals toString
public class DemoData {
    /**
     * 设置表头名称
     */
    @ExcelProperty(value = "学生编号", index = 0)
    private Integer sno;

    @ExcelProperty( value = "学生名字", index = 1)
    private String sname;
}
