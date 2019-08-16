package com.wust.springcloud.common.entity.sys.operationlog;


import com.wust.springcloud.common.entity.BaseEntity;


/**
 * Created by WST on 2019/5/28.
 */
public class SysOperationLog extends BaseEntity {
    private static final long serialVersionUID = 8700623348930994787L;

    private String moduleName;
    private String businessName;
    private String operationRole;
    private String operationData;
    private String operationType;
    private String operationIp;
    private String source;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getOperationRole() {
        return operationRole;
    }

    public void setOperationRole(String operationRole) {
        this.operationRole = operationRole;
    }

    public String getOperationData() {
        return operationData;
    }

    public void setOperationData(String operationData) {
        this.operationData = operationData;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationIp() {
        return operationIp;
    }

    public void setOperationIp(String operationIp) {
        this.operationIp = operationIp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSysOperationLog{" +
                "moduleName='" + moduleName + '\'' +
                ", businessName='" + businessName + '\'' +
                ", operationRole='" + operationRole + '\'' +
                ", operationData='" + operationData + '\'' +
                ", operationType='" + operationType + '\'' +
                ", operationIp='" + operationIp + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
