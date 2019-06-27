
package com.wust.springcloud.common.dto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MessageMap implements Serializable {
    private static final long serialVersionUID = 1L;

    public MessageMap() {
        this.setFlag(MessageMap.INFOR_SUCCESS);
        this.setMessage("完成");
    }

    /**
     * Success
     */
    public static final String INFOR_SUCCESS = "SUCCESS";
    /**
     * Error
     */
    public static final String INFOR_ERROR = "ERROR";
    /**
     * Warning
     */
    public static final String INFOR_WARNING = "WARNING";

    /**
     * 返回结果为空
     */
    public static final String EMPTY_RESULT = "Return result is empty";

    private String code;

    /**
     * 结果标记，默认是成功
     */
    private String flag;

    /**
     * 结果消息
     */
    private String message;


    /**
     * 可封装的结果对象
     */
    private Object obj;


    /**
     * 现代分布式模式下，随时更新token，以便保证请求安全
     */
    private String token;

    /**
     * 需要返回的集合，可封装在这里
     */
    private Map<String, Object> mapMessage = new HashMap<>();
    private List<?> lstSuccessMessage = new ArrayList<>();
    private List<?> lstErrorMessage = new ArrayList<>();
    private List<?> lstWarningMessage = new ArrayList<>();


    public static String getInforSuccess() {
        return INFOR_SUCCESS;
    }

    public static String getInforError() {
        return INFOR_ERROR;
    }

    public static String getInforWarning() {
        return INFOR_WARNING;
    }

    public static String getEmptyResult() {
        return EMPTY_RESULT;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, Object> getMapMessage() {
        return mapMessage;
    }

    public void setMapMessage(Map<String, Object> mapMessage) {
        this.mapMessage = mapMessage;
    }

    public List<?> getLstSuccessMessage() {
        return lstSuccessMessage;
    }

    public void setLstSuccessMessage(List<?> lstSuccessMessage) {
        this.lstSuccessMessage = lstSuccessMessage;
    }

    public List<?> getLstErrorMessage() {
        return lstErrorMessage;
    }

    public void setLstErrorMessage(List<?> lstErrorMessage) {
        this.lstErrorMessage = lstErrorMessage;
    }

    public List<?> getLstWarningMessage() {
        return lstWarningMessage;
    }

    public void setLstWarningMessage(List<?> lstWarningMessage) {
        this.lstWarningMessage = lstWarningMessage;
    }

    @Override
    public String toString() {
        return "MessageMap{" +
                "code='" + code + '\'' +
                ", flag='" + flag + '\'' +
                ", message='" + message + '\'' +
                ", obj=" + obj +
                ", token='" + token + '\'' +
                ", mapMessage=" + mapMessage +
                ", lstSuccessMessage=" + lstSuccessMessage +
                ", lstErrorMessage=" + lstErrorMessage +
                ", lstWarningMessage=" + lstWarningMessage +
                '}';
    }
}

