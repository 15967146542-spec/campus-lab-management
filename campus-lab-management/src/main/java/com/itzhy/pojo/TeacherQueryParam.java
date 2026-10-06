package com.itzhy.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class TeacherQueryParam {
    private Integer page = 1;      //当前页码，默认第1页
    private Integer pageSize = 10; //每页显示记录数，默认10条
    private String name;           //姓名（模糊查询）
    private Integer gender;        //性别, 1:男, 2:女

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;       //入职日期-起始

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;         //入职日期-结束
}
