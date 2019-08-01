package com.wust.springcloud.common.dao;

import com.wust.springcloud.common.entity.BaseEntity;
import org.springframework.dao.DataAccessException;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import java.util.List;

public interface IBaseMapper<T extends BaseEntity>  extends Mapper<T>, MySqlMapper<T> {
    /**
     * 分页查询
     * @param search
     * @return
     * @throws DataAccessException
     */
    List<T> listPage(T search) throws DataAccessException;


    /**
     * 根据指定的条件查询
     * @param search
     * @return
     * @throws DataAccessException
     */
    List<T> findByCondition(T search) throws DataAccessException;


    /**
     * 批量更新
     * @param entities
     * @return
     * @throws DataAccessException
     */
    int batchUpdate(List<T> entities) throws DataAccessException;


    /**
     * 批量删除
     * @param keys
     * @return
     * @throws DataAccessException
     */
    int batchDelete(List<String> keys) throws DataAccessException;
}
