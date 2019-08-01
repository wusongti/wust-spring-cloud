package com.wust.springcloud.admin.server.core.dao;


import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.department.SysDepartment;
import com.wust.springcloud.common.entity.sys.department.SysDepartmentList;
import com.wust.springcloud.common.entity.sys.department.SysDepartmentSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * Created by WST on 2019/6/3.
 */
public interface SysDepartmentMapper  extends IBaseMapper<SysDepartment> {
    List<SysDepartmentList> listPage(SysDepartmentSearch search) throws DataAccessException;

    List<SysDepartmentList> findByCondition(SysDepartmentSearch search) throws DataAccessException;
}
