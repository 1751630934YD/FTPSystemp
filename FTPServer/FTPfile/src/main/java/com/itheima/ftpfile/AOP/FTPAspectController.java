package com.itheima.ftpfile.AOP;

import com.itheima.ftpfile.ftpclass.FTPuser;
import com.itheima.ftpfile.ftpclass.FtpLoginConverter;
import com.itheima.ftpfile.ftpclass.ftpLogin;
import com.itheima.ftpfile.mapper.Loggermapper;
import com.itheima.ftpfile.mapper.usermapper;
import org.apache.commons.net.ftp.FTP;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class FTPAspectController {

    @Autowired
    private  usermapper user;

    @Autowired
    private Loggermapper logger;


    @Around("execution(* com.itheima.ftpfile.controller.FTPfileController.*(..))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        //验证登录 登录失败返回0
//        ftpLogin login=new ftpLogin();
//        Object[] args = joinPoint.getArgs(); // 获取目标方法的参数数组
//        if (args.length > 0 && args[0] instanceof ftpLogin)
//        {
//             login=(ftpLogin) args[0];
//            if (login.getPassword()==null||login.getUsername()==null)
//            {
//                return 0;
//            }
//        }
//        try {
//            FTPuser FTPuser= user.selectuser(login.getUsername(),login.getPassword());
//            if (FTPuser==null)
//            {
//                return 0;
//            }
            System.out.println("Before method fun: " + joinPoint.getArgs());
            // 方法执行
            Object result = joinPoint.proceed();
            // 执行后的逻辑
//            System.out.println("After method execution: " + joinPoint.getSignature().getName());
            // 返回方法执行结果
            return result;
//        } catch (RuntimeException e) {
//            System.out.println(e);
//            return e;
//        }finally {
//            logger.AddLogger(login.getUsername(),joinPoint.getSignature().getName());
//        }

    }
}
