package com.wust.springcloud.common.entity.sys.operationlog;

import com.wust.springcloud.common.dto.PageDto;

import java.util.Date;

/**
 * Created by WST on 2019/5/28.
 */
public class SysOperationLogSearch extends SysOperationLog{
    private static final long serialVersionUID = -8405287637204912631L;

    private PageDto pageDto;

    private Date beginOperationDate;

    private Date endOperationDate;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }

    public Date getBeginOperationDate() {
        return beginOperationDate;
    }

    public void setBeginOperationDate(Date beginOperationDate) {
        this.beginOperationDate = beginOperationDate;
    }

    public Date getEndOperationDate() {
        return endOperationDate;
    }

    public void setEndOperationDate(Date endOperationDate) {
        this.endOperationDate = endOperationDate;
    }
}
