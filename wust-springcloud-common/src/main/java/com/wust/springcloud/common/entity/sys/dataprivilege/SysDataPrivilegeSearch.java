package com.wust.springcloud.common.entity.sys.dataprivilege;

import com.wust.springcloud.common.dto.PageDto;

/**
 * Created by WST on 2019/6/10.
 */
public class SysDataPrivilegeSearch extends SysDataPrivilege {
    private static final long serialVersionUID = -1694373320117534705L;

    private PageDto pageDto;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
