package com.wust.springcloud.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <T>
 * @param <E>
 */
public class BaseDto<T,E> implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<T> lstDto;
	private List<E> lstDetailDto;
    private PageDto page;
    private MessageMap messageMap;
    private T t;
    private E e;
    private String token;
    private Object obj;


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

    public MessageMap getMessageMap() {
        return messageMap;
    }

    public void setMessageMap(MessageMap messageMap) {
        this.messageMap = messageMap;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "BaseDto{" +
                "lstDto=" + lstDto +
                ", lstDetailDto=" + lstDetailDto +
                ", page=" + page +
                ", messageMap=" + messageMap +
                ", t=" + t +
                ", e=" + e +
                ", token='" + token + '\'' +
                ", obj=" + obj +
                '}';
    }
}
