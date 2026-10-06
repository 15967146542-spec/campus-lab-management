package com.itzhy.pojo;

import lombok.Data;

@Data
public class MemberQueryParam {
    private Integer page = 1;      //当前页码，默认第1页
    private Integer pageSize = 10; //每页显示记录数，默认10条
    private String name;           //姓名（模糊查询）
    private Integer degree;        //学历, 1:专科, 2:本科, 3:硕士, 4:博士
    private Integer teamId;        //课题组ID
}
