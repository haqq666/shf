package com.atguigu.aspect;

import com.atguigu.util.IpUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/23 11:03
 */
@Aspect
@Component
public class ControllerAspect {
    //SLF4J的日志记录器对象
    private Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
    //配置切点
    @Pointcut("execution(* com.atguigu.controller.*.*(..))")
    public void controllerAction(){}

    @Around("controllerAction()")
    public Object recordLogs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
//        创建StringBuffer用来拼接日志内容
        StringBuffer stringBuffer = new StringBuffer("");
//        获取用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof User)){
            //说明没登录
            return proceedingJoinPoint.proceed();
        }
        //登录了
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        stringBuffer.append(username);

        //获取请求的相关数据
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = sra.getRequest();
        String url = request.getRequestURL().toString();
        stringBuffer.append("|"+url);

        //获取请求方式
        String method = request.getMethod();
        stringBuffer.append("|"+method);

        //获取ip地址
        String ipAddress = IpUtil.getIpAddress(request);
        stringBuffer.append("|"+ipAddress);
        //获取切入点相关信息
        //获取调用的方法：类的全类名.方法名
        Signature signature = proceedingJoinPoint.getSignature();
        String classMethod = signature.getDeclaringTypeName() + "." + signature.getName();
        stringBuffer.append("|"+classMethod);
        //获取方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        if (args != null && args.length > 0){
            stringBuffer.append("|");
            for (int i = 0; i < args.length; i++) {
                if (i < args.length - 1){
                    stringBuffer.append(args[i] + ",");
                }else {
                    stringBuffer.append(args[i]);
                }
            }
        }
        Long startTime = 0L;

        try {
            //获取当前的系统时间（执行之前）
            startTime = System.currentTimeMillis();
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        } finally {
            Long endTime = System.currentTimeMillis();
            Long executeTime = endTime - startTime;
            stringBuffer.append("|" + executeTime);

            logger.info("request logs:"+stringBuffer);
        }
    }




}
























