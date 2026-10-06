package com.itzhy.controller;

import com.itzhy.pojo.PageResult;
import com.itzhy.pojo.Result;
import com.itzhy.pojo.Team;
import com.itzhy.pojo.TeamQueryParam;
import com.itzhy.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//课题组管理Controller
@Slf4j
@RequestMapping("/teams")
@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    //条件分页查询
    @GetMapping
    public Result page(TeamQueryParam teamQueryParam) {
        log.info("条件分页查询： {}", teamQueryParam);
        PageResult<Team> pageResult = teamService.page(teamQueryParam);
        return Result.success(pageResult);
    }

    //新增课题组
    @PostMapping
    public Result add(@RequestBody Team team){
        log.info("新增课题组：{}", team);
        teamService.add(team);
        return Result.success();
    }

    //根据id查询课题组信息(修改回显)
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据ID查询课题组：{}", id);
        return Result.success(teamService.getById(id));
    }

    //修改课题组
    @PutMapping
    public Result update(@RequestBody Team team){
        log.info("修改课题组：{}", team);
        teamService.update(team);
        return Result.success();
    }

    //根据ID删除课题组
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("根据ID删除课题组：{}", id);
        teamService.deleteById(id);
        return Result.success();
    }

    //查询所有课题组列表
    @GetMapping("/list")
    public Result findAll(){
        log.info("查询所有课题组列表");
        List<Team> teamList = teamService.findAll();
        return Result.success(teamList);
    }
}
