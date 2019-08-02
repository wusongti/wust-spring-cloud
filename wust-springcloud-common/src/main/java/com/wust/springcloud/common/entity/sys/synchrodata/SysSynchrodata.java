package com.wust.springcloud.common.entity.sys.synchrodata;

import com.wust.springcloud.common.entity.BaseEntity;
import java.util.Date;

/**
 * @author ：wust
 * @date ：Created in 2019/7/19 16:32
 * @description：
 * @version:
 */
public class SysSynchrodata extends BaseEntity {
    private static final long serialVersionUID = -6711790922198557776L;

    private String code;
    private String tableName;
    private String tableFieldName;
    private String tableFieldValue;
    private String companyIdSource;
    private String companyIdTarget;
    private String mqMode;
    private String exchange;
    private String queue;
    private String routing;
    private String operation;
    private Integer priorityLevel;
    private String state;
    private String description;
    private String errorMessage;
    private Date synchroTime;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableFieldName() {
        return tableFieldName;
    }

    public void setTableFieldName(String tableFieldName) {
        this.tableFieldName = tableFieldName;
    }

    public String getTableFieldValue() {
        return tableFieldValue;
    }

    public void setTableFieldValue(String tableFieldValue) {
        this.tableFieldValue = tableFieldValue;
    }

    public String getCompanyIdSource() {
        return companyIdSource;
    }

    public void setCompanyIdSource(String companyIdSource) {
        this.companyIdSource = companyIdSource;
    }

    public String getCompanyIdTarget() {
        return companyIdTarget;
    }

    public void setCompanyIdTarget(String companyIdTarget) {
        this.companyIdTarget = companyIdTarget;
    }

    public String getMqMode() {
        return mqMode;
    }

    public void setMqMode(String mqMode) {
        this.mqMode = mqMode;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(Integer priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getSynchroTime() {
        return synchroTime;
    }

    public void setSynchroTime(Date synchroTime) {
        this.synchroTime = synchroTime;
    }
}
