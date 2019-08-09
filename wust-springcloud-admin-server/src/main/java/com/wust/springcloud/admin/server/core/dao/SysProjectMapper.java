package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.project.SysProject;
import com.wust.springcloud.common.entity.sys.project.SysProjectList;
import com.wust.springcloud.common.entity.sys.project.SysProjectSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;

public interface SysProjectMapper extends IBaseMapper<SysProject> {
    List<SysProjectList> listPage(SysProjectSearch search) throws DataAccessException;
}
