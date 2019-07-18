package com.wust.springcloud.admin.server.core.api.open;

import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/DemoApi")
@RestController
public class DemoApi {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public @ResponseBody
    ResponseDto test() {
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        System.out.println("获得参数=版本1"+ctx.getSignMap());
        return mm;
    }
}
