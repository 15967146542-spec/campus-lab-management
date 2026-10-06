package com.itzhy.service;

import com.itzhy.pojo.Lab;

import java.util.List;

public interface LabService {
    //查询所有的实验室数据
    List<Lab> findAll();

    //根据id删除实验室
    void deleteById(Integer id);

    //新增实验室
    void add(Lab lab);

    //根据id查询实验室
    Lab getById(Integer id);

    //修改实验室
    void update(Lab lab);
}
