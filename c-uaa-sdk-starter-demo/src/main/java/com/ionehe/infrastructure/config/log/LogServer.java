package com.ionehe.infrastructure.config.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Copyright (c) 2020 ionehe.com
 * Date: 2020/9/15
 * Time: 下午10:58
 *
 * @author 2020年 <a href="mailto:a@ionehe.com">秀</a>
 */
@Slf4j
@Aspect
@Component
public class LogServer {
    @Around("execution(public * com.ionehe.demo.controller..*.*(..))")
    public Object controllerLog(ProceedingJoinPoint point) throws Throwable{
        Long startTime = System.currentTimeMillis();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        // 获取参数数组
        String typeName = point.getSignature().getDeclaringTypeName();
        String methodName = point.getSignature().getName();
        String className = typeName.substring(typeName.lastIndexOf(".") + 1);
        Object[] args = point.getArgs();

        log.info(request.getMethod() + " " + request.getRequestURI());
        log.info("{}[]{}[]start! args:{}", className, methodName, args);

        //执行原方法并获取返回结果
        Object ret = null;
        try {
            ret = point.proceed(args);
        } catch (Exception e) {
            log.error("{}[]{}[]error! cause:{}", className, methodName, e.getCause());
            return ret;
        }

        Long endTime = System.currentTimeMillis();
        log.info("{}[]{}[]result:{}", className, methodName, ret);

        log.info("{}[]{}[]success! ###耗时:{}毫秒###", className, methodName, endTime - startTime);

        return ret;
    }
}
