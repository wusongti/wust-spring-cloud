package com.wust.springcloud.common.entity.sys.operationlog;

/**
 * Created by WST on 2019/5/28.
 */
public class SysOperationLogList extends SysOperationLog{
    private static final long serialVersionUID = -7667164045993450034L;

    private String userRealName;

    private String roleName;

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
