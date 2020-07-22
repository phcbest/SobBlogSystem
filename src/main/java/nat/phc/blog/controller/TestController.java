package nat.phc.blog.controller;

import lombok.extern.slf4j.Slf4j;
import nat.phc.blog.pojo.testPojo;
import nat.phc.blog.pojo.testUser;
import nat.phc.blog.response.ResponseResult;
import nat.phc.blog.response.ResponseState;
import org.springframework.web.bind.annotation.*;

/**
 * @author PengHaiChen
 */
@Slf4j
@RestController//这个注解将类自动填入工厂并且调用api能返回参数
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public ResponseResult helloWorld() {

        log.info("hello");
        return ResponseResult.SUCCESS().setData("以后再也不打ow的排位了，头都给人打爆");
    }

    @GetMapping("/nohello")
    public ResponseResult noHelloWorld() {
        log.info("nohello");
        return ResponseResult.FAILED().setData(new testPojo("996福报杰克马",
                100, "人民富豪小区", "对钱没有兴趣路"));
    }

    @PostMapping("/helloRes")
    public ResponseResult abababa(@RequestBody testUser user) {
        log.info("helloRes");
        ResponseResult responseResult = new ResponseResult(ResponseState.LOGIN_SUCCESS);
        return responseResult.setData(new testPojo(user.toString() + "白手起家腾化马",
                30, "低调赚钱小区", "从不创新路"));
    }
}
