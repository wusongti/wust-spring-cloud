package com.wust.springcloud.common.service;

import com.wust.springcloud.common.entity.BaseEntity;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    List<T> listPage(T search);

    Object selectOne(T search);

    List<T> select(T search);

    List<T> selectByExample(Example example);

    int insert(T entity);

    int insertUseGeneratedKeys(T entity);

    int batchInsert(List<T> entities);

    int updateByPrimaryKey(T entity);

    int batchUpdate(List<T> entities);

    int delete(T entity);

    int deleteByPrimaryKey(Long key);

    int batchDelete(List<Object> keys);
}
