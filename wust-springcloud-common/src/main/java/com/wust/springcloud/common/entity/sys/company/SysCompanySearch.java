package com.wust.springcloud.common.entity.sys.company;

import com.wust.springcloud.common.dto.PageDto;

/**
 * Created by WST on 2019/6/3.
 */
public class SysCompanySearch extends SysCompany{
    private static final long serialVersionUID = 1132959286170603676L;

    private PageDto pageDto;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
