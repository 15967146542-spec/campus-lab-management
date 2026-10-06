package com.itzhy.service;

import com.itzhy.pojo.PageResult;
import com.itzhy.pojo.Team;
import com.itzhy.pojo.TeamQueryParam;

import java.util.List;

public interface TeamService {
    //条件分页查询
    //参数：teamQueryParam 封装了分页参数(page/pageSize)与查询条件(name/begin/end)
    PageResult<Team> page(TeamQueryParam teamQueryParam);

    //新增课题组
    void add(Team team);

    //根据ID查询课题组
    Team getById(Integer id);

    //修改课题组
    void update(Team team);

    //根据ID删除课题组
    void deleteById(Integer id);

    //查询所有课题组列表
    List<Team> findAll();
}
