package nat.phc.blog.controller;

import nat.phc.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PengHaiChen
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public ResponseResult helloWorld() {
        ResponseResult responseResult = new ResponseResult(true,2000,"操作成功","helloWorld");
        return responseResult;
    }
}
