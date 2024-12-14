package com.cpp.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Aspect
@Slf4j
@Component
public class AOPError {
    @Pointcut("execution(* com.cpp.service..*.*(..))")
    private void pt() {}

    @Around("pt()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            LocalDate a = LocalDate.now();
            log.error("\n时间:{}\n方法名:{}\n异常信息:{}", a, proceedingJoinPoint.getSignature(), e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}

   