package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

@Aspect
@Component
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(*)) && @annotation(com.sky.annotation.AutoFill)")     // 约定只传入一个实体类参数
    public void autoFillPointcut(){}

    @Before("autoFillPointcut()")
    public void autoFillUserTime(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length != 0) {
            // 转换为methodSignature才能获取到注解
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            // 获取方法上指定类型的注解
            AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);
            // 获取注解内的属性
            OperationType operationType = autoFill.operateType();
            // 约定只传入一个实体类参数
            Object arg = args[0];
            LocalDateTime now = LocalDateTime.now();
            Long currentUserId = BaseContext.getCurrentId();
            arg.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class).invoke(arg, now);
            arg.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER,Long.class).invoke(arg, currentUserId);
            if (operationType == OperationType.INSERT){
                arg.getClass().getMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class).invoke(arg, now);
                arg.getClass().getMethod(AutoFillConstant.SET_CREATE_USER,Long.class).invoke(arg, currentUserId);
            }
        }


        // 这样实现将界面限制在了员工，不方便扩展
//        if (args != null && args.length != 0 && args[0] instanceof Employee) {
//            Employee employee = (Employee) args[0];
//            employee.setUpdateUser(BaseContext.getCurrentId());
//            employee.setUpdateTime(LocalDateTime.now());
//
//            if (methodName.contains("insert")) {
//                employee.setCreateUser(BaseContext.getCurrentId());
//                employee.setCreateTime(LocalDateTime.now());
//            }
//            args[0] = employee;
//        }
    }
}
