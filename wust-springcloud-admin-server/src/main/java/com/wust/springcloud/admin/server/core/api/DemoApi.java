package com.wust.springcloud.admin.server.core.api;



import com.wust.springcloud.common.dto.ResponseDto;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/DemoApi")
@RestController
public class DemoApi {
    @GetMapping(value = "/v1/test")
    public ResponseDto test() {
        ResponseDto mm = new ResponseDto();
        mm.setMessage("版本1");
        System.out.println(" 版本1");
        return mm;
    }
}
