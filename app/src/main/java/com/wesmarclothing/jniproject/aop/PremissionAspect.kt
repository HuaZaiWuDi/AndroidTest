//package com.wesmarclothing.jniproject.aop
//
//import android.content.pm.PackageManager
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.wesmarclothing.jniproject.App
//import com.wesmarclothing.kotlintools.kotlin.d
//import org.aspectj.lang.ProceedingJoinPoint
//import org.aspectj.lang.annotation.Around
//import org.aspectj.lang.annotation.Aspect
//import org.aspectj.lang.annotation.Pointcut
//
///**
// * @Package com.wesmarclothing.jniproject.aop
// * @FileName PremissionAspect
// * @Date 2019/7/16 15:03
// * @Author JACK
// * @Describe TODO
// * @Project JNIProject
// */
//@Aspect
// class PremissionAspect {
//
//
//    @Pointcut("execution(@com.wesmarclothing.jniproject.aop.Premission * *(..))")
//    fun onPermissionPointcut() {
//
//    }
//
//    /**
//     * execution(@com.app.annotation.aspect.Permission(切入点路径) * *(..)) && @annotation(permission)"
//     * 切点表达式的组成：
//     * execution(@注解 访问权限 返回值的类型 包名.函数名(参数))
//     */
//    @Around("onPermissionPointcut() && @annotation(permission)")
//    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint, premission: Premission) {
//        "点击事件".d()
//        val curActivity = App.mApp?.getCurActivity() ?: return
//        if (ContextCompat.checkSelfPermission(
//                curActivity,
//                premission.value[0]
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                curActivity,
//                premission.value,
//                1
//            )
//        } else {
//            joinPoint.proceed()
//        }
//    }
//}