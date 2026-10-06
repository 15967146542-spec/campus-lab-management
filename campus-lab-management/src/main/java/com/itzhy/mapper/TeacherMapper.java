package com.itzhy.mapper;

import com.itzhy.pojo.Teacher;
import com.itzhy.pojo.TeacherQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//教师信息
@Mapper
public interface TeacherMapper {

    //条件分页查询(动态SQL，实现在 TeacherMapper.xml)
    public List<Teacher> list(TeacherQueryParam teacherQueryParam);

    //新增教师
    @Options(useGeneratedKeys = true, keyProperty = "id")//获取到主键-主键返回
    @Insert("insert into teacher(username, name, gender, phone, title, research_fund, image, hire_date, lab_id, create_time, update_time)" +
            "    values(#{username}, #{name}, #{gender}, #{phone}, #{title}, #{researchFund}, #{image}, #{hireDate}, #{labId}, #{createTime}, #{updateTime})")
    void insert(Teacher teacher);

    //根据ID批量删除教师的基本信息
    void deleteByIds(List<Integer> ids);

    //根据ID查询教师信息以及教师的教育经历信息
    Teacher getById(Integer id);

    //根据ID修改教师基本信息(动态SQL，实现在 TeacherMapper.xml)
    void update(Teacher teacher);

    //查询所有教师列表(课题组组长下拉数据源)
    @Select("select id, username, name, gender, phone, title, research_fund, image, hire_date, lab_id, create_time, update_time from teacher")
    List<Teacher> findAll();

    //统计指定实验室下的教师数量
    @Select("select count(*) from teacher where lab_id = #{labId}")
    Long countByLabId(Integer labId);

    //根据用户名和密码查询教师信息
    @Select("select id, username, name  from teacher where username = #{username} and password = #{password}")
    Teacher selectUsernameAndPassword(Teacher teacher);
}
