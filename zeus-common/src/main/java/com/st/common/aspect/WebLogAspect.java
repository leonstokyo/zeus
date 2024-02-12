package com.st.common.aspect;

import com.st.common.model.WebLog;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leon
 * @date 2024/2/12 11:29
 */
@Component
@Aspect
@Order(1)
public class WebLogAspect {

    /**
     * 日志记录
     * 环绕通知
     */

    /**
     * 定义一个切点
     */
    @Pointcut("execution(* com.st.controller.*.*(..))") // controller包里所有类，类里面所有方法
    public void webLog(){}


    @Around("webLog()")
    public Object recordWebLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        WebLog webLog = new WebLog();
        long start = System.currentTimeMillis();

        result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        long end = System.currentTimeMillis();



        webLog.setSpendTime((int)(start - end) / 1000);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String url = request.getRequestURL().toString();
        webLog.setUri(request.getRequestURI());
        webLog.setUrl(url);
        //TODO 这里没做
        webLog.setBasePath(url); // http://ip:port/
        webLog.setUsername(authentication == null ? "anonymous" : authentication.getPrincipal().toString());
        webLog.setIp(request.getRemoteAddr()); //
        // 获取方法注解
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String targetClassName = proceedingJoinPoint.getTarget().getClass().getName();
        Method method = signature.getMethod();
        // 使用Swagger工具，方法上面有@ApiOpreation注解
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        webLog.setDescription(annotation == null ? "no desc" : annotation.value());
        webLog.setMethod(targetClassName + "." + method.getName());
        webLog.setParameter(getMethodParameter(method, proceedingJoinPoint.getArgs())); //{"key": "value" :}
        webLog.setResult(result);

        return result;
    }

    private Object getMethodParameter(Method method, Object[] args) {
        Map<String, Object> methodParameterWithValues = new HashMap<>();

        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
        if (parameterNames != null && parameterNames.length > 0) {
            for (int i = 0; i < parameterNames.length; i++) {
                methodParameterWithValues.put(parameterNames[i], args[i]);
            }
        }

        return methodParameterWithValues;
    }
}
