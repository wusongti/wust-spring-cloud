package com.wust.springcloud.admin.server.core.web.api;


import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.version.ApiVersion;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/{version}/DemoApi")
@RestController
@ApiVersion(2)
public class V2DemoApi {
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public @ResponseBody
    ResponseDto test() {
        ResponseDto mm = new ResponseDto();
        mm.setMessage("版本2");
        return mm;
    }
}
