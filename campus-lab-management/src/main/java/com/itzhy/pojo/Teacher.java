package com.itzhy.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//教师实体类（连表查询时封装实验室名称、教育经历信息）
@Data
public class Teacher {
    private Integer id; //ID
    private String username; //用户名
    private String password; //密码
    @NotBlank(message = "姓名为必填项")
    private String name; //姓名
    @NotNull(message = "性别为必填项")
    private Integer gender; //性别, 1:男, 2:女
    //手机号为可选字段：允许为空（null 或空串），一旦填写则必须是合法手机号
    //注：@Pattern 对 null 不校验，但对空串会校验，所以正则需显式接受空串
    @Pattern(regexp = "(^$)|(^1[3-9]\\d{9}$)", message = "手机号格式不正确")
    private String phone; //手机号
    private Integer title; //职称, 1:教授, 2:副教授, 3:讲师, 4:助教, 5:研究员
    private Integer researchFund; //科研经费(万元)
    private String image; //头像
    private LocalDate hireDate; //入职日期
    private Integer labId; //所属实验室ID
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //修改时间
    private String labName; //封装实验室名称 (连表查询)
    private List<TeacherExpr> eduList; //封装教育经历信息
}
