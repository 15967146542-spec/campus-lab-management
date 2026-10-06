package com.itzhy.controller;

import com.itzhy.pojo.LoginInfo;
import com.itzhy.pojo.Result;
import com.itzhy.pojo.Teacher;
import com.itzhy.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 登录控制器
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private TeacherService teacherService;

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody Teacher teacher){
        log.info("登录：{}",teacher);
        LoginInfo info = teacherService.login(teacher);
        if(info != null){
            return Result.success(info);
        }
        return Result.error("用户名或密码错误");
    }
}
