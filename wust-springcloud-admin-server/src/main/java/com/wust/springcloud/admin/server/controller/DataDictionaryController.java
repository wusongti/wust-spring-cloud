package com.wust.springcloud.admin.server.controller;


import com.wust.springcloud.admin.server.service.SysLookupPrivateService;
import com.wust.springcloud.admin.server.service.SysLookupService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.BaseDto;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.lookup.SysLookup;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupList;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupSearch;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import com.wust.springcloud.common.util.cache.DataDictionaryUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WST on 2019/5/28.
 */
@RequestMapping("/DataDictionaryController")
@RestController
public class DataDictionaryController {
    @Autowired
    private SpringRedisTools springRedisTools;

    @Autowired
    private SysLookupService sysLookupServiceImpl;

    @Autowired
    private SysLookupPrivateService sysLookupPrivateServiceImpl;


    @RequestMapping(value = "/getLookupListByParentCode/{parentCode}/{defaultValue}",method = RequestMethod.POST)
    public @ResponseBody
    BaseDto getLookupListByParentCode(@PathVariable String parentCode, @PathVariable String defaultValue){
        BaseDto baseDto = new BaseDto();
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        List<SysLookupList> sysLookupLists = DataDictionaryUtil.getLookupListByParentCode(ctx.getCompanyId(),parentCode);
        if (CollectionUtils.isNotEmpty(sysLookupLists)) {
            StringBuffer html = new StringBuffer();
            boolean hasDefaultValueFlag = false;
            for (SysLookupList sysLookupList : sysLookupLists) {
                String visible = sysLookupList.getVisible();
                String value = sysLookupList.getCode();
                String label = sysLookupList.getName();

                if("100702".equals(visible)){
                    // 不显示
                    continue;
                }

                if (MyStringUtils.isNotBlank(MyStringUtils.null2String(defaultValue)) && MyStringUtils.null2String(defaultValue).equals(value)) {
                    hasDefaultValueFlag = true;
                    html.append("<option value=" + value + " selected>" + label + "</option>");
                } else {
                    html.append("<option value=" + value + ">" + label + "</option>");
                }
            }
            String result = "";
            if (!hasDefaultValueFlag) {
                result = "<option value=\"\" selected>--请选择--</option>" + html.toString();
            } else {
                result = "<option value=\"\">--请选择--</option>" + html.toString();
            }
            baseDto.setT(result);
        }else{
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("没有找到数据字典记录");
        }
        baseDto.setMessageMap(mm);
        return baseDto;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_LOOKUP,businessName="查询默认数据字典表格树数据",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/getLookupTableTreeData",method = RequestMethod.POST)
    public @ResponseBody
    BaseDto getLookupTableTreeData(@RequestBody SysLookupSearch sysLookupSearch){
        BaseDto baseDto = new BaseDto();
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        sysLookupSearch.setRootCode("0000");
        List<SysLookupList> sysLookupLists =  sysLookupServiceImpl.findByCondition(sysLookupSearch);
        if(CollectionUtils.isNotEmpty(sysLookupLists)){
            List<SysLookupList> resultList = new ArrayList<>(100);
            for (SysLookupList sysLookupList : sysLookupLists) {
                sysLookupList.setVisibleLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getCompanyId(),sysLookupList.getVisible()));
                sysLookupList.setStatusLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getCompanyId(),sysLookupList.getStatus()));
                resultList.add(sysLookupList);
                getChildrenByParentCode(sysLookupList,resultList);
            }
            baseDto.setLstDto(resultList);
        }
        baseDto.setPage(sysLookupSearch.getPageDto());
        baseDto.setMessageMap(mm);
        return baseDto;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_LOOKUP,businessName="查询个性化数据字典表格树数据",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/getIndividuationLookupTableTreeData",method = RequestMethod.POST)
    public @ResponseBody
    BaseDto getIndividuationLookupTableTreeData(@RequestBody SysLookupSearch sysLookupSearch){
        BaseDto baseDto = new BaseDto();
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        sysLookupSearch.setRootCode("0000");
        List<SysLookupList> sysLookupLists =  sysLookupPrivateServiceImpl.findByCondition(sysLookupSearch);
        if(CollectionUtils.isNotEmpty(sysLookupLists)){
            List<SysLookupList> resultList = new ArrayList<>(100);
            for (SysLookupList sysLookupList : sysLookupLists) {
                sysLookupList.setVisibleLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getCompanyId(),sysLookupList.getVisible()));
                sysLookupList.setStatusLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getCompanyId(),sysLookupList.getStatus()));
                resultList.add(sysLookupList);
                getChildrenByParentCode(sysLookupList,resultList);
            }
            baseDto.setLstDto(resultList);
        }
        baseDto.setPage(sysLookupSearch.getPageDto());
        baseDto.setMessageMap(mm);
        return baseDto;
    }

    private void getChildrenByParentCode(SysLookup parent,final List<SysLookupList> resultList){
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        List<SysLookupList> sysLookupLists = DataDictionaryUtil.getLookupListByParentCode(ctx.getCompanyId(),parent.getCode());
        if(CollectionUtils.isNotEmpty(sysLookupLists)){
            for (SysLookupList sysLookupList : sysLookupLists) {
                sysLookupList.setVisibleLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getCompanyId(),sysLookupList.getVisible()));
                sysLookupList.setStatusLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getCompanyId(),sysLookupList.getStatus()));
                resultList.add(sysLookupList);
                getChildrenByParentCode(sysLookupList,resultList);
            }
        }
    }



    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_LOOKUP,businessName="判断个性化数据是否已经存在",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/exist",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap exist(@RequestBody SysLookupSearch sysLookupSearch){
        MessageMap mm = new MessageMap();
        List<SysLookupList> sysLookupLists =  sysLookupPrivateServiceImpl.findByCondition(sysLookupSearch);
        if(CollectionUtils.isNotEmpty(sysLookupLists)){
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("该记录已经存在个性化表");
            return mm;
        }
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_LOOKUP,businessName="新增个性化数据",operationType= OperationLogEnum.Insert)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap create(@RequestBody SysLookup sysLookup){
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        sysLookup.setCreaterId(ctx.getUserId());
        sysLookup.setCreaterName(ctx.getLoginName());

        sysLookupPrivateServiceImpl.insert(sysLookup);
        return mm;
    }

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_LOOKUP,businessName="修改个性化数据",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap update(@RequestBody SysLookup sysLookup){
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        sysLookup.setCreaterId(ctx.getUserId());
        sysLookup.setCreaterName(ctx.getLoginName());

        sysLookupPrivateServiceImpl.update(sysLookup);
        return mm;
    }

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_LOOKUP,businessName="复制个性化数据",operationType= OperationLogEnum.Insert)
    @RequestMapping(value = "/copy",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap copy(@RequestBody SysLookup sysLookup){
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        SysLookupSearch sysLookupSearch = new SysLookupSearch();
        sysLookupSearch.setCode(sysLookup.getCode());
        sysLookupSearch.setLan(sysLookup.getLan());
        List<SysLookupList> sysLookupLists = sysLookupPrivateServiceImpl.findByCondition(sysLookupSearch);
        if(CollectionUtils.isNotEmpty(sysLookupLists)){
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("该语言["+sysLookup.getLan()+"]的记录已经存在，请换一种语言试试");
            return mm;
        }

        sysLookup.setCreaterId(ctx.getUserId());
        sysLookup.setCreaterName(ctx.getLoginName());
        sysLookupPrivateServiceImpl.copy(sysLookup);
        return mm;
    }

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_LOOKUP,businessName="修改显示隐藏属性",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/updateVisible",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap updateVisible(@RequestBody SysLookup sysLookup){
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        sysLookup.setCreaterId(ctx.getUserId());
        sysLookup.setCreaterName(ctx.getLoginName());

        if("100701".equals(sysLookup.getVisible())){
            sysLookup.setVisible("100702");
        }else{
            sysLookup.setVisible("100701");
        }

        sysLookupPrivateServiceImpl.update(sysLookup);
        return mm;
    }

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_LOOKUP,businessName="修改启用禁用属性",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap updateStatus(@RequestBody SysLookup sysLookup){
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        sysLookup.setCreaterId(ctx.getUserId());
        sysLookup.setCreaterName(ctx.getLoginName());

        if("100201".equals(sysLookup.getStatus())){
            sysLookup.setStatus("100202");
        }else{
            sysLookup.setStatus("100201");
        }

        sysLookupPrivateServiceImpl.update(sysLookup);
        return mm;
    }


}
