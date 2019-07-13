package com.wust.springcloud.admin.server.core.controller;

import com.wust.springcloud.admin.server.core.service.SysDataSourceService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.BaseDto;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSource;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSourceList;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSourceSearch;
import com.wust.springcloud.common.enums.OperationLogEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by WST on 2019/6/17.
 */
@RequestMapping("/DataSourceController")
@RestController
public class DataSourceController {
    @Autowired
    private SysDataSourceService sysDataSourceService;

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_DATA_SOURCE,businessName="分页查询",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public @ResponseBody
    BaseDto listPage(@RequestBody SysDataSourceSearch search){
        BaseDto baseDto = new BaseDto();
        MessageMap mm = new MessageMap();
        List<SysDataSourceList> sysDataSourceLists =  sysDataSourceService.listPage(search);
        baseDto.setPage(search.getPageDto());
        baseDto.setLstDto(sysDataSourceLists);
        baseDto.setMessageMap(mm);
        return baseDto;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_DATA_SOURCE,businessName="初始化数据源",operationType= OperationLogEnum.Insert)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap create(@RequestBody SysDataSource entity){
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        entity.setCreaterId(ctx.getUserId());
        entity.setCreaterName(ctx.getLoginName());
        mm = sysDataSourceService.insert(entity);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_DATA_SOURCE,businessName="修改",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap update(@RequestBody SysDataSource entity){
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        entity.setModifyId(ctx.getUserId());
        entity.setModifyName(ctx.getLoginName());
        sysDataSourceService.update(entity);
        return mm;
    }
}
