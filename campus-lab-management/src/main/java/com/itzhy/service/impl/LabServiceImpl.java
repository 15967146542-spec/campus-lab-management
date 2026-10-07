package com.itzhy.service.impl;

import com.itzhy.exception.BusinessException;
import com.itzhy.mapper.LabMapper;
import com.itzhy.mapper.TeacherMapper;
import com.itzhy.pojo.Lab;
import com.itzhy.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LabServiceImpl implements LabService {

    @Autowired
    private LabMapper labMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    @Cacheable(cacheNames = "labList")
    public List<Lab> findAll() {
        return labMapper.findAll();
    }

    @Override
    @CacheEvict(cacheNames = "labList", allEntries = true)
    public void deleteById(Integer id) {
        //1.校验实验室下是否关联有教师，有则不允许删除
        Long count = teacherMapper.countByLabId(id);
        if (count > 0) {
            throw new BusinessException("对不起，该实验室下有教师，不能直接删除！");
        }

        //2.调用Mapper接口方法删除数据
        labMapper.deleteById(id);
    }

    @Override
    @CacheEvict(cacheNames = "labList", allEntries = true)
    public void add(Lab lab) {
        //1.补全基础属性-createTime,updateTime
        lab.setCreateTime(LocalDateTime.now());
        lab.setUpdateTime(LocalDateTime.now());

        //2.调用Mapper接口方法插入数据
        labMapper.insert(lab);
    }

    @Override
    public Lab getById(Integer id) {
        return labMapper.getById(id);
    }

    @Override
    @CacheEvict(cacheNames = "labList", allEntries = true)
    public void update(Lab lab) {
        //1.补全基础属性
        lab.setUpdateTime(LocalDateTime.now());

        //2.调用Mapper接口方法更新数据
        labMapper.update(lab);

    }


}
