package top.lingkang.annotation.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import top.lingkang.holder.FinalHolder;

/**
 * @author lingkang
 * Created by 2022/1/11
 */
@Aspect
public class FinalCheckLoginAnnotation {

    @Around("@annotation(top.lingkang.annotation.FinalCheckLogin)")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        FinalHolder.isLogin();
        return joinPoint.proceed();
    }
}
