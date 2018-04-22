package com.puke.lol.aop;

import com.puke.lol.base.PageResult;
import com.puke.lol.base.Result;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author zijiao
 * @version 18/4/22
 */
@Aspect
@Order(-99) // 控制多个Aspect的执行顺序，越小越先执行
@Component
public class AutoCatchExceptionHandler {

    @Around("(@within(AutoCatchException) || @annotation(AutoCatchException))" +
            "&& " +
            "(execution(public com.puke.lol.base.Result *.*(..)) || execution(public com.puke.lol.base.PageResult *.*(..)))")
    public Object around(ProceedingJoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Class returnType = methodSignature.getReturnType();
        try {
            return point.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            String errorMsg = Optional.ofNullable(e.getMessage())
                    .filter(Strings::isNotEmpty)
                    .orElse("服务端异常~");
            if (PageResult.class.isAssignableFrom(returnType)) {
                return PageResult.error(errorMsg);
            }
            if (Result.class.isAssignableFrom(returnType)) {
                return Result.error(errorMsg);
            }
            throw new RuntimeException(e);
        }
    }

}
