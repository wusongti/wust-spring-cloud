package com.wust.springcloud.sso.server.core.web.rpc;

import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author ：wust
 * @date ：Created in 2019/7/18 14:09
 * @description：
 * @version:
 */
@RequestMapping("/VerifyApiRpc")
@RestController
public class VerifyApiRpc {
    @Autowired
    private SpringRedisTools springRedisTools;

    /**
     * 缓存是否已经存在接口签名
     * @param sign
     * @return
     */
    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_COMMON,businessName="查询缓存是否已经存在该API签名",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/hasSign/{sign}")
    public boolean hasSign(@PathVariable String sign) {
        if(springRedisTools.hasKey(sign)){
            return true;
        }else {
            springRedisTools.addData(sign,sign,5, TimeUnit.MINUTES);
            return false;
        }
    }
}
