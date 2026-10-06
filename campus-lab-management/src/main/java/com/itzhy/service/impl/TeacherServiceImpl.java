package com.itzhy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itzhy.mapper.TeacherExprMapper;
import com.itzhy.mapper.TeacherMapper;
import com.itzhy.pojo.*;
import com.itzhy.service.TeacherService;
import com.itzhy.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private TeacherExprMapper teacherExprMapper;

    //PageHelper分页查询
    //注意事项：1.定义的SQL语句结尾不能加分号
    //2.PageHelper仅仅能对紧跟在其后的第一个查询语句进行分页处理
    @Override
    public PageResult<Teacher> page(TeacherQueryParam teacherQueryParam) {
        //1.设置分页参数
        PageHelper.startPage(teacherQueryParam.getPage(), teacherQueryParam.getPageSize());

        //2.执行查询
        List<Teacher> teacherList = teacherMapper.list(teacherQueryParam);

        //3.解析查询结果，并封装
        Page<Teacher> p = (Page<Teacher>) teacherList;

        return new PageResult<Teacher>(p.getTotal(), p.getResult());
    }

    @Transactional(rollbackFor = Exception.class)//事务管理的注解
    @Override
    public void save(Teacher teacher) {
        //1.保存教师基本信息
        teacher.setCreateTime(LocalDateTime.now());
        teacher.setUpdateTime(LocalDateTime.now());
        teacherMapper.insert(teacher);

        //2.保存教师教育经历信息
        List<TeacherExpr> eduList = teacher.getEduList();
        if(!CollectionUtils.isEmpty(eduList)){
            //遍历集合，为teacherId赋值
            eduList.forEach(teacherExpr -> {
                teacherExpr.setTeacherId(teacher.getId());
            });
            teacherExprMapper.insertBatch(eduList); // 批量插入教师教育经历信息
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids) {
        //1.批量删除教师基本信息
        teacherMapper.deleteByIds(ids);

        //2. 批量删除教师教育经历信息
        teacherExprMapper.deleteByTeacherIds(ids);
    }

    @Override
    public Teacher getInfo(Integer id) {
        return teacherMapper.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)//事务管理注解
    @Override
    public void update(Teacher teacher) {
        //1.设置修改时间，并更新教师基本信息
        teacher.setUpdateTime(LocalDateTime.now());
        teacherMapper.update(teacher);

        //2.更新教师教育经历信息：先删后增
        List<TeacherExpr> eduList = teacher.getEduList();
        if (eduList == null) {
            //请求体未携带 eduList 字段（反序列化后为 null）：本次不修改教育经历
            //必须与“传了空集合”区分开，否则会把未传误当成清空，无条件删掉子表数据
            log.info("未提交教育经历字段，跳过子表更新，教师id：{}", teacher.getId());
            return;
        }

        Integer teacherId = teacher.getId();
        teacherExprMapper.deleteByTeacherId(teacherId);
        //eduList 为空集合：表示用户主动删光了所有教育经历，故只删不增
        if (!eduList.isEmpty()) {
            //遍历集合，为教师id赋值
            eduList.forEach(teacherExpr -> {
                teacherExpr.setTeacherId(teacherId);
            });
            teacherExprMapper.insertBatch(eduList);
        }
    }

    @Override
    public List<Teacher> findAll() {
        return teacherMapper.findAll();
    }

    @Override
    public LoginInfo login(Teacher teacher) {
        //1.调用Mapper接口，根据用户名和密码查询教师信息
        Teacher t = teacherMapper.selectUsernameAndPassword(teacher);
        //2.判断：是否存在这个教师，如果存在，组装登录成功信息
        if (t != null) {
            log.info("登录成功, 教师信息：{}", t);
            //生成JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",t.getId());
            claims.put("username",t.getUsername());
            String jwt = JwtUtils.generateToken(claims);

            return new LoginInfo(t.getId(), t.getUsername(), t.getName(), jwt);
        }
        //3.不存在，返回null
        return null;
    }
}
