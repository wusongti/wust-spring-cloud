package com.wust.springcloud.common.dao;

import com.wust.springcloud.common.entity.BaseEntity;
import org.springframework.dao.DataAccessException;
import java.util.List;

public interface BaseMapper<T extends BaseEntity> {
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
     * 根据key获取单个数据
     * @param key key可以是id，也可是code，表里能区别数据的唯一键
     * @return
     * @throws DataAccessException
     */
    T findSingle(String key) throws DataAccessException;

    /**
     * 单个插入
     * @param entity
     * @return
     * @throws DataAccessException
     */
    int insert(T entity) throws DataAccessException;

    /**
     * 批量插入
     * @param entities
     * @return
     * @throws DataAccessException
     */
    int batchInsert(List<T> entities) throws DataAccessException;

    /**
     * 单个更新
     * @param entity
     * @return
     * @throws DataAccessException
     */
    int update(T entity) throws DataAccessException;

    /**
     * 批量更新
     * @param entities
     * @return
     * @throws DataAccessException
     */
    int batchUpdate(List<T> entities) throws DataAccessException;

    /**
     * 单个删除
     * @param key
     * @return
     * @throws DataAccessException
     */
    int delete(String key) throws DataAccessException;

    /**
     * 批量删除
     * @param keys
     * @return
     * @throws DataAccessException
     */
    int batchDelete(List<String> keys) throws DataAccessException;
}
