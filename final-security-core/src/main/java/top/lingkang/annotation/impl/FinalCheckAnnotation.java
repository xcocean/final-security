package top.lingkang.annotation.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import top.lingkang.annotation.FinalCheck;
import top.lingkang.constants.FinalConstants;
import top.lingkang.http.FinalSecurityHolder;
import top.lingkang.utils.AuthUtils;

import java.io.FileNotFoundException;

/**
 * @author lingkang
 * Created by 2022/1/11
 */
@Aspect
public class FinalCheckAnnotation {
    @Autowired(required = false)
    private FinalSecurityHolder securityHolder;

    @Around("@within(top.lingkang.annotation.FinalCheck) || @annotation(top.lingkang.annotation.FinalCheck)")
    public Object method(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!securityHolder.isLogin()) {
            throw new FileNotFoundException(FinalConstants.NOT_LOGIN_MSG);
        }

        FinalCheck clazz = joinPoint.getTarget().getClass().getAnnotation(FinalCheck.class);
        if (clazz != null) {
            check( clazz);
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        FinalCheck method = signature.getMethod().getAnnotation(FinalCheck.class);
        if (method != null){
            check( method);
        }

        return joinPoint.proceed();
    }

    private void check(FinalCheck check) {
        if (check.orRole().length != 0)
            AuthUtils.checkRole(check.orRole(), securityHolder.getRole());
        if (check.andRole().length != 0)
            AuthUtils.checkAndRole(check.andRole(), securityHolder.getRole());
        if (check.orPermission().length != 0)
            AuthUtils.checkPermission(check.orPermission(), securityHolder.getPermission());
        if (check.andPermission().length != 0)
            AuthUtils.checkAndPermission(check.andPermission(), securityHolder.getPermission());
    }
}
