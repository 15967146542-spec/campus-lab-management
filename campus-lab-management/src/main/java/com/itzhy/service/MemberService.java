package com.itzhy.service;

import com.itzhy.pojo.Member;
import com.itzhy.pojo.MemberQueryParam;
import com.itzhy.pojo.PageResult;

import java.util.List;

public interface MemberService {
    //条件分页查询
    //参数：memberQueryParam 封装了分页参数(page/pageSize)与查询条件(name/degree/teamId)
    PageResult<Member> page(MemberQueryParam memberQueryParam);

    //新增成员信息
    void save(Member member);

    //根据ID批量删除成员信息
    void delete(List<Integer> ids);

    //根据Id查询成员信息
    Member getInfo(Integer id);

    //修改成员信息
    void update(Member member);

    //安全违规处理
    void updateViolation(Integer id, Integer score);
}
