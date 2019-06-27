package com.wust.springcloud.common.entity.sys.organization;

import com.wust.springcloud.common.dto.PageDto;

/**
 * Created by WST on 2019/6/3.
 */
public class SysOrganizationSearch extends SysOrganization{
    private static final long serialVersionUID = -6952759366447718649L;

    private PageDto pageDto;

    private String name;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
