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
     * 根据key获取单个数据
     * @param key key可以是id，也可是code，表里能区别数据的唯一键
     * @return
     */
    T findSingle(String key);

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
    int update(T entity);

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
    int delete(String key);

    /**
     * 批量删除
     * @param keys
     * @return
     */
    int batchDelete(List<String> keys);
}
