package com.wust.springcloud.admin.server.dao;


import com.wust.springcloud.common.entity.sys.department.SysDepartment;
import com.wust.springcloud.common.entity.sys.department.SysDepartmentList;
import com.wust.springcloud.common.entity.sys.department.SysDepartmentSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * Created by WST on 2019/6/3.
 */
public interface SysDepartmentMapper {
    List<SysDepartmentList> listPage(SysDepartmentSearch search) throws DataAccessException;

    List<SysDepartmentList> findByCondition(SysDepartmentSearch search) throws DataAccessException;

    int batchInsert(List<SysDepartment> entities) throws DataAccessException;

    int batchUpdate(List<SysDepartment> entities) throws DataAccessException;

    int batchDelete(List<String> keys) throws DataAccessException;
}
