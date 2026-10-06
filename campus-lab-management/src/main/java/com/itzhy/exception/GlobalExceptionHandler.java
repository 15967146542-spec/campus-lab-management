package com.itzhy.exception;


import com.itzhy.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常处理器
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //业务异常处理（如：班级下有学员不允许删除、部门下有员工不允许删除）
    @ExceptionHandler
    public Result handleBusinessException(BusinessException e){
        log.error("业务异常：{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler
    public Result handleException(Exception e){
        log.error("程序出错啦~",e);
        return Result.error("出错啦~，请联系管理员");

    }

    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e){
        log.error("程序出错啦~",e);
        String message = e.getMessage();
        int i = message.indexOf("Duplicate entry");
        String errMsg = message.substring(i);
        String[] arr = errMsg.split(" ");
        return Result.error(arr[2] + "已存在");
    }

    //参数校验失败处理（@RequestBody + @Validated 场景）
    @ExceptionHandler
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("参数校验失败：{}", e.getMessage());
        FieldError fieldError = e.getBindingResult().getFieldError();
        //fieldError 可能为 null（非字段级错误），故给出兜底提示
        String errMsg = fieldError == null ? "参数不合法" : fieldError.getDefaultMessage();
        return Result.error(errMsg);
    }
}
