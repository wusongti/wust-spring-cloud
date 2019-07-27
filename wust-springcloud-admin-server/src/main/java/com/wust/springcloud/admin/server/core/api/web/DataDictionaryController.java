package com.wust.springcloud.admin.server.core.api.web;

import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.lookup.SysLookupList;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import com.wust.springcloud.common.util.cache.DataDictionaryUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by WST on 2019/5/28.
 */
@RequestMapping("/DataDictionaryController")
@RestController
public class DataDictionaryController {
    @Autowired
    private SpringRedisTools springRedisTools;


    @RequestMapping(value = "/getLookupListByParentCode/{parentCode}/{defaultValue}",method = RequestMethod.POST)
    public @ResponseBody
    ResponseDto getLookupListByParentCode(@PathVariable String parentCode, @PathVariable String defaultValue){
        ResponseDto baseDto = new ResponseDto();
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        List<SysLookupList> sysLookupLists = DataDictionaryUtil.getLookupListByParentCode(ctx.getLocale().toString(),parentCode);
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
            mm.setFlag(ResponseDto.INFOR_WARNING);
            mm.setMessage("没有找到数据字典记录");
        }
        return baseDto;
    }
}
