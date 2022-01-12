package top.lingkang.annotation.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import top.lingkang.annotation.FinalCheck;
import top.lingkang.holder.FinalHolder;
import top.lingkang.utils.AuthUtils;

import java.util.Optional;

/**
 * @author lingkang
 * Created by 2022/1/11
 */
@Aspect
public class FinalCheckAnnotation {
    @Around("@annotation(top.lingkang.annotation.FinalCheck)")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        FinalCheck check = getAnnotation(joinPoint).orElseThrow(IllegalAccessException::new);
        String token = FinalHolder.getToken();
        if (check.orRole().length != 0)
            AuthUtils.checkRole(check.orRole(), FinalHolder.getRole(token));
        if (check.andRole().length != 0)
            AuthUtils.checkAndRole(check.andRole(), FinalHolder.getRole(token));
        if (check.orPermission().length != 0)
            AuthUtils.checkPermission(check.orPermission(), FinalHolder.getPermission(token));
        if (check.andPermission().length != 0)
            AuthUtils.checkAndPermission(check.andPermission(), FinalHolder.getPermission(token));
        return joinPoint.proceed();
    }

    private Optional<FinalCheck> getAnnotation(ProceedingJoinPoint pjp) throws SecurityException {
        return Optional.ofNullable(((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(FinalCheck.class));
    }
}
