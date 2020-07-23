package nat.phc.blog.controller.user;

import lombok.extern.slf4j.Slf4j;
import nat.phc.blog.pojo.SobUser;
import nat.phc.blog.response.ResponseResult;
import nat.phc.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 7:32 2020/7/21
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private IUserService userService;

    /**
     * 初始化管理员账号
     */
    @PostMapping("/admin_account")
    public ResponseResult initManagerAccount(@RequestBody SobUser sobUser, HttpServletRequest request) {
        return userService.initManagerAccount(sobUser,request);
    }

    /**
     * 注册帐号
     *
     * @param sobUser
     * @return
     */
    @PostMapping
    public ResponseResult register(@RequestBody SobUser sobUser) {
        return null;
    }

    /**
     * @param captcha
     * @param sobUser
     * @return
     */
    @PostMapping("/{captcha}")
    public ResponseResult login(@PathVariable("captcha") String captcha, @RequestBody SobUser sobUser) {
        return null;
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @GetMapping("/captcha")
    public ResponseResult getCaptcha() {
        return null;
    }

    /**
     * 邮箱地址
     *
     * @param emailAddress
     * @return
     */
    @GetMapping("/verify_code")
    public ResponseResult sendVerifyCode(@RequestParam("email") String emailAddress) {
        log.info("email========>" + emailAddress);
        return null;
    }

    /**
     * 修改密码
     *
     * @param sobUser
     * @return
     */
    @PutMapping("/password/{userId}")
    public ResponseResult updatePassword(@RequestBody SobUser sobUser, @PathVariable("userId") String userId) {
        return null;
    }

    /**
     * 获得作者信息
     *
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseResult getUserInfo(@PathVariable("userId") String userId) {
        return null;
    }

    /**
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseResult updateUserInfo(@RequestBody SobUser sobUser, @PathVariable("userId") String userId) {
        return null;
    }

    /**
     * 获取用户列表
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return null;
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseResult deleteUser(@PathVariable("userId") String userId) {
        return null;
    }

}
