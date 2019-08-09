package com.wust.springcloud.common.entity.sys.userorganization;

import com.wust.springcloud.common.dto.PageDto;

/**
 * @author ：wust
 * @date ：Created in 2019/8/9 9:57
 * @description：
 * @version:
 */
public class SysUserOrganizationSearch extends SysUserOrganization{
    private static final long serialVersionUID = -1222205073903300868L;

    private PageDto pageDto;

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }

}
