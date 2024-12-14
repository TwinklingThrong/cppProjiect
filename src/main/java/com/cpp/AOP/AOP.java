package com.cpp.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Aspect
@Slf4j
@Component
public class AOP {
    @Pointcut("execution(* com.cpp.service..*.*(..))")
    private void pt() {}

    @Around("pt()")
    public Object  Around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LocalDate a = LocalDate.now();
        Object result = proceedingJoinPoint.proceed();
        log.info("\n时间:{}\n方法名:{}\n运行结果:{}",a,proceedingJoinPoint.getSignature(),result);
        return result;
    }
}
