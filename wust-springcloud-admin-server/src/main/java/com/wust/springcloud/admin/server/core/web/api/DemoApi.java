package com.wust.springcloud.admin.server.core.web.api;



import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.version.ApiVersion;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/DemoApi")
@RestController
@ApiVersion(1)
public class DemoApi {
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public @ResponseBody
    ResponseDto test() {
        ResponseDto mm = new ResponseDto();
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        System.out.println("获得参数="+ctx.getSignMap());
        return mm;
    }
}
