package com.wust.springcloud.admin.server.core.web.controller;

import com.wust.springcloud.admin.server.core.service.SysDataSourceService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
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
    ResponseDto listPage(@RequestBody SysDataSourceSearch search){
        ResponseDto baseDto = new ResponseDto();
        List<SysDataSourceList> sysDataSourceLists =  sysDataSourceService.listPage(search);
        baseDto.setPage(search.getPageDto());
        baseDto.setLstDto(sysDataSourceLists);
        return baseDto;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_DATA_SOURCE,businessName="初始化数据源",operationType= OperationLogEnum.Insert)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public @ResponseBody
    ResponseDto create(@RequestBody SysDataSource entity){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        entity.setCreaterId(ctx.getUserId());
        entity.setCreaterName(ctx.getLoginName());
        mm = sysDataSourceService.insert(entity);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_DATA_SOURCE,businessName="修改",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public @ResponseBody
    ResponseDto update(@RequestBody SysDataSource entity){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        entity.setModifyId(ctx.getUserId());
        entity.setModifyName(ctx.getLoginName());
        sysDataSourceService.update(entity);
        return mm;
    }
}
