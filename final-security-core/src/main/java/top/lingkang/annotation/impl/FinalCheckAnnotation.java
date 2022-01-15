package top.lingkang.annotation.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import top.lingkang.FinalManager;
import top.lingkang.annotation.FinalCheck;
import top.lingkang.utils.AuthUtils;

/**
 * @author lingkang
 * Created by 2022/1/11
 */
@Aspect
public class FinalCheckAnnotation {
    @Autowired(required = false)
    private FinalManager manager;

    @Around("@within(top.lingkang.annotation.FinalCheck) || @annotation(top.lingkang.annotation.FinalCheck)")
    public Object method(ProceedingJoinPoint joinPoint) throws Throwable {
        String token = manager.getToken();
        FinalCheck clazz = joinPoint.getTarget().getClass().getAnnotation(FinalCheck.class);
        if (clazz != null) {
            check(token, clazz);
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        FinalCheck method = signature.getMethod().getAnnotation(FinalCheck.class);
        if (method != null){
            check(token, method);
        }

        return joinPoint.proceed();
    }

    private void check(String token, FinalCheck check) {
        if (check.orRole().length != 0)
            AuthUtils.checkRole(check.orRole(), manager.getSessionManager().getRoles(token));
        if (check.andRole().length != 0)
            AuthUtils.checkAndRole(check.andRole(), manager.getSessionManager().getRoles(token));
        if (check.orPermission().length != 0)
            AuthUtils.checkPermission(check.orPermission(), manager.getSessionManager().getPermission(token));
        if (check.andPermission().length != 0)
            AuthUtils.checkAndPermission(check.andPermission(), manager.getSessionManager().getPermission(token));
    }
}
