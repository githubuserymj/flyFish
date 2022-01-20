package com.flyFish.flyFishCommon.annotation;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

/**
 * @Author: yumingjun
 * @Date: 2022/1/20 9:49
 * @Version: 1.0.0
 * @Description:
 */
@Aspect
@Slf4j
@Component
public class MethodBaseLogAspect {
    /**
     * 定义切点为@MethodBaseLog注解的类方法
     */
    @Pointcut("@annotation(com.hikvision.pbg.sc.modules.annotation.MethodBaseLog)")
    public void pointCut(){}

    /**
     * 使用增强环绕通知处理，也可使用基础的@Before @After,@AfterReturning
     * @param proceedingJoinPoint
     * @param <T>
     * @return
     */
    @Around(value = "pointCut()")
    public <T> T around(ProceedingJoinPoint proceedingJoinPoint){
        try {
            //前置通知
            long startTime = System.currentTimeMillis();

            Signature signature = proceedingJoinPoint.getSignature();
            /**
             * 获取参数值
             */
            Object[] args = proceedingJoinPoint.getArgs();
            /**
             * 方法所在类路径
             */
            String methodPath = signature.getDeclaringTypeName();
            /**
             * 参数名
             */
            String[] parameterNames = ((CodeSignature) proceedingJoinPoint.getSignature()).getParameterNames();
            /**
             * 方法名
             */
            String methodName = signature.getName();
            String methodFullPathName = methodPath + "." + methodName;
            Object result = proceedingJoinPoint.proceed();
            //返回通知
            long endTime = System.currentTimeMillis();
            long timeConsumer = endTime - startTime;
            StringBuffer methodBaseLog = new StringBuffer();
            methodBaseLog.append(String.format("\n\n方法：%s", methodFullPathName));
            for (int i = 0; i < parameterNames.length; i++) {
                methodBaseLog.append(String.format("\n参数%s:%s:%s", i, parameterNames[i], args[i]));
            }
            methodBaseLog.append(String.format("\n方法返回：%s\n方法耗时：%s\n", JSONObject.toJSONString(result), this.transTime(timeConsumer)));
            log.info(methodBaseLog.toString());
        } catch (Throwable e) {
            //异常通知
//            e.printStackTrace();
            throw e;
        } finally {
            //最终通知
            return null;
        }
    }

    private String transTime(long timeMillis){
        long second = 0;
        if (timeMillis >= 1000){
            second = timeMillis / 1000;
            timeMillis  = timeMillis % 1000;
        }
        String timeStr = second + "秒" + timeMillis + "毫秒";
        return timeStr;
    }
}
