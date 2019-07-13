package com.wust.springcloud.admin.server.core.controller;

import com.wust.springcloud.admin.server.core.service.SysDataPrivilegeRulesService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.BaseDto;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.dataprivilege.rules.SysDataPrivilegeRulesList;
import com.wust.springcloud.common.entity.sys.dataprivilege.rules.SysDataPrivilegeRulesSearch;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.cache.DataDictionaryUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by WST on 2019/6/11.
 */
@RequestMapping("/DataPrivilegeRulesController")
@RestController
public class DataPrivilegeRulesController {
    @Autowired
    private SysDataPrivilegeRulesService sysDataPrivilegeRulesServiceImpl;

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_DATA_PRIVILEGE,businessName="分页查询",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public @ResponseBody
    BaseDto listPage(@RequestBody SysDataPrivilegeRulesSearch search){
        BaseDto baseDto = new BaseDto();
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        List<SysDataPrivilegeRulesList> sysDataPrivilegeRulesLists =  sysDataPrivilegeRulesServiceImpl.listPage(search);
        if(CollectionUtils.isNotEmpty(sysDataPrivilegeRulesLists)){
            for (SysDataPrivilegeRulesList sysDataPrivilegeRulesList : sysDataPrivilegeRulesLists) {
                String typeNameStr = "";
                String[] types = sysDataPrivilegeRulesList.getType().split(",");
                Arrays.sort(types);
                for (String type : types) {
                    typeNameStr += "【" + DataDictionaryUtil.getLookupNameByCode(ctx.getCompanyId(),type) + "】";
                }
                sysDataPrivilegeRulesList.setTypeName(typeNameStr);
            }
        }
        baseDto.setPage(search.getPageDto());
        baseDto.setLstDto(sysDataPrivilegeRulesLists);
        baseDto.setMessageMap(mm);
        return baseDto;
    }

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_DATA_PRIVILEGE,businessName="修改",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/update/{dataPrivilegeId}/{types}",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap update(@PathVariable String dataPrivilegeId,@PathVariable String types){
        MessageMap mm = sysDataPrivilegeRulesServiceImpl.update(dataPrivilegeId,types);
        return mm;
    }
}
