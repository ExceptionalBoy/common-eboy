package com.eboy.common.interceptor;

import com.eboy.common.exception.BusinessException;
import com.eboy.common.util.LogUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Aspect
@Order(5)
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(LogUtil.ACCESS_LOG_NAME);
    private Logger LOG = LoggerFactory.getLogger(WebLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static final String NOT_DATA = "-";

    @Value("${spring.profiles.active}")
    private String version;

    @Value("${spring.application.name}")
    private String serverName;

    @Pointcut("execution(public * com.eboy..controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String request_id = request.getHeader(LogUtil.REQUEST_ID_HEADER_KEY);
        if (StringUtils.isEmpty(request_id)) {
            request_id = UUID.randomUUID().toString();
        }
        CommonRequest.requestId.set(request_id);
        // 请求日志
        StringBuilder sb = new StringBuilder();
        sb.append(checkStr(request.getMethod())).append(" ")
                .append(checkStr(request.getRequestURL().toString())).append(" ")
                .append(checkStr(request.getHeader(LogUtil.REQUEST_APP_NAME_HEADER_KEY))).append(" ")
                .append(checkStr(request_id)).append(" ")
                .append(checkStr(request.getRemoteAddr())).append(" ")
                .append(checkStr(String.valueOf(request.getRemotePort()))).append(" ")
                .append(checkStr(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName())).append(" ")
                .append(checkStr(String.valueOf(Arrays.toString(joinPoint.getArgs())))).append(" ")
        ;
        LOG.info("request info :" +sb.toString());
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        //处理回传信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        response.setHeader(LogUtil.RESPONSE_HOST_HEADER_KEY, request.getLocalAddr() + ":" + request.getLocalPort());
        logger.info(getLogStr(ret == null ? null : ret.toString(), null));
        LOG.info("response body :" + (ret == null ? null : ret.toString()));
    }


    @AfterThrowing(throwing = "ex", pointcut = "webLog()")
    public void doAfterThrowing(Throwable ex) throws Throwable {
        if (ex instanceof BusinessException) {
//            logger.info(getLogStr(null, ex.toString(), HttpStatus.PRECONDITION_FAILED.value()));
            logger.info(getLogStr(null, ex.toString(), HttpStatus.OK.value()));
        } else {
            logger.info(getLogStr(null, ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }


    private String getLogStr(String responseBody, String errorMessage) {
        return getLogStr(responseBody, errorMessage, HttpStatus.OK.value());
    }

    private String getLogStr(String responseBody, String errorMessage, int respStatus) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        String now = sdf.format(new Date());
        String request_id = CommonRequest.requestId.get();
        response.setStatus(respStatus);
        //处理错误信息
        if (!StringUtils.isEmpty(errorMessage) && errorMessage.length() > 100) {
            errorMessage = errorMessage.substring(0, 99);
        }
        // 处理完请求，返回内容
        StringBuilder sb = new StringBuilder();
        sb.append(checkStr(serverName)).append(" ")
                .append(checkStr(now)).append(" ")
                .append(checkStr(request.getRemoteHost())).append(" ")
                .append(checkStr(request.getHeader(LogUtil.X_FORWARDED_FOR_HEADER_KEY))).append(" ")
                .append(checkStr(request.getMethod() + " " + request.getRequestURL().toString())).append(" ")
                .append(checkStr(String.valueOf(response.getStatus()))).append(" ")
                .append(checkStr(String.valueOf(System.currentTimeMillis() - startTime.get()))).append(" ")
                .append(checkStr(String.valueOf(StringUtils.isEmpty(responseBody) ? 0 : responseBody.length()))).append(" ")
                .append(checkStr(request.getHeader(LogUtil.REQUEST_APP_NAME_HEADER_KEY))).append(" ")
                .append(checkStr(request_id)).append(" ")
                .append(checkStr(request.getLocalAddr() + ":" + request.getLocalPort())).append(" ")
                .append(checkStr(errorMessage))
        ;
        return sb.toString();
    }

    public String checkStr(String str) {
        return StringUtils.isEmpty(str) ? "\"" + NOT_DATA + "\"" : "\"" + str.replace("\"", "'") + "\"";
    }

}

