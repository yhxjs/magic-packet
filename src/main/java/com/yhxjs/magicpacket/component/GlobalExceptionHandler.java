package com.yhxjs.magicpacket.component;

import com.yhxjs.magicpacket.controller.Code;
import com.yhxjs.magicpacket.controller.Result;
import com.yhxjs.magicpacket.exception.AccessIllegalException;
import com.yhxjs.magicpacket.exception.ServiceException;
import com.yhxjs.magicpacket.utils.LimitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.net.SocketTimeoutException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class,
            ConstraintViolationException.class, MethodArgumentNotValidException.class,
            HttpRequestMethodNotSupportedException.class, HttpMessageNotReadableException.class})
    public Result handler(HttpServletRequest req) {
        return Result.error(Code.ACCESS_ILLEGAL, LimitUtil.getIllegalIPString(req));
    }

    @ExceptionHandler(SocketTimeoutException.class)
    public Result socketTimeoutExceptionHandler() {
        return Result.error(Code.SYSTEM_ERROR, "连接超时，请稍后再试");
    }

    @ExceptionHandler(AccessIllegalException.class)
    public Result accessIllegalExceptionHandler(AccessIllegalException e) {
        return Result.error(e.getCode(), LimitUtil.getIllegalIPString());
    }

    @ExceptionHandler(ServiceException.class)
    public Result serviceExceptionHandler(ServiceException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error("服务器错误", e);
        return Result.error(Code.SYSTEM_ERROR, "服务器错误，请稍后再试");
    }
}
