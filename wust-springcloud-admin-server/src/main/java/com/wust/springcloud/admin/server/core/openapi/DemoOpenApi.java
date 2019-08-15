package com.wust.springcloud.admin.server.core.openapi;

import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/DemoOpenApi")
@RestController
public class DemoOpenApi {

    @RequestMapping(value = "/v1/test",method = RequestMethod.GET)
    public @ResponseBody
    ResponseDto test() {
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        System.out.println("获得参数=版本1"+ctx.getSignMap());
        return mm;
    }
}
