package com.wust.springcloud.common.context;

import java.util.Map;

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



    private Long userId;
    private String loginName;
    private String realName;
    private String userType;
    private String xAuthToken;
    /** api请求参数 */
    private Map<String, Object> signMap;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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


    public Map<String, Object> getSignMap() {
        return signMap;
    }

    public void setSignMap(Map<String, Object> signMap) {
        this.signMap = signMap;
    }

    @Override
    public String toString() {
        return "DefaultBusinessContext{" +
                "userId='" + userId + '\'' +
                ", loginName='" + loginName + '\'' +
                ", realName='" + realName + '\'' +
                ", userType='" + userType + '\'' +
                ", xAuthToken='" + xAuthToken + '\'' +
                ", signMap=" + signMap +
                '}';
    }
}
