package com.wust.springcloud.common.service;

import com.wust.springcloud.common.entity.BaseEntity;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    /**
     * 分页查询
     * @param search
     * @return
     */
    List<T> listPage(T search);

    /**
     * 获取一个记录
     * @param search
     * @return
     */
    Object selectOne(T search);

    /**
     * 根据指定的条件查询
     * @param search
     * @return
     */
    List<T> select(T search);

    /**
     * 自定义查询
     * @param example
     * @return
     */
    List<T> selectByExample(Example example);


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
    int deleteByPrimaryKey(Long key);

    /**
     * 批量删除
     * @param keys
     * @return
     */
    int batchDelete(List<Object> keys);
}
