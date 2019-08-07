package com.wust.springcloud.admin.server.core.service;


import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.dto.ResponseDto;

/**
 * Created by WST on 2019/5/24.
 */
public interface SysUserImportService{
    ResponseDto importByExcel(JSONObject jsonObject);
}
