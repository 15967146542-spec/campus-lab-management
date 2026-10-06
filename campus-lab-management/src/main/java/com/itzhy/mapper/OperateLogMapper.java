package com.itzhy.mapper;

import com.itzhy.pojo.OperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

//操作日志
@Mapper
public interface OperateLogMapper {

    //插入日志数据
    @Insert("insert into operate_log (operate_teacher_id, operate_time, class_name, method_name, method_params, return_value, cost_time) " +
            "values (#{operateTeacherId}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime});")
    public void insert(OperateLog log);

}
