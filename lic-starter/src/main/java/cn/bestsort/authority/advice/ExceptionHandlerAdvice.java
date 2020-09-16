package cn.bestsort.authority.advice;

import cn.bestsort.authority.dto.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 以Controller为入口的报错异常的捕获处理
 *
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    //非法参数的报错
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseInfo badRequestException(IllegalArgumentException exception) {
        return new ResponseInfo(HttpStatus.BAD_REQUEST.value() + "", exception.getMessage());
        //exception.getMessage() 异常抛出的信息
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseInfo badRequestException(AccessDeniedException exception) {
        return new ResponseInfo(HttpStatus.FORBIDDEN.value() + "", exception.getMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class,
        UnsatisfiedServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseInfo badRequestException(Exception exception) {
        return new ResponseInfo(HttpStatus.BAD_REQUEST.value() + "", exception.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseInfo exception(Throwable throwable) {
        log.error("系统异常", throwable);
        return new ResponseInfo(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", throwable.getMessage());

    }

}
