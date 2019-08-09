package com.wust.springcloud.admin.server.core.service.impl;


import com.wust.springcloud.admin.server.core.dao.SysOrganizationMapper;
import com.wust.springcloud.admin.server.core.dao.SysUserMapper;
import com.wust.springcloud.admin.server.core.dao.SysUserOrganizationMapper;
import com.wust.springcloud.admin.server.core.service.SysUserOrganizationService;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.organization.SysOrganization;
import com.wust.springcloud.common.entity.sys.user.SysUser;
import com.wust.springcloud.common.entity.sys.userorganization.SysUserOrganization;
import com.wust.springcloud.common.enums.DataDictionaryEnum;
import com.wust.springcloud.common.service.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WST on 2019/4/18.
 */
@Service("sysUserOrganizationServiceImpl")
public class SysUserOrganizationServiceImpl extends BaseServiceImpl implements SysUserOrganizationService {
    @Autowired
    private SysUserOrganizationMapper sysUserOrganizationMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysUserOrganizationMapper;
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void init() {
        sysUserOrganizationMapper.deleteAll();

        SysUser sysUser = new SysUser();
        List<SysUser> sysUserList = sysUserMapper.select(sysUser);
        if(CollectionUtils.isNotEmpty(sysUserList)){
            for (SysUser user : sysUserList) {
                String type = user.getType();
                Long id = user.getId();

                SysOrganization sysOrganizationSearch = new SysOrganization();
                sysOrganizationSearch.setRelationId(id);
                sysOrganizationSearch.setType(DataDictionaryEnum.ORGANIZATION_TYPE_USER.getStringValue());
                List<SysOrganization> sysOrganizations =  sysOrganizationMapper.select(sysOrganizationSearch);
                if(CollectionUtils.isNotEmpty(sysOrganizations)) {
                    if (DataDictionaryEnum.USER_TYPE_PLATFORM_ADMIN.getStringValue().equals(type)) { // 平台管理员
                    } else if (DataDictionaryEnum.USER_TYPE_PLATFORM_USER.getStringValue().equals(type)) { // 平台普通用户
                    } else if (DataDictionaryEnum.USER_TYPE_BUSINESS_ADMIN.getStringValue().equals(type)) { // 运营管理员
                        List<SysUserOrganization> sysUserOrganizations = new ArrayList<>(10);
                        for (SysOrganization sysOrganization : sysOrganizations) {
                            SysUserOrganization sysUserOrganization = new SysUserOrganization();
                            sysUserOrganization.setUserId(id);
                            sysUserOrganizations.add(sysUserOrganization);

                            lookupByAgentUser(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
                        }
                        sysUserOrganizationMapper.insertList(sysUserOrganizations);
                    } else if (DataDictionaryEnum.USER_TYPE_PROJECT_USER.getStringValue().equals(type)) { // 业务操作员
                    }
                }
            }
        }
    }

    /**
     *  运营方账号有三种：代理商的账号、总公司的账号、分公司的账号。分三种情况搜寻：
     *  1.代理商账号搜寻方式：先向上递归搜寻到代理商，再向下递归搜寻到分公司；
     *  2.总公司账号搜寻方式：先向上递归搜寻到代理商，再向下递归搜寻到分公司；
     *  3.分公司账号搜寻方式：直接向上递归搜寻到代理商，结束。
     * @param organizationId
     * @param sysUserOrganization
     */
    private void lookupByAgentUser(Long organizationId,SysUserOrganization sysUserOrganization,List<SysUserOrganization> sysUserOrganizations){
        SysOrganization sysOrganizationSearch = new SysOrganization();
        sysOrganizationSearch.setId(organizationId);
        SysOrganization sysOrganization = sysOrganizationMapper.selectOne(sysOrganizationSearch);
        if(sysOrganization != null){
            if(DataDictionaryEnum.ORGANIZATION_TYPE_ROLE.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setRoleId(sysOrganization.getRelationId());
                lookupByAgentUser(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
            }else if(DataDictionaryEnum.ORGANIZATION_TYPE_DEPARTMENT.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setDepartmentId(sysOrganization.getRelationId());
                lookupByAgentUser(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
            }else if(DataDictionaryEnum.ORGANIZATION_TYPE_BRANCH_COMPANY.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setBranchCompanyId(sysOrganization.getRelationId());
                lookupByAgentUser(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
            }else if(DataDictionaryEnum.ORGANIZATION_TYPE_PARENT_COMPANY.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setParentCompanyId(sysOrganization.getRelationId());
                lookupByAgentUser(sysOrganization.getPid(),sysUserOrganization,sysUserOrganizations);
            }else if(DataDictionaryEnum.ORGANIZATION_TYPE_AGENT.getStringValue().equals(sysOrganization.getType())){
                sysUserOrganization.setAgentId(sysOrganization.getRelationId());

                // 向上递归到次结束，不再调用lookupByAgentUser。继续向下递归找到所有总公司
                lookupParentCompany(sysOrganization.getId(),sysUserOrganizations);

                // 继续向下找到所有分公司
                lookupBranchCompany(sysOrganization.getId(),sysUserOrganizations);
            }
        }
    }

    /**
     * 从上往下找总公司
     * @param parentOrganizationId
     * @param sysUserOrganizations
     */
    private void lookupParentCompany(Long parentOrganizationId,List<SysUserOrganization> sysUserOrganizations){
        SysOrganization sysOrganizationSearch = new SysOrganization();
        sysOrganizationSearch.setPid(parentOrganizationId);
        List<SysOrganization> sysOrganizations = sysOrganizationMapper.select(sysOrganizationSearch);
        if(CollectionUtils.isNotEmpty(sysOrganizations)){
            int count = 0; // 只有一个总公司吗？
            for (SysOrganization sysOrganization : sysOrganizations) {
                if (DataDictionaryEnum.ORGANIZATION_TYPE_PARENT_COMPANY.getStringValue().equals(sysOrganization.getType())) { // 总公司
                    count ++;

                    for (SysUserOrganization userOrganization : sysUserOrganizations) {
                        userOrganization.setParentCompanyId(sysOrganization.getRelationId());
                    }

                    if(count > 1){
                        SysUserOrganization sysUserOrganization = new SysUserOrganization();
                        BeanUtils.copyProperties(sysUserOrganizations.get(0),sysUserOrganization);
                        sysUserOrganization.setParentCompanyId(sysOrganization.getRelationId());
                        sysUserOrganizations.add(sysUserOrganization);
                    }
                }
            }

        }
    }


    /**
     * 从上往下找分公司
     * @param parentOrganizationId
     * @param sysUserOrganizations
     */
    private void lookupBranchCompany(Long parentOrganizationId,List<SysUserOrganization> sysUserOrganizations){
        SysOrganization sysOrganizationSearch = new SysOrganization();
        sysOrganizationSearch.setPid(parentOrganizationId);
        List<SysOrganization> sysOrganizations = sysOrganizationMapper.select(sysOrganizationSearch);
        if(CollectionUtils.isNotEmpty(sysOrganizations)){
            int count = 0; // 只有一个分公司吗？
            for (SysOrganization sysOrganization : sysOrganizations) {
                if (DataDictionaryEnum.ORGANIZATION_TYPE_BRANCH_COMPANY.getStringValue().equals(sysOrganization.getType())) { // 总公司
                    count ++;

                    for (SysUserOrganization userOrganization : sysUserOrganizations) {
                        userOrganization.setBranchCompanyId(sysOrganization.getRelationId());
                    }

                    if(count > 1){
                        SysUserOrganization sysUserOrganization = new SysUserOrganization();
                        BeanUtils.copyProperties(sysUserOrganizations.get(0),sysUserOrganization);
                        sysUserOrganization.setBranchCompanyId(sysOrganization.getRelationId());
                        sysUserOrganizations.add(sysUserOrganization);
                    }
                }else{
                    lookupBranchCompany(sysOrganization.getId(),sysUserOrganizations);
                }
            }
        }
    }
}
