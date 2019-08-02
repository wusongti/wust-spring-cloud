package com.wust.springcloud.common.entity.sys.apptoken;

import com.wust.springcloud.common.entity.BaseEntity;

import javax.persistence.Id;
import java.util.Date;

/**
 * Created by WST on 2019/4/18.
 */
public class SysAppToken extends BaseEntity{
    private static final long serialVersionUID = 949859914341793577L;

    private String appId;
    private String loginName;
    private String password;
    private Date expireTime;
    private String token;
    private String status;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
