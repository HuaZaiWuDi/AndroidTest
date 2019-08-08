package com.wesmarclothing.jniproject.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Package com.wesmarclothing.jniproject.aop
 * @FileName Premission
 * @Date 2019/7/18 10:22
 * @Author JACK
 * @Describe TODO
 * @Project JNIProject
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Premission {
    String[] value() default "";
}
