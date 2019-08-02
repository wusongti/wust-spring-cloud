package com.wust.springcloud.common.entity.sys.importexport;

import com.wust.springcloud.common.entity.BaseEntity;
import java.util.Date;

/**
 * Created by WST on 2019/5/20.
 */
public class SysImportExport extends BaseEntity{
    private static final long serialVersionUID = 8972450411783576856L;

    /** 模块名称 **/
    private String moduleName;
    /** 描述 **/
    private String description;
    /** 操作类型：导入，导出 **/
    private String operationType;
    /** 当前状态：1，执行中；2，全部导入成功；3，部分导入成功；4，导入失败。 **/
    private String status;
    /** 批次号 **/
    private String batchNo;
    /** 开始时间 **/
    private Date startTime;
    /** 结束时间 **/
    private Date endTime;
    private String msg;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSysImportExport{" +
                ", moduleName='" + moduleName + '\'' +
                ", description='" + description + '\'' +
                ", operationType='" + operationType + '\'' +
                ", status='" + status + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
