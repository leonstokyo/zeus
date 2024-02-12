package com.st.common.aspect;

import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.st.common.model.ResponseResult;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author leon
 * @date 2024/2/12 11:54
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 内部api调用异常
     */
    @ExceptionHandler(value = ApiException.class)
    public ResponseResult<?> handlerApiException(ApiException apiException) {
        IErrorCode errorCode = apiException.getErrorCode();
        if (errorCode != null) {
            return ResponseResult.fail();
        }

        return ResponseResult.fail(apiException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return handlerBindingResult(bindingResult, exception);
    }

    @ExceptionHandler(BindException.class)
    public ResponseResult<?> handlerBindException(BindException bindException) {
        BindingResult bindingResult = bindException.getBindingResult();
        return handlerBindingResult(bindingResult, bindException);
    }

    private ResponseResult<?> handlerBindingResult(BindingResult bindingResult, Exception e) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                return ResponseResult.fail(fieldError.getField() + fieldError.getDefaultMessage());
            }
        }
        return ResponseResult.fail(e.getMessage());
    }
}
