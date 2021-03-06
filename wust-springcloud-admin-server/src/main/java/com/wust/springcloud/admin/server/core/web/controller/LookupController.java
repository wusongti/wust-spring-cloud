package com.wust.springcloud.admin.server.core.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.service.SysLookupService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.lookup.SysLookup;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupList;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupSearch;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.cache.DataDictionaryUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author ：wust
 * @date ：Created in 2019/7/26 14:48
 * @description：
 * @version:
 */
@RequestMapping("/LookupController")
@RestController
public class LookupController {
    @Autowired
    private SysLookupService sysLookupServiceImpl;


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_LOOKUP,businessName="构建数据字典树",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/buildLookupTree",method = RequestMethod.POST)
    public ResponseDto buildLookupTree(){
        ResponseDto baseDto = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        JSONArray jsonArray = new JSONArray();
        JSONObject rootJSONObject = new JSONObject();
        jsonArray.add(rootJSONObject);
        rootJSONObject.put("id","0000");
        rootJSONObject.put("pId",null);
        rootJSONObject.put("rootCode",null);
        rootJSONObject.put("lookupId","-1");
        rootJSONObject.put("name","数据字典");
        rootJSONObject.put("open",true);

        SysLookupSearch sysLookupSearch = new SysLookupSearch();
        sysLookupSearch.setLan(ctx.getLocale().toString());
        List<SysLookup> sysLookupLists =  sysLookupServiceImpl.select(sysLookupSearch);
        if(CollectionUtils.isNotEmpty(sysLookupLists)){
            for (SysLookup sysLookup : sysLookupLists) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",sysLookup.getCode());
                jsonObject.put("pId",sysLookup.getParentCode());
                jsonObject.put("rootCode",sysLookup.getRootCode());
                jsonObject.put("lookupId",sysLookup.getId());
                jsonObject.put("name",sysLookup.getDescription());
                jsonObject.put("open",false);
                jsonArray.add(jsonObject);
            }
            baseDto.setObj(jsonArray.toJSONString());
        }
        return baseDto;
    }

    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public ResponseDto listPage(@RequestBody SysLookupSearch sysLookupSearch){
        ResponseDto responseDto = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        sysLookupSearch.setLan(ctx.getLocale().toString());
        List<SysLookupList>  sysLookupLists = sysLookupServiceImpl.listPage(sysLookupSearch);
        if (CollectionUtils.isNotEmpty(sysLookupLists)) {
            for (SysLookupList sysLookupList : sysLookupLists) {
                sysLookupList.setVisibleLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getLocale().toString(),sysLookupList.getVisible()));
                sysLookupList.setStatusLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getLocale().toString(),sysLookupList.getStatus()));
            }

            responseDto.setLstDto(sysLookupLists);
            responseDto.setPage(sysLookupSearch.getPageDto());
        }else{
            responseDto.setFlag(ResponseDto.INFOR_WARNING);
            responseDto.setMessage("没有找到数据字典记录");
        }
        return responseDto;
    }
}
