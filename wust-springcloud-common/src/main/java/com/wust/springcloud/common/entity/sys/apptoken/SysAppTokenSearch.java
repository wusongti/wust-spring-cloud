package com.wust.springcloud.common.entity.sys.apptoken;

import com.wust.springcloud.common.dto.PageDto;

/**
 * Created by WST on 2019/4/18.
 */
public class SysAppTokenSearch extends SysAppToken{
    private static final long serialVersionUID = 1341861022012893998L;
    private PageDto pageDto;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
