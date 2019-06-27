package com.wust.springcloud.common.entity.sys.operationlog;

import com.wust.springcloud.common.dto.PageDto;

/**
 * Created by WST on 2019/5/28.
 */
public class SysOperationLogSearch extends SysOperationLog{
    private static final long serialVersionUID = -8405287637204912631L;

    private PageDto pageDto;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
