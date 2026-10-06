package com.itzhy.pojo;

import lombok.Data;

import java.time.LocalDate;

//教师教育经历实体类
@Data
public class TeacherExpr {
    private Integer id; //ID
    private Integer teacherId; //教师ID
    private LocalDate begin; //开始时间
    private LocalDate end; //结束时间
    private String school; //毕业院校
    private String degree; //学位
}
