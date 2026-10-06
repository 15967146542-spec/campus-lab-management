package com.itzhy.aop;

import tools.jackson.databind.ObjectMapper;
import com.itzhy.mapper.OperateLogMapper;
import com.itzhy.pojo.OperateLog;
import com.itzhy.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

/**
 * 操作日志切面：记录系统所有增、删、改接口的运行信息。
 */
@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    // 用于把方法参数、返回值序列化为 JSON 字符串
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 切入点：拦截 controller 包下所有标注了增、删、改映射的方法。
     * 说明：LoginController#login 也是 @PostMapping，但它不属于增删改业务，下面单独排除。
     */
    @Around("execution(* com.itzhy.controller.*.*(..)) && ("
            + "@annotation(org.springframework.web.bind.annotation.PostMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.PutMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.DeleteMapping))")
    public Object recordOperateLog(ProceedingJoinPoint pjp) throws Throwable {
        // 登录接口不属于增删改，直接放行，不记录日志
        if ("login".equals(pjp.getSignature().getName())) {
            return pjp.proceed();
        }

        // 1.记录方法执行的开始时间
        long begin = System.currentTimeMillis();

        // 2.执行原始方法，获取返回值
        Object result = pjp.proceed();

        // 3.记录方法执行的结束时间，计算耗时
        long end = System.currentTimeMillis();

        // 4.组装操作日志对象
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateTeacherId(getCurrentTeacherId());       // 操作人
        operateLog.setOperateTime(LocalDateTime.now());              // 操作时间
        operateLog.setClassName(pjp.getTarget().getClass().getName()); // 目标类全类名
        operateLog.setMethodName(pjp.getSignature().getName());      // 方法名
        operateLog.setMethodParams(toJsonString(pjp.getArgs()));     // 方法运行时参数
        operateLog.setReturnValue(toJsonString(result));             // 返回值
        operateLog.setCostTime(end - begin);                         // 执行时长(ms)

        // 5.保存日志
        operateLogMapper.insert(operateLog);
        log.info("记录操作日志：{}", operateLog);

        return result;
    }

    /**
     * 从当前请求的 JWT 令牌中解析出操作人(教师)ID。
     */
    private Integer getCurrentTeacherId() {
        try {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return null;
            }
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("token");
            if (token == null || token.isEmpty()) {
                return null;
            }
            Claims claims = JwtUtils.parseToken(token);
            Object id = claims.get("id");
            return id == null ? null : Integer.valueOf(id.toString());
        } catch (Exception e) {
            log.warn("解析操作人ID失败", e);
            return null;
        }
    }

    /**
     * 把对象序列化为 JSON 字符串，序列化失败时降级为 toString，避免影响主流程。
     */
    private String toJsonString(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (Exception e) {
            return String.valueOf(value);
        }
    }
}
