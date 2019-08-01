package com.wust.springcloud.common.service;

import com.wust.springcloud.common.entity.BaseEntity;
import java.util.List;

public interface BaseService<T extends BaseEntity> {
    /**
     * 分页查询
     * @param search
     * @return
     */
    List<T> listPage(T search);


    /**
     * 根据指定的条件查询
     * @param search
     * @return
     */
    List<T> findByCondition(T search);


    /**
     * 单个插入
     * @param entity
     * @return
     */
    int insert(T entity);

    /**
     * 批量插入
     * @param entities
     * @return
     */
    int batchInsert(List<T> entities);

    /**
     * 单个更新
     * @param entity
     * @return
     */
    int updateByPrimaryKey(T entity);

    /**
     * 批量更新
     * @param entities
     * @return
     */
    int batchUpdate(List<T> entities);

    /**
     * 单个删除
     * @param key
     * @return
     */
    int deleteByPrimaryKey(String key);

    /**
     * 批量删除
     * @param keys
     * @return
     */
    int batchDelete(List<String> keys);
}
