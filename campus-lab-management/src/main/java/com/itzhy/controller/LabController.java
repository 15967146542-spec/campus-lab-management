package com.itzhy.controller;

import com.itzhy.pojo.Lab;
import com.itzhy.pojo.Result;
import com.itzhy.service.LabService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//实验室管理Controller
@Slf4j
@RequestMapping("/labs")
@RestController
public class LabController {

    @Autowired
    private LabService labService;

    //查询全部实验室数据
    @GetMapping
    public Result list(){
        log.info("查询全部实验室数据");
        List<Lab> labList = labService.findAll();
        return Result.success(labList);
    }

    //删除实验室
    @DeleteMapping
    public Result delete(Integer id){
        log.info("根据ID删除实验室：{}" , id);
        labService.deleteById(id);
        return Result.success();
    }

    //新增实验室
    @PostMapping
    public Result add(@RequestBody Lab lab){
        log.info("新增实验室：{}" ,  lab);
        labService.add(lab);
        return Result.success();
    }

    //根据ID查询实验室
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据ID查询实验室：{}" , id);
        Lab lab = labService.getById(id);
        return Result.success(lab);
    }

    //修改实验室
    @PutMapping
    public Result update(@RequestBody Lab lab){
        log.info("修改实验室：{}" ,  lab);
        labService.update(lab);
        return Result.success();
    }
}
