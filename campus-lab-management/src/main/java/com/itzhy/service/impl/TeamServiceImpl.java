package com.itzhy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itzhy.exception.BusinessException;
import com.itzhy.mapper.MemberMapper;
import com.itzhy.mapper.TeamMapper;
import com.itzhy.pojo.PageResult;
import com.itzhy.pojo.Team;
import com.itzhy.pojo.TeamQueryParam;
import com.itzhy.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private MemberMapper memberMapper;

    //PageHelper分页查询
    @Override
    public PageResult<Team> page(TeamQueryParam teamQueryParam) {
        //1.设置分页参数
        PageHelper.startPage(teamQueryParam.getPage(), teamQueryParam.getPageSize());

        //2.执行查询
        List<Team> teamList = teamMapper.list(teamQueryParam);

        //3.动态计算课题组状态
        LocalDate now = LocalDate.now();
        for (Team team : teamList) {
            if (now.isAfter(team.getEndDate())) {
                team.setStatus("已结题"); //当前时间 > 结题时间
            } else if (now.isBefore(team.getBeginDate())) {
                team.setStatus("筹备中"); //当前时间 < 立项时间
            } else {
                team.setStatus("运行中");
            }
        }

        //4.解析查询结果，并封装
        Page<Team> p = (Page<Team>) teamList;
        return new PageResult<Team>(p.getTotal(), p.getResult());
    }

    @Override
    public void add(Team team) {
        //1.补全基础属性-createTime,updateTime
        team.setCreateTime(LocalDateTime.now());
        team.setUpdateTime(LocalDateTime.now());

        //2.调用Mapper接口方法插入数据
        teamMapper.insert(team);
    }

    @Override
    public Team getById(Integer id) {
        return teamMapper.getById(id);
    }

    @Override
    public void update(Team team) {
        //1.补全基础属性
        team.setUpdateTime(LocalDateTime.now());

        //2.调用Mapper接口方法更新数据
        teamMapper.update(team);
    }

    @Override
    public void deleteById(Integer id) {
        //1.校验课题组下是否关联有成员，有则不允许删除
        Long count = memberMapper.countByTeamId(id);
        if (count > 0) {
            throw new BusinessException("对不起, 该课题组下有成员, 不能直接删除");
        }

        //2.调用Mapper接口方法删除数据
        teamMapper.deleteById(id);
    }

    @Override
    public List<Team> findAll() {
        return teamMapper.findAll();
    }
}
