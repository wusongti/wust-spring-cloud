package com.wust.springcloud.admin.server.core.service.imports;

import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.dto.ResponseDto;

/**
 * Created by WST on 2019/5/27.
 */
public interface SysRoleImportService{
    ResponseDto importByExcel(JSONObject jsonObject);
}
