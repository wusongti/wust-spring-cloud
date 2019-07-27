package com.wust.springcloud.admin.server.core.service.impl;

import com.wust.springcloud.admin.server.core.dao.SysLookupPrivateMapper;
import com.wust.springcloud.admin.server.core.service.SysLookupPrivateService;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.entity.sys.lookup.SysLookup;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupList;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupSearch;
import com.wust.springcloud.common.util.cache.DataDictionaryUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WST on 2019/5/28.
 */
@Service("sysLookupPrivateServiceImpl")
public class SysLookupPrivateServiceImpl implements SysLookupPrivateService {
    @Autowired
    private SysLookupPrivateMapper sysLookupPrivateMapper;


    @Override
    public List<SysLookupList> listPage(SysLookupSearch search) {
        return sysLookupPrivateMapper.listPage(search);
    }

    @Override
    public List<SysLookupList> findByCondition(SysLookupSearch search) {
        return sysLookupPrivateMapper.findByCondition(search);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public int insert(SysLookup entity) {
        final List<SysLookup> creates = new ArrayList<>(100);
        creates.add(entity);
        final List<String> codes = new ArrayList<>(100);
        codes.add(entity.getCode());
        getChildrenByParentCode(entity,creates,codes);

        sysLookupPrivateMapper.batchDelete(codes);
        return sysLookupPrivateMapper.batchInsert(creates);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public int copy(SysLookup entity) {
        final List<SysLookup> creates = new ArrayList<>(100);
        creates.add(entity);
        final List<String> codes = new ArrayList<>(100);
        codes.add(entity.getCode());

        getChildrenByParentCode(entity,creates,codes);
        return sysLookupPrivateMapper.batchInsert(creates);
    }

    @Override
    public int update(SysLookup sysLookup) {
        return sysLookupPrivateMapper.update(sysLookup);
    }

    @Override
    public int delete(String id) {
        List<String> keys = new ArrayList<>(1);
        keys.add(id);
        return sysLookupPrivateMapper.batchDelete(keys);
    }

    private void getChildrenByParentCode(SysLookup parent,final List<SysLookup> resultList,List<String> codes){
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        List<SysLookupList> sysLookupLists = DataDictionaryUtil.getLookupListByParentCode(ctx.getLocale().toString(),parent.getCode());
        if(CollectionUtils.isNotEmpty(sysLookupLists)){
            for (SysLookupList sysLookupList : sysLookupLists) {
                sysLookupList.setLan(parent.getLan());
                resultList.add(sysLookupList);
                codes.add(sysLookupList.getCode());
                getChildrenByParentCode(sysLookupList,resultList,codes);
            }
        }
    }
}
