package com.luobin.demo.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luobin.common_utils.R;
import com.luobin.demo.edu.entity.EduTeacher;
import com.luobin.demo.edu.entity.vo.TeachQueryVo;

import com.luobin.demo.edu.service.EduTeacherService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author luobin
 * @since 2022-04-28
 */

/**
 *     @RestController = @Controller + @ResponseBody组成，等号右边两位注解简单介绍两句，
 *        就明白我们@RestController的意义了：
 *
 *     @Controller 将当前修饰的类注入SpringBoot IOC容器，使得从该类所在的项目跑起来的过程中，
 *        这个类就被实例化。当然也有语义化的作用，即代表该类是充当Controller的作用
 *     @ResponseBody 它的作用简短截说就是指该类中所有的API接口返回的数据，甭管你
 *        对应的方法返回Map或是其他Object，它会以Json字符串的形式返回给客户端
 */

/**
 * @RequestMapping 可以放置到类上面以及方法上面，放在类上面表示访问的根路径就是这个地方
 *                 放置在方法上面表示请求的根路径下面的子路径地址
 */

/**
 *      整体的访问逻辑是：请求来到后端，使用 service 调用相关的方法，service 调用 mapper ,
 * mapper 里面调用写了数据库代码的 SQL 语句，SQL  语句执行，查询数据库，得到最终的结果；
 *
 * @author yimeng
 */
@Api(description = "讲师管理") // 在 swagger 中生成的 ui 界面中，对于 controller 总体的描述
@Slf4j
@RestController
@RequestMapping("/edu/teacher")   // 前端访问的路径
@CrossOrigin
public class EduTeacherController {
    /**
     * Controller 中注入 service ,调用相关的方法
     */
    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询表中的所有数据
     * rest 风格 在访问的时候，可以添加上去相关的访问方法
     * 浏览器访问的地址 ：http://localhost:8001/edu/teacher/findAll
     * @return`
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        // 调用 service 里面的方法，实现查询所有的操作
        // 经过了 service 调用 mapper mapper 执行并且返回数据，数据的格式是 json
        List<EduTeacher> list = teacherService.list(null);
        log.info("/edu/teacher/findAll 这个接口查询到的所有讲师是：{}", list);

        return R.ok().data("items", list);
    }

    /**
     * 逻辑删除的方式
     *   @DeleteMapping("{id}") 表示将来的这个参数是从 URL 访问的路径取到的
     *   @PathVariable 表示的是这个参数是在路径中获取到的参数 得到路径中的 id 值
     */
    @ApiOperation(value = "根据 id 删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id) {
        // 服务调用相关方法即可

        boolean flag = teacherService.removeById(id);
        System.out.println("运行的结果是： " + flag);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 分页查询讲师的列表
     * @return 返回查询成功或者失败
     * 将查询的参数在请求路径中进行传递
     */
    @ApiOperation("查询指定的页")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current, @PathVariable long limit) {
        // 创建 page 对象
        // Page 里面传递的参数，1 标识当前页数 3 表示当前页的条数
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        // 调用方法，实现分页功能
        // pageTeacher 是翻页对象，后面是添加的条件，没有条件就写 null
        // 调用方法的时候，将分页的数据封装到 pageTeacher 里面去，
        teacherService.page(pageTeacher, null);

        // 在上面将所有的数据封装到了 pageTeacher 里面去，下面把数据取出来
        // 总记录数量
        long total = pageTeacher.getTotal();
        System.out.println("total: " + total);

        // 数据 List 的集合 就是在每一个 页面中的数据个体的集合
        List<EduTeacher> records = pageTeacher.getRecords();
        System.out.println("records:" + records);

        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * example–举例说明
     * @param current 当前页
     * @param limit 当前页中包含的数据条数
     * @param teachQuery 前端传递进行的查询条件
     * @return 统一的返回结果
     */
    @ApiOperation("条件查询，数据按照分页返回")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeachQueryVo teachQuery) {
        /**
         * @RequestBody 前面的请求按照 json 传递过来 前端的请求封装成为 TeachQuery 对象 传递进来的
         * @Responsebody 后端把数据打包成为 json 传递到前端
         *
         * 使用了  @RequestBody 需要使用 Post 请求 ,否则后端无法查看到前端的数据
         *
         * @ApiOperation 这个注解在 swagger 里面对于整个后端访问接口的简单描述
         */

        // 创建 page 对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        // 构建查询条件
        QueryWrapper wrapper = new QueryWrapper();

        /**
         * 创建出来的 wrapper 里面将查询的条件进行封装，传递到底层进行查询
         *
         * 下面有使用到 动态 SQL ，条件不为空的话，将条件拼接进去即可
         *
         * wrapper 里面对于每个字段，的处理都设置相关的条件，然后拼接成为一个完整的动态 SQL 进行查询
         */
        // 将前端传递过来的将所有查询条件封装成为了一个对象里面的各个参数记性解析，看传递了什么参数过来
        String name = teachQuery.getName();
        Integer level = teachQuery.getLevel();
        String begin = teachQuery.getBegin();
        String end = teachQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        wrapper.orderByDesc("gmt_create");
        teacherService.page(pageTeacher, wrapper);

        // 在上面将所有的数据封装到了 pageTeacher 里面去，下面把数据取出来
        long total = pageTeacher.getTotal();
        System.out.println("total: " + total);

        // 数据 List 的集合 就是在每一个 页面中的数据个体的集合
        List<EduTeacher> records = pageTeacher.getRecords();
        System.out.println("records:" + records);
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 将前端需要传递的教师对象在这里进行传递
     * 使用 json 的方式传递过来过来
     * 因为使用了 json 的方式传递了，所以在这个地方使用的是 @RequestBody 注解
     *
     * @param eduTeacher
     * @return
     */
    @ApiOperation("添加教师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save == true) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 修改讲师的信息
     *  1、查询回显
     *  2、将信息进行修改
     */
    @ApiOperation("根据 id 在修改前对讲师信息回显")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);

        // 在 ok 方法执行结束之后，进行连续调用，将数据按照对象的方法返回到前端
        return R.ok().data("teacher", eduTeacher);
    }

    /**
     * 将数据传递回来进行数据的修改， @RequestBody 与 @PostMapping 是放在一起使用的
     *
     * 没有一起使用的话，在参数的传递方面可能存在一定的问题
     */
    @ApiOperation("根据 id 修改讲师的内容")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);

//        try {
//            int i = 10 / 0;
//        } catch (Exception e) {
//            // 自行自定义异常
//            throw new GuliException(20001, "执行了自定义定义异常");
//        }

        if (flag == true) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}