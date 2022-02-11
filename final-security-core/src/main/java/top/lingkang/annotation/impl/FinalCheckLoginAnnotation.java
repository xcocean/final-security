package top.lingkang.annotation.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalPermissionException;
import top.lingkang.http.FinalSecurityHolder;

/**
 * @author lingkang
 * Created by 2022/1/11
 */
@Aspect
public class FinalCheckLoginAnnotation {
    @Autowired(required = false)
    private FinalSecurityHolder securityHolder;

    @Around("@within(top.lingkang.annotation.FinalCheckLogin) || @annotation(top.lingkang.annotation.FinalCheckLogin)")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!securityHolder.isLogin()) {
            throw new FinalPermissionException(FinalConstants.UNAUTHORIZED_MSG);
        }
        return joinPoint.proceed();
    }
}
