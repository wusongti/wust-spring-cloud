package com.wust.springcloud.admin.server.core.rpc.api;



import com.wust.springcloud.common.dto.ResponseDto;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/DemoRpc")
@RestController
public class DemoRpc {
    @GetMapping(value = "/test")
    public ResponseDto test() {
        ResponseDto mm = new ResponseDto();
        mm.setMessage("版本1");
        System.out.println(" 版本1");
        return mm;
    }
}
