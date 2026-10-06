package com.itzhy.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class TeamQueryParam {
    private Integer page = 1;      //当前页码，默认第1页
    private Integer pageSize = 10; //每页显示记录数，默认10条
    private String name;           //课题组名称（模糊查询）

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;       //结题时间-起始

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;         //结题时间-结束
}
