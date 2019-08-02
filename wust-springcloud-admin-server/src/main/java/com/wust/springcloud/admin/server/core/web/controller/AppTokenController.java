package com.wust.springcloud.admin.server.core.web.controller;


import com.wust.springcloud.admin.server.core.service.defaults.SysAppTokenService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
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
import java.util.Date;
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
    ResponseDto listPage(@RequestBody SysAppTokenSearch search){
        ResponseDto baseDto = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        List<SysAppTokenList> sysAppTokenLists =  sysAppTokenServiceImpl.listPage(search);
        if(CollectionUtils.isNotEmpty(sysAppTokenLists)){
            for (SysAppTokenList sysAppTokenList : sysAppTokenLists) {
                sysAppTokenList.setStatusLabel(DataDictionaryUtil.getLookupNameByCode(ctx.getLocale().toString(),sysAppTokenList.getStatus()));
            }
        }
        baseDto.setPage(search.getPageDto());
        baseDto.setLstDto(sysAppTokenLists);
        return baseDto;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_APP_TOKEN,businessName="新建",operationType= OperationLogEnum.Insert)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public @ResponseBody
    ResponseDto create(@RequestBody SysAppToken entity){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        SysAppTokenSearch sysAppTokenSearch = new SysAppTokenSearch();
        sysAppTokenSearch.setLoginName(entity.getLoginName());
        List<SysAppTokenList> sysAppTokenLists = sysAppTokenServiceImpl.findByCondition(sysAppTokenSearch);
        if(CollectionUtils.isNotEmpty(sysAppTokenLists)){
            mm.setFlag(ResponseDto.INFOR_WARNING);
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
    ResponseDto updatePassword(@RequestBody SysAppToken entity){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        entity.setModifyId(ctx.getUserId());
        entity.setModifyName(ctx.getLoginName());
        String passwordRC4 = org.apache.commons.codec.binary.Base64.encodeBase64String(RC4.encry_RC4_string(entity.getPassword(), ApplicationEnum.RC4_LOGIN_PASSWORD.getStringValue()).getBytes());
        entity.setPassword(passwordRC4);
        entity.setModifyTime(new Date());
        sysAppTokenServiceImpl.updateByPrimaryKey(entity);
        return mm;
    }

    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_APP_TOKEN,businessName="修改状态",operationType= OperationLogEnum.Update)
    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    public @ResponseBody
    ResponseDto updateStatus(@RequestBody SysAppToken entity){
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        entity.setCreaterId(ctx.getUserId());
        entity.setCreaterName(ctx.getLoginName());

        if("100201".equals(entity.getStatus())){
            entity.setStatus("100202");
        }else{
            entity.setStatus("100201");
        }
        entity.setModifyTime(new Date());
        sysAppTokenServiceImpl.updateByPrimaryKey(entity);
        return mm;
    }


    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_ADMIN_APP_TOKEN,businessName="删除",operationType= OperationLogEnum.Delete)
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseDto delete(@PathVariable Long id){
        ResponseDto mm = new ResponseDto();
        sysAppTokenServiceImpl.deleteByPrimaryKey(id);
        return mm;
    }
}
