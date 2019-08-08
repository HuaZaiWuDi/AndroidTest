package com.wesmarclothing.jniproject.aop;

import android.util.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @Package com.wesmarclothing.jniproject.aop
 * @FileName PermissionAspect
 * @Date 2019/7/18 10:26
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
@Aspect
public class PermissionAspect {

    /**
     * execution(@com.app.annotation.aspect.Permission(切入点路径) * *(..)) && @annotation(permission)"
     * 切点表达式的组成：
     * execution(@注解 访问权限 返回值的类型 包名.函数名(参数))
     */
    @Around("execution(@com.wesmarclothing.jniproject.aop.Premission * *(..)) && @annotation(premission)")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint, Premission premission) {
        try {
            Log.d("aroundJoinPoint", "aroundJoinPoint");
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
