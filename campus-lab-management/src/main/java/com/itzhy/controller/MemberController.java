package com.itzhy.controller;

import com.itzhy.pojo.Member;
import com.itzhy.pojo.MemberQueryParam;
import com.itzhy.pojo.PageResult;
import com.itzhy.pojo.Result;
import com.itzhy.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//课题组成员管理Controller
@Slf4j
@RequestMapping("/members")
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    //条件分页查询
    @GetMapping
    public Result page(MemberQueryParam memberQueryParam) {
        log.info("条件分页查询： {}", memberQueryParam);
        PageResult<Member> pageResult = memberService.page(memberQueryParam);
        return Result.success(pageResult);
    }

    //新增成员
    @PostMapping
    public Result save(@RequestBody @Validated Member member){
        log.info("新增成员：{}", member);
        memberService.save(member);
        return Result.success();
    }

    //根据ID批量删除成员
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("根据ID批量删除成员：{}", ids);
        memberService.delete(ids);
        return Result.success();
    }

    //根据id查询成员信息(修改回显)
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据ID查询成员信息：{}", id);
        return Result.success(memberService.getInfo(id));
    }

    //修改成员
    @PutMapping
    public Result update(@RequestBody @Validated Member member){
        log.info("修改成员：{}", member);
        memberService.update(member);
        return Result.success();
    }

    //安全违规处理
    @PutMapping("/violation/{id}/{score}")
    public Result updateViolation(@PathVariable Integer id, @PathVariable Integer score){
        log.info("安全违规处理：成员ID={}，扣除分数={}", id, score);
        memberService.updateViolation(id, score);
        return Result.success();
    }
}
