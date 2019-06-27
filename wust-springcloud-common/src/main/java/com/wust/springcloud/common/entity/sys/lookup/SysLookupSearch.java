package com.wust.springcloud.common.entity.sys.lookup;

import com.wust.springcloud.common.dto.PageDto;

/**
 * Created by WST on 2019/4/29.
 */
public class SysLookupSearch extends SysLookup{
    private static final long serialVersionUID = -2630562136316172217L;

    private PageDto pageDto;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
