package com.wust.springcloud.admin.server.exception;

import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.exception.BusinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class WebExceptionHandler {
    private static Log logger = LogFactory.getLog(WebExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseDto handle(Throwable e){
        ResponseDto result = new ResponseDto();
        if(e instanceof BusinessException){
            logger.error("【自定义异常】:{}"+e.getMessage());
            result.setFlag(ResponseDto.INFOR_ERROR);
            result.setMessage(e.getMessage());
        }else{
            logger.error("【系统异常】:{}"+e.getMessage());
            result.setFlag(ResponseDto.INFOR_ERROR);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
