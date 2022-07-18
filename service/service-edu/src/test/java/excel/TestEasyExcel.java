package excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Doraemon
 * @date 2022/6/3 4:23 下午
 * @version 1.0
 */
public class  TestEasyExcel {
    public static void main(String[] args) {
        // 实现 excel 的写操作
        // 写文件夹的地址以及文件名称
        String filePath = "/Users/yimeng/Desktop/write.xlsx";

        // 调用 easyExcel 方法实现写操作
        // 设置 sheet 的名字，写入的数据就是 getData() 得到的数据
        EasyExcel.write(filePath, DemoData.class).sheet("学生列表").doWrite(getData());
    }

    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSname("Jack" + i);
            data.setSno(i);
            list.add(data);
        }
        return list;
    }

    /**
     * 使用 easyExcel 实现对于 Excel 表数据的读取
     */
    @Test
    public void testRead() {
        String readPath = "/Users/yimeng/Desktop/write.xlsx";

        EasyExcel.read(readPath, DemoData.class, new ExcelListener()).sheet().doRead();
    }
}
