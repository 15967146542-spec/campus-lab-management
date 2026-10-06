package com.itzhy.mapper;

import com.itzhy.pojo.Lab;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

//实验室信息
@Mapper
public interface LabMapper {

    @Select("select id, name, create_time, update_time from lab order by update_time desc")
    List<Lab> findAll();

    @Delete("delete from lab where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into lab(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    void insert(Lab lab);

    @Select("select id, name, create_time, update_time from lab where id = #{id}")
    Lab getById(Integer id);

    @Update("update lab set name = #{name} , update_time = #{updateTime} where id = #{id};")
    void update(Lab lab);
}
