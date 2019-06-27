package com.wust.springcloud.admin.server.controller;

import com.wust.springcloud.admin.server.service.SysOperationLogService;
import com.wust.springcloud.common.dto.BaseDto;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLogList;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLogSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by WST on 2019/5/28.
 */
@RequestMapping("/OperationLogController")
@RestController
public class OperationLogController {
    @Autowired
    private SysOperationLogService sysOperationLogServiceImpl;

    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public @ResponseBody
    BaseDto listPage(@RequestBody SysOperationLogSearch sysOperationLogSearch){
        BaseDto baseDto = new BaseDto();
        MessageMap mm = new MessageMap();
        List<SysOperationLogList> sysOperationLogLists =  sysOperationLogServiceImpl.listPage(sysOperationLogSearch);
        baseDto.setPage(sysOperationLogSearch.getPageDto());
        baseDto.setLstDto(sysOperationLogLists);
        baseDto.setMessageMap(mm);
        return baseDto;
    }
}
