package com.wust.springcloud.admin.server.core.api;


import com.wust.springcloud.admin.server.version.ApiVersion;
import com.wust.springcloud.common.dto.ResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/{version}/DemoApi")
@RestController
@ApiVersion(1)
public class V1DemoApi {
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public @ResponseBody
    ResponseDto test() {
        ResponseDto mm = new ResponseDto();
        mm.setMessage("版本1");
        return mm;
    }
}
