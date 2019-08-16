package com.wust.springcloud.common.context;

import com.wust.springcloud.common.entity.sys.user.SysUser;

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


    private SysUser user;
    private String xAuthToken;
    /** api请求参数 */
    private Map<String, Object> signMap;

    public static ThreadLocal<DefaultBusinessContext> getLOCAL() {
        return LOCAL;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
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
        return super.toString() + "\nDefaultBusinessContext{" +
                ", user=" + user +
                ", xAuthToken='" + xAuthToken + '\'' +
                ", signMap=" + signMap +
                '}';
    }
}
