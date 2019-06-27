package com.wust.springcloud.admin.server.controller;


import com.wust.springcloud.admin.server.service.SysAppTokenService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.BaseDto;
import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppToken;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenList;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenSearch;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.RC4;
import com.wust.springcloud.common.util.cache.DataDictionaryUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by WST on 2019/6/5.
 */
@RequestMapping("/AppTokenController")
@RestController
public class AppTokenController {
    @Autowired
    private SysAppTokenService sysAppTokenServiceImpl;

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_APP_TOKEN,businessName="分页查询",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public @ResponseBody
    BaseDto listPage(@RequestBody SysAppTokenSearch search){
        BaseDto baseDto = new BaseDto();
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        List<SysAppTokenList> sysAppTokenLists =  sysAppTokenServiceImpl.listPage(search);
        if(CollectionUtils.isNotEmpty(sysAppTokenLists)){
            for (SysAppTokenList sysAppTokenList : sysAppTokenLists) {
                sysAppTokenList.setStatusLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getCompanyId(),sysAppTokenList.getStatus()));
            }
        }
        baseDto.setPage(search.getPageDto());
        baseDto.setLstDto(sysAppTokenLists);
        baseDto.setMessageMap(mm);
        return baseDto;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_APP_TOKEN,businessName="新建",operationType= OperationLogEnum.Insert)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap create(@RequestBody SysAppToken entity){
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        SysAppTokenSearch sysAppTokenSearch = new SysAppTokenSearch();
        sysAppTokenSearch.setLoginName(entity.getLoginName());
        List<SysAppTokenList> sysAppTokenLists = sysAppTokenServiceImpl.findByCondition(sysAppTokenSearch);
        if(CollectionUtils.isNotEmpty(sysAppTokenLists)){
            mm.setFlag(MessageMap.INFOR_WARNING);
            mm.setMessage("该账号已经存在");
            return mm;
        }

        entity.setCreaterId(ctx.getUserId());
        entity.setCreaterName(ctx.getLoginName());

        String passwordRC4 = org.apache.commons.codec.binary.Base64.encodeBase64String(RC4.encry_RC4_string(entity.getPassword(), ApplicationEnum.RC4_LOGIN_PASSWORD.getStringValue()).getBytes());
        entity.setPassword(passwordRC4);
        sysAppTokenServiceImpl.insert(entity);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_APP_TOKEN,businessName="修改密码",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap updatePassword(@RequestBody SysAppToken entity){
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        entity.setModifyId(ctx.getUserId());
        entity.setModifyName(ctx.getLoginName());
        String passwordRC4 = org.apache.commons.codec.binary.Base64.encodeBase64String(RC4.encry_RC4_string(entity.getPassword(), ApplicationEnum.RC4_LOGIN_PASSWORD.getStringValue()).getBytes());
        entity.setPassword(passwordRC4);
        sysAppTokenServiceImpl.update(entity);
        return mm;
    }

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_APP_TOKEN,businessName="修改状态",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    public @ResponseBody
    MessageMap updateStatus(@RequestBody SysAppToken entity){
        MessageMap mm = new MessageMap();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        entity.setCreaterId(ctx.getUserId());
        entity.setCreaterName(ctx.getLoginName());

        if("100201".equals(entity.getStatus())){
            entity.setStatus("100202");
        }else{
            entity.setStatus("100201");
        }

        sysAppTokenServiceImpl.update(entity);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_APP_TOKEN,businessName="删除",operationType= OperationLogEnum.Delete)
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public @ResponseBody
    MessageMap delete(@PathVariable String id){
        MessageMap mm = new MessageMap();
        sysAppTokenServiceImpl.delete(id);
        return mm;
    }
}
