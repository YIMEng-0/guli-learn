package com.luobin.demo.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luobin.demo.edu.entity.EduSubject;
import com.luobin.demo.edu.entity.excel.SubjectData;
import com.luobin.demo.edu.entity.subject.OneSubject;
import com.luobin.demo.edu.entity.subject.TwoSubject;
import com.luobin.demo.edu.listener.SubjectExcelListener;
import com.luobin.demo.edu.mapper.EduSubjectMapper;
import com.luobin.demo.edu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author luobin
 * @since 2022-06-03
 */
@Slf4j
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    /**
     * 调用这个方法，使用 EasyExcel 读取数据将分类信息存储在数据库中
     * @param file
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream in = file.getInputStream();
            /**
             * in ： 将文件转换为 输入流的形式
             * SubjectClass.class ：Class 类型的参数，表示的是 Excel 文件中列的信息，里面有几列，列的名字是什么
             * new SubjectExcelListener(eduSubjectService) : 读取文件的第一个监听器，里面定义了具体读取每一行数据做的操作
             *      在这个功能实现的监听器中，如果读取的数据存在那么就不保存，读取的数据数据局中不存在才会放在数据库中
             */
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  查询数据库，得到所有的以及信息，以及二级分类信息
     *  将一级信息以及二级信息进行封装，封装成为 OneSubject 类型返回即可
     * @return
     */
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 1、查询出来所有的一级分类；数据库中的 parent_id 的数值都是 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0"); // eq 表示等于
        // baseMapper 是 MPS （Mybatis - Plus）自己封装好的一个字段，在 Spring 中将 Mapper 进行一个自动注入
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        // 2、查询出来所有的二级分类；数据库中的 parent_id 的数值不是 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0"); // ne 表示不等于
        // baseMapper 是 MPS 自己封装好的一个字段，在 Spring 中将 Mapper 进行一个自动注入

        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        log.info("数据库查询出来的二级分类所有结果是：{}", twoSubjectList);
        log.info("数据库查询出来的二级分类所有结果是：{}", twoSubjectList);

        // 创建 List 集合，存储最终的数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        // 3、封装一级分类
        for (int i = 0; i < oneSubjectList.size(); i++) {
            // 首先处理的是数据库中取出来的数据，这个数据是 EduSubject 实体数据（数据库的一种 ORM 映射）
            EduSubject eduSubject = oneSubjectList.get(i);
            // 然后将 eduSubject 中的数据取出来，放到 OneSubject 对象里面去
            OneSubject oneSubject = new OneSubject();
            /**
             *  oneSubject.setId(eduSubject.getId());
             *  oneSubject.setTitle(eduSubject.getTitle());
             *
             *  当属性比较多的时候，使用这个一个一个取出来赋值，显然是效率比较低下的，所以使用下面的方式
             *  BeanUtils.copyProperties(eduSubject, oneSubject);
             */
            BeanUtils.copyProperties(eduSubject, oneSubject);

            // 多个 OneSubject 放到 finalSubjectList 对象里面去
            finalSubjectList.add(oneSubject);


            // 在一级分类下面寻找二级分类，将二级分类进行封装
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                // 取出来每一个二级分类
                EduSubject tSubject = twoSubjectList.get(j);
                // 判断子类的 parent_id 和父类的 id 需要一样的时候，证明找到了一级分类下面的子类，将子类进行一个封装
                if (tSubject.getParentId().equals(eduSubject.getId())) {
                    // 从数据库中获得的数据是 EduSubject 类型的，数据太多，所以使用 BeanUtils.copyProperties(tSubject, twoSubject)，
                    // 将数据转换成为 twoSubject 使用的数据
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);

                    // 抽取出来 twoSubject 数据之后，将数据封装成为 twoFinalSubjectList 列表 ，传递到将来 OneSubject 下面的 Children 字段下面
                    twoFinalSubjectList.add(twoSubject);
                }
            }

            log.info("找到的二级分类是： {}", twoFinalSubjectList);
            log.error("找到的二级分类是： {}", twoFinalSubjectList);
            // 将找到的二级分类放置到一级分类下面
            oneSubject.setChildren(twoFinalSubjectList);
        }

        // 4、封装二级分类
        // 在一级循环中遍历得到二级循环

        return finalSubjectList;
    }
}