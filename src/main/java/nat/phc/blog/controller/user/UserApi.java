package nat.phc.blog.controller.user;

import lombok.extern.slf4j.Slf4j;
import nat.phc.blog.pojo.SobUser;
import nat.phc.blog.response.ResponseResult;
import nat.phc.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        return userService.initManagerAccount(sobUser, request);
    }

    /**
     * 注册帐号
     *
     * @param sobUser
     * @return
     */
    @PostMapping
    public ResponseResult register(@RequestBody SobUser sobUser,
                                   @RequestParam("email_code")String emailCode,
                                   @RequestParam("captcha_code")String captchaCode,
                                   @RequestParam("captcha_key")String captchaKey,
                                   HttpServletRequest request) {
        return userService.register(sobUser,emailCode,captchaCode,captchaKey,request);

    }

    /**
     * 登录需要提交的数据
     * 用户的账号，可以是昵称或者邮箱
     * 用户密码
     * 图灵码
     * 图灵码key
     *
     *
     * @param captcha
     * @param sobUser
     * @return
     */
    @PostMapping("/{captcha}/{captcha_key}")
    public ResponseResult login( @PathVariable("captcha_key") String captchaKey,
                                 @PathVariable("captcha") String captcha,
                                 @RequestBody SobUser sobUser,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        return userService.doLogin(captcha,captchaKey,sobUser,request,response);
    }



    /**
     * 获取图灵验证码
     *
     * @return
     */
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, @RequestParam("captcha_key") String captchaKey)  {
        try {
            userService.getCaptcha(response,captchaKey);
        } catch (Exception e) {
            log.error(e.toString());
        }

    }


    /**
     * 发送邮件
     *三种使用场景，注册，找回密码，修改邮箱
     *
     *
     * @param emailAddress
     * @return
     */
    @GetMapping("/verify_code")
    public ResponseResult sendVerifyCode(HttpServletRequest request,@RequestParam("type")String type,
                                         @RequestParam("email") String emailAddress) {
        log.info("email========>" + emailAddress);
        return userService.sendEmail(type,request,emailAddress);
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
