package com.wust.springcloud.common.entity.sys.importexport;

import com.wust.springcloud.common.dto.PageDto;

/**
 * Created by WST on 2019/5/20.
 */
public class SysImportExportSearch extends SysImportExport{

    private static final long serialVersionUID = -8936939623931148493L;
    private PageDto pageDto;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
