package cn.bestsort.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.bestsort.model.vo.ResponseInfo;
import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.exception.LicException;
import cn.bestsort.model.vo.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 全局异常处理器
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 16:49
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * validator result handler
     *
     * @param e result
     * @return response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<ObjectError>   errorList = e.getBindingResult().getAllErrors();
        Map<String, String> errors    = new HashMap<>(errorList.size());
        errorList.forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));
        log.debug("bad request: {}", errors);

        return ResponseEntity.badRequest().body(
            ErrorResponse.builder()
                .errors(errors)
                .code(ExceptionConstant.VERIFICATION_FAILED.getCode())
                .message(JSON.toJSONString(errors))
                .build()
        );
    }

    @ExceptionHandler(LicException.class)
    public ResponseEntity<ErrorResponse> handleCustomExceptions(LicException e) {
        log.debug("exception type: {}, http status: {}, message: {}", e.getClass(), e.getCode(), e.getMsg());
        return ResponseEntity.status(e.getCode())
            .body(ErrorResponse.builder()
                .errors(e.getMsg())
                .code(e.getCode())
                .build()
            );
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseInfo badRequestException(IllegalArgumentException exception) {
        return ResponseInfo.of(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseInfo badRequestException(AccessDeniedException exception) {
        return ResponseInfo.of(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class,
        UnsatisfiedServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseInfo badRequestException(Exception exception) {
        return ResponseInfo.of(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseInfo exception(Throwable throwable) {
        return ResponseInfo.of(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage());
    }
}
