package ru.geekbrains.springmarket.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ControllersLoggingAspect {
    @Before("execution(public * ru.geekbrains.springmarket.controllers.ProductController.*(..))")
    public void beforeAnyMethodInProductControllerClass(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        StringBuilder message = new StringBuilder("AOP: In " + joinPoint.getTarget().getClass().getSimpleName() + " was called method " + methodSignature);
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            message.append(" with arguments: ");
            for (Object o : args) {
                message.append(o).append(" ");
            }
        }
        log.info(String.valueOf(message).trim());
    }

    @AfterReturning(
            pointcut = "execution(public * ru.geekbrains.springmarket.controllers.AuthController.createToken(*))",
            returning = "response")
    public void afterCreateTokenInAuthControllerClass(ResponseEntity<?> response) {
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            log.info("AOP: Create new token");
        } else {
            log.warn("AOP: Trying to create a token");
        }
    }
}
