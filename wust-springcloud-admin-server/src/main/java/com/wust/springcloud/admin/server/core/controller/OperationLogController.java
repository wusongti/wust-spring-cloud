package com.wust.springcloud.admin.server.core.controller;

import com.wust.springcloud.admin.server.core.service.SysOperationLogService;
import com.wust.springcloud.common.dto.ResponseDto;
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
    ResponseDto listPage(@RequestBody SysOperationLogSearch sysOperationLogSearch){
        ResponseDto baseDto = new ResponseDto();
        List<SysOperationLogList> sysOperationLogLists =  sysOperationLogServiceImpl.listPage(sysOperationLogSearch);
        baseDto.setPage(sysOperationLogSearch.getPageDto());
        baseDto.setLstDto(sysOperationLogLists);
        return baseDto;
    }
}
