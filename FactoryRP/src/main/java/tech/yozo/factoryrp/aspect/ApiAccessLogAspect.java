package tech.yozo.factoryrp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * API日志
 */
@Aspect
@Component
public class ApiAccessLogAspect {


    private static Logger logger = LoggerFactory.getLogger(ApiAccessLogAspect.class);


    /**
     * 控制器切点
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerAcpect(){

    }


    /**
     * 返回通知
     * @param joinPoint
     * @param resp 此参数值必须和方法签名里面的resp一致
     */
    @AfterReturning(pointcut="controllerAcpect()",returning = "resp")
    public void afterReturnMethod(JoinPoint joinPoint, Object resp){
        handleLog(joinPoint,resp);
    }

    /**
     * 抛出通知
     * @param joinPoint
     * @param ex 此参数值必须和方法签名里面的ex一致
     */
    @AfterThrowing(pointcut="controllerAcpect()",throwing = "ex")
    public void afterThrowingMethod(JoinPoint joinPoint,Exception ex){
        handleLog(joinPoint,ex);
    }

    /**
     * 处理日志
     * @param joinPoint
     * @param object
     */
    public void handleLog(JoinPoint joinPoint,Object object){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();


        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //logger.info("ARGS : " + JSON.toJSONString(joinPoint.getArgs()));
    }

}
