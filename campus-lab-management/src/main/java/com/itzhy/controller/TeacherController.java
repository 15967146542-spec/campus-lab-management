package com.itzhy.controller;

import com.itzhy.pojo.PageResult;
import com.itzhy.pojo.Result;
import com.itzhy.pojo.Teacher;
import com.itzhy.pojo.TeacherQueryParam;
import com.itzhy.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//教师管理Controller
@Slf4j
@RequestMapping("/teachers")
@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    //分页查询
    @GetMapping
    public Result page(TeacherQueryParam teacherQueryParam) {
        log.info("分页查询： {}", teacherQueryParam);
        PageResult<Teacher> pageResult = teacherService.page(teacherQueryParam);
        return Result.success(pageResult);
    }

    //新增教师
    @PostMapping
    public Result save(@RequestBody @Validated Teacher teacher){
        log.info("新增教师：{}",teacher);
        teacherService.save(teacher);
        return Result.success();
    }

    //删除教师
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        log.info("删除教师：{}", ids);
        teacherService.delete(ids);
        return Result.success();
    }

    //根据id查询教师信息(修改回显)
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("查询教师信息：{}", id);
        return Result.success(teacherService.getInfo(id));
    }

    //修改教师
    @PutMapping
    public Result update(@RequestBody @Validated Teacher teacher){
        log.info("修改教师：{}", teacher);
        teacherService.update(teacher);
        return Result.success();
    }

    //查询所有教师列表(课题组组长下拉数据源)
    @GetMapping("/list")
    public Result findAll(){
        log.info("查询所有教师列表");
        List<Teacher> teacherList = teacherService.findAll();
        return Result.success(teacherList);
    }
}
