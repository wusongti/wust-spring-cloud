package com.wust.springcloud.common.exception;

import com.wust.springcloud.common.dto.ResponseDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseWebExceptionHandler {
    private static Log logger = LogFactory.getLog(BaseWebExceptionHandler.class);

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
