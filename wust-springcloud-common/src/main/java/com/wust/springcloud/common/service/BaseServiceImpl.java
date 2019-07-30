package com.wust.springcloud.common.service;

import com.wust.springcloud.common.dao.BaseMapper;
import com.wust.springcloud.common.entity.BaseEntity;
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
    public List findByCondition(BaseEntity search){
        return getBaseMapper().findByCondition(search);
    }

    @Override
    public BaseEntity findSingle(String key){
        return getBaseMapper().findSingle(key);
    }

    @Override
    public int insert(BaseEntity entity){
        return getBaseMapper().insert(entity);
    }

    @Override
    public int batchInsert(List entities){
        return getBaseMapper().batchInsert(entities);
    }

    @Override
    public int update(BaseEntity entity){
        return getBaseMapper().update(entity);
    }

    @Override
    public int batchUpdate(List entities){
        return getBaseMapper().batchUpdate(entities);
    }

    @Override
    public int delete(String key){
        return getBaseMapper().delete(key);
    }

    @Override
    public int batchDelete(List keys){
        return getBaseMapper().batchDelete(keys);
    }

    protected abstract BaseMapper getBaseMapper();
}
