package com.eboy.common.interceptor;

import com.eboy.common.exception.BusinessException;
import com.eboy.common.enums.SystemCodeEnum;
import com.eboy.common.result.Context;
import com.eboy.common.result.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.profiles.active}")
    private String version;

    private String lineSeparator = (String) java.security.AccessController.doPrivileged(
            new sun.security.action.GetPropertyAction("line.separator"));

    @Value("${spring.application.name}")
    private String serverName;

    private final String exceptionNullMsg = "server error : exception is null";

    boolean exceptionIsNull(Throwable t) {
        return t == null ? Boolean.TRUE : Boolean.FALSE;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Context defaultExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        if (exceptionIsNull(e)) {
            logger.error(exceptionNullMsg + ", exception:{} ", e);
        }
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stackTraceElement : stackTrace) {
            stringBuilder.append(stackTraceElement.toString()).append(lineSeparator);
        }
        logger.error("========通用异常捕获！========", e);
        Header header = null;
        if (e instanceof BusinessException) {
            BusinessException businessException= (BusinessException) e;
            String code = businessException.getCode();
            String msg = businessException.getMessage();
            header = new Header(code,msg);
        }else {
            header = new Header(SystemCodeEnum.SYSTEM_ERROR.getCode(), SystemCodeEnum.SYSTEM_ERROR.getMsg());
        }
        Context context = new Context(header);
        return context;
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Context BusinessExceptionHandler(HttpServletRequest request, HttpServletResponse response, BusinessException e) {
        if (exceptionIsNull(e)) {
            logger.error(exceptionNullMsg + ", exception:{} ", e);
        }
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stackTraceElement : stackTrace) {
            stringBuilder.append(stackTraceElement.toString());
        }
        String code = e.getCode();
        String message = e.getMessage();
        logger.warn("========业务逻辑异常捕获！========", e);
        logger.warn("========业务逻辑异常捕获！========", code);
        logger.warn("========业务逻辑异常捕获！========", message);

        Header header = new Header(code,message);
        Context context = new Context(header);
        return context;
    }

    /**
     * 处理所有接口数据验证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    @ResponseBody
    public Context handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        if (exceptionIsNull(e)) {
            logger.error(exceptionNullMsg + ", exception:{} ", e);
            //return new Result(ErrorCodeEnum.SYSTEM_ERROR, exceptionNullMsg);
        }
        logger.error(e.getMessage(), e);
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stackTraceElement : stackTrace) {
            stringBuilder.append(stackTraceElement.toString());
        }
        String code = SystemCodeEnum.PARAM_ERROR.getCode();
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        Header header = new Header(code,message);
        Context context = new Context(header);
        return context;
    }
}
