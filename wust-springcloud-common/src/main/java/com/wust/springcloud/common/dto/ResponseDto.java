package com.wust.springcloud.common.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ResponseDto<T,E> implements Serializable {
    private static final long serialVersionUID = -4163766286277670113L;
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


    private String token;

    private String code;

    private String flag;

    private String message;

    private List<T> lstDto;

    private List<E> lstDetailDto;

    private PageDto page;

    private T t;

    private E e;

    private Object obj;

    private Map mapMessage;

    public ResponseDto() {
        this.setFlag(ResponseDto.INFOR_SUCCESS);
        this.setMessage("成功");
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public List<T> getLstDto() {
        return lstDto;
    }

    public void setLstDto(List<T> lstDto) {
        this.lstDto = lstDto;
    }

    public List<E> getLstDetailDto() {
        return lstDetailDto;
    }

    public void setLstDetailDto(List<E> lstDetailDto) {
        this.lstDetailDto = lstDetailDto;
    }

    public PageDto getPage() {
        return page;
    }

    public void setPage(PageDto page) {
        this.page = page;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public E getE() {
        return e;
    }

    public void setE(E e) {
        this.e = e;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Map getMapMessage() {
        return mapMessage;
    }

    public void setMapMessage(Map mapMessage) {
        this.mapMessage = mapMessage;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "token='" + token + '\'' +
                ", code='" + code + '\'' +
                ", flag='" + flag + '\'' +
                ", message='" + message + '\'' +
                ", lstDto=" + lstDto +
                ", lstDetailDto=" + lstDetailDto +
                ", page=" + page +
                ", t=" + t +
                ", e=" + e +
                '}';
    }
}
