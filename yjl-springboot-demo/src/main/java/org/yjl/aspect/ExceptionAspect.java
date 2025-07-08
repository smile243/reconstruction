package org.yjl.aspect;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yjl.utils.JsonUtils;

import java.util.StringJoiner;

/**
 * 异常入参拦截
 */
@Slf4j
@Aspect
@Component
public class ExceptionAspect {

    @Pointcut("execution(* org.yjl.controller..*(..))")
    public void declareJoinPointerExpression() {}

    @AfterThrowing(value = "declareJoinPointerExpression()",throwing = "e")
    public void aroundMethod(JoinPoint joinPoint, Exception e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String params = argsArrayToString(joinPoint.getArgs());
        String logMsg = StrUtil.format("===异常报错,访问URL:{},错误信息:{},入参:{}", request.getRequestURI(),e.getMessage(),params);
        log.error(logMsg);
    }

    private String argsArrayToString(Object[] paramsArray) {
        StringJoiner params = new StringJoiner(" ");
        if (ArrayUtil.isEmpty(paramsArray)) {
            return params.toString();
        }
        for (Object o : paramsArray) {
            if (ObjectUtil.isNotNull(o)) {
                String str = JsonUtils.toJsonString(o);
                params.add(str);
            }
        }
        return params.toString();
    }
}
