package com.wust.springcloud.common.entity.sys.project;


import com.wust.springcloud.common.dto.PageDto;

/**
 * @author ：wust
 * @date ：Created in 2019/7/30 11:41
 * @description：
 * @version:
 */
public class SysProjectSearch extends SysProject{
    private static final long serialVersionUID = 6279300458568239176L;


    private PageDto pageDto;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
