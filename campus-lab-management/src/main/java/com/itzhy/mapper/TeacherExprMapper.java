package com.itzhy.mapper;

import com.itzhy.pojo.TeacherExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//教师教育经历
@Mapper
public interface TeacherExprMapper {

    //批量插入教师教育经历信息
    void insertBatch(List<TeacherExpr> eduList);

    //根据教师id批量删除教师教育经历信息
    void deleteByTeacherIds(List<Integer> ids);

    //根据单个教师id删除教师教育经历信息(用于修改时先删后增)
    void deleteByTeacherId(Integer teacherId);
}
