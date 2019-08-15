package com.wust.springcloud.admin.server.config;

import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.exception.BaseWebExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class WebExceptionHandler extends BaseWebExceptionHandler {
    @Override
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseDto handle(Throwable e) {
        return super.handle(e);
    }
}
