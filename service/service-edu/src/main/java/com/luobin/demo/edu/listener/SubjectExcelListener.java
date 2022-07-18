package com.luobin.demo.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luobin.demo.edu.entity.EduSubject;
import com.luobin.demo.edu.entity.excel.SubjectData;
import com.luobin.demo.edu.exceptionhandler.GuliException;
import com.luobin.demo.edu.service.EduSubjectService;

/**
 * @author Doraemon
 * @date 2022/6/12 7:36 下午
 * @version 1.0
 */

/**
 * 实现一个监听器
 * <p>
 * 这个监听器是在 service 中 new 出来的，不需要使用 Spring 进行管理操作，
 * 因为没有使用 Spring 进行对象的管理，所以其他的对象在这里也是不能 Autowired 的，不可以直接注入进来
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    // 传递进来 方便添加以及查询功能的实现
    public EduSubjectService subjectService;

    public SubjectExcelListener() {

    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        // 使用构造方法将对象构造出来，构造方法中传递参数，传递的参数赋值到对象，使用 this 表示的就是即将被创造出来的对象的字段的赋值
        this.subjectService = subjectService;
    }

    /**
     * 读取 excel 里面的数据
     *
     * 每解析一行 Excel 会回调一次 invoke() 方法 EasyExcel 会自动的执行监听器里面的这个方法
     * @param subjectData
     * @param context
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext context) {
        // SubjectData 里面保存的是读取的 Excel 中的每一行的数据，每读取一行 Excel 数据 EasyExcel 将这行数据保存早 SubjectData 中，
        // 所以在下面会进行 SubjectData 数据的判断
        if (subjectData == null) {
            // 以前自己制定的异常机制，异常里面存在异常编码以及相关解释信息
            throw new GuliException(20001, "文件数据为空");
        }

        // 读取文件的时候，每次读取的是两个数值，第一个数值是一级分类、第二个值是二级分类
        // 将读取的数据加载到数据库中
        // 判断以及分类中是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (existOneSubject == null) { // 数据库表中没有以及分类，添加即可
            existOneSubject = new EduSubject(); // 上面的 existOneSubject 是 null 在这里赋值，按照 EduSubject 对象将数据放进去
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName()); // 一级分类名称
            subjectService.save(existOneSubject);
        }

        // 添加二级分类
        String parentId = existOneSubject.getId(); // 上一级的分类名称，下面的调用 existTwoSubject() 方法使用
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), parentId);
        if (existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(parentId);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName()); // 二级分类的名称
            subjectService.save(existTwoSubject); // 将拼装好的对象放入到数据库中
        }
    }

    /**
     * 在整个 Excel 读取结束之后，执行这个方法
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    /**
     * 判断一级分类中的名字不能重复添加
     * 使用 Mybatis-Plus 进行数据库查询
     * <p>
     * 查询数据库，看数据库中有没有名字是 name ， 并且 parent id = 0 的条目
     */
    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();

        // 根据等级的名字以及对应的 parent_id 判断这个条目有没有出现过
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");

        // 根据查询条件找到一个符合条件的条目
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    /**
     * 判断二级分类中的名字不能重复添加
     */
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String parentId) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", parentId);

        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }
}
