package org.example.reproject.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Aspects {

    @Before("execution(* org.example.reproject.*.*.*(..))")
    public void writeBeforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println(methodName+" methodu kullanıldı.");
    }
}
