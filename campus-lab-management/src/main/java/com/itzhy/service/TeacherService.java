package com.itzhy.service;

import com.itzhy.pojo.LoginInfo;
import com.itzhy.pojo.PageResult;
import com.itzhy.pojo.Teacher;
import com.itzhy.pojo.TeacherQueryParam;

import java.util.List;

public interface TeacherService {
    //分页查询
    //参数：teacherQueryParam 封装了分页参数(page/pageSize)与查询条件(name/gender/begin/end)
    PageResult<Teacher> page(TeacherQueryParam teacherQueryParam);

    //新增教师信息
    void save(Teacher teacher);

    //删除教师信息
    void delete(List<Integer> ids);

    //根据Id查询教师信息
    Teacher getInfo(Integer id);

    //修改教师信息
    void update(Teacher teacher);

    //查询所有教师列表(课题组组长下拉数据源)
    List<Teacher> findAll();

    //教师登录
    LoginInfo login(Teacher teacher);
}
