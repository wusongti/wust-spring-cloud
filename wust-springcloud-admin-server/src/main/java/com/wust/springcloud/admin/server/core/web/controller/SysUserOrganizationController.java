package com.wust.springcloud.admin.server.core.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.mq.producer.UpdateUserOrganizationProducer;
import com.wust.springcloud.common.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WST on 2019/6/5.
 */
@RequestMapping("/SysUserOrganization")
@RestController
public class SysUserOrganizationController {
    @Autowired
    private UpdateUserOrganizationProducer updateUserOrganizationProducer;

    /**
     * 初始化用户组织关系
     * @return
     */
    @RequestMapping(value = "/init",method = RequestMethod.POST)
    public ResponseDto init() {
        ResponseDto mm = new ResponseDto();
        updateUserOrganizationProducer.send(new JSONObject());
        return mm;
    }
}
