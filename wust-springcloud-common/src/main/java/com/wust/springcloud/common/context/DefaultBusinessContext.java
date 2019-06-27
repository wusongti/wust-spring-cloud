package com.wust.springcloud.common.context;

/**
 * Created by WST on 2019/5/6.
 */
public class DefaultBusinessContext extends BaseBusinessContext{

    private static final long serialVersionUID = -3524432430765687155L;
    private DefaultBusinessContext(){}

    private static final ThreadLocal<DefaultBusinessContext> LOCAL = new ThreadLocal<DefaultBusinessContext>() {
        protected DefaultBusinessContext initialValue() {
            return new DefaultBusinessContext();
        }
    };

    public static DefaultBusinessContext getContext() {
        return LOCAL.get();
    }



    private String userId;
    private String loginName;
    private String realName;
    private String userType;
    private String xAuthToken;
    private String ip;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getxAuthToken() {
        return xAuthToken;
    }

    public void setxAuthToken(String xAuthToken) {
        this.xAuthToken = xAuthToken;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return super.toString() + "\nDefaultBusinessContext{" +
                "userId='" + userId + '\'' +
                ", loginName='" + loginName + '\'' +
                ", realName='" + realName + '\'' +
                ", userType='" + userType + '\'' +
                ", xAuthToken='" + xAuthToken + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
