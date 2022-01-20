package com.flyFish.flyFishCommon.annotation;

import java.lang.annotation.*;

/**
 * @Author: yumingjun
 * @Date: 2022/1/20 9:43
 * @Version: 1.0.0
 * @Description: 自定义注解输出方法的执行时间，和出入参（spring AOP）
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodBaseLog {
}
