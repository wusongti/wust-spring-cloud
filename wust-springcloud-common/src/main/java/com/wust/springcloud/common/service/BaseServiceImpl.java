package com.wust.springcloud.common.service;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author ：wust
 * @date ：Created in 2019/7/30 14:10
 * @description：服务层默认实现，满足大部分需求，个性化需求在自己的服务层实现
 * @version:
 */
public abstract class BaseServiceImpl implements BaseService{

    @Override
    public List listPage(BaseEntity search) {
        return getBaseMapper().listPage(search);
    }

    @Override
    public Object selectOne(BaseEntity search) {
        return getBaseMapper().selectOne(search);
    }

    @Override
    public List select(BaseEntity search){
        return getBaseMapper().select(search);
    }

    @Override
    public List selectByExample(Example example) {
        return getBaseMapper().selectByExample(example);
    }

    @Override
    public int insert(BaseEntity entity){
        return getBaseMapper().insert(entity);
    }

    @Override
    public int insertUseGeneratedKeys(BaseEntity entity) {
        return getBaseMapper().insertUseGeneratedKeys(entity);
    }

    @Override
    public int updateByPrimaryKey(BaseEntity entity) {
        return getBaseMapper().updateByPrimaryKey(entity);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public int batchInsert(List entities){
        return getBaseMapper().insertList(entities);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public int batchUpdate(List entities){
        return getBaseMapper().batchUpdate(entities);
    }

    @Override
    public int deleteByPrimaryKey(Long key){
        return getBaseMapper().deleteByPrimaryKey(key);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public int batchDelete(List keys){
        return getBaseMapper().batchDelete(keys);
    }

    protected abstract IBaseMapper getBaseMapper();
}
