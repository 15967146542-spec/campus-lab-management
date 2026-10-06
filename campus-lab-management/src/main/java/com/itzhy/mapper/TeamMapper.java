package com.itzhy.mapper;

import com.itzhy.pojo.Team;
import com.itzhy.pojo.TeamQueryParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//课题组信息
@Mapper
public interface TeamMapper {

    //条件分页查询课题组列表(动态SQL，实现在 TeamMapper.xml)
    public List<Team> list(TeamQueryParam teamQueryParam);

    //新增课题组
    @Options(useGeneratedKeys = true, keyProperty = "id")//获取到主键-主键返回
    @Insert("insert into team(name, location, begin_date, end_date, leader_id, field, create_time, update_time)" +
            "    values(#{name}, #{location}, #{beginDate}, #{endDate}, #{leaderId}, #{field}, #{createTime}, #{updateTime})")
    void insert(Team team);

    //根据ID删除课题组
    @Delete("delete from team where id = #{id}")
    void deleteById(Integer id);

    //根据ID查询课题组
    @Select("select * from team where id = #{id}")
    Team getById(Integer id);

    //修改课题组(动态SQL，实现在 TeamMapper.xml)
    void update(Team team);

    //查询所有课题组列表
    @Select("select * from team order by update_time desc")
    List<Team> findAll();
}
