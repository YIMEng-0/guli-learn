package excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author Doraemon
 * @date 2022/6/3 4:54 下午
 * @version 1.0
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {
    /**
     * 读取表头的内容
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头：" + headMap);
    }

    /**
     * 一行一行的读取 Excel 里面的内容
     * @param data
     * @param context
     */
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        System.out.println("****" + data);
    }

    /**
     *数据读取完成之后做的事情
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
