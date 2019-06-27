package com.wust.springcloud.common.entity.sys.menu;

import com.wust.springcloud.common.dto.PageDto;

/**
 * Created by WST on 2019/4/28.
 */
public class SysMenuSearch extends SysMenu{
    private static final long serialVersionUID = 6242560881745133655L;

    private PageDto pageDto;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
