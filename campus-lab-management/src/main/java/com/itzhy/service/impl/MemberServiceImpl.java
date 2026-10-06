package com.itzhy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itzhy.mapper.MemberMapper;
import com.itzhy.pojo.Member;
import com.itzhy.pojo.MemberQueryParam;
import com.itzhy.pojo.PageResult;
import com.itzhy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    //PageHelper分页查询
    @Override
    public PageResult<Member> page(MemberQueryParam memberQueryParam) {
        //1.设置分页参数
        PageHelper.startPage(memberQueryParam.getPage(), memberQueryParam.getPageSize());

        //2.执行查询
        List<Member> memberList = memberMapper.list(memberQueryParam);

        //3.解析查询结果，并封装
        Page<Member> p = (Page<Member>) memberList;
        return new PageResult<Member>(p.getTotal(), p.getResult());
    }

    @Override
    public void save(Member member) {
        //1.补全基础属性-createTime,updateTime(违规次数、违规扣分由数据库默认值0初始化)
        member.setCreateTime(LocalDateTime.now());
        member.setUpdateTime(LocalDateTime.now());

        //2.调用Mapper接口方法插入数据
        memberMapper.insert(member);
    }

    @Override
    public void delete(List<Integer> ids) {
        memberMapper.deleteByIds(ids);
    }

    @Override
    public Member getInfo(Integer id) {
        return memberMapper.getById(id);
    }

    @Override
    public void update(Member member) {
        //1.补全基础属性
        member.setUpdateTime(LocalDateTime.now());

        //2.调用Mapper接口方法更新数据
        memberMapper.update(member);
    }

    @Override
    public void updateViolation(Integer id, Integer score) {
        memberMapper.updateViolation(id, score);
    }
}
