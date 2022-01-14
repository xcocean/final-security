package top.lingkang.annotation.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import top.lingkang.FinalManager;
import top.lingkang.annotation.FinalCheck;
import top.lingkang.utils.AuthUtils;

import java.util.Optional;

/**
 * @author lingkang
 * Created by 2022/1/11
 */
@Aspect
public class FinalCheckAnnotation {
    @Autowired(required = false)
    private FinalManager manager;

    @Around("@annotation(top.lingkang.annotation.FinalCheck)")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        FinalCheck check = getAnnotation(joinPoint).orElseThrow(IllegalAccessException::new);
        String token = manager.getToken();
        if (check.orRole().length != 0)
            AuthUtils.checkRole(check.orRole(), manager.getSessionManager().getRoles(token));
        if (check.andRole().length != 0)
            AuthUtils.checkAndRole(check.andRole(), manager.getSessionManager().getRoles(token));
        if (check.orPermission().length != 0)
            AuthUtils.checkPermission(check.orPermission(), manager.getSessionManager().getPermission(token));
        if (check.andPermission().length != 0)
            AuthUtils.checkAndPermission(check.andPermission(), manager.getSessionManager().getPermission(token));
        return joinPoint.proceed();
    }

    private Optional<FinalCheck> getAnnotation(ProceedingJoinPoint pjp) throws SecurityException {
        return Optional.ofNullable(((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(FinalCheck.class));
    }

}
