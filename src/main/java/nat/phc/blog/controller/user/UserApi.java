package nat.phc.blog.controller.user;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
                                   @RequestParam("email_code") String emailCode,
                                   @RequestParam("captcha_code") String captchaCode,
                                   @RequestParam("captcha_key") String captchaKey,
                                   HttpServletRequest request) {
        return userService.register(sobUser, emailCode, captchaCode, captchaKey, request);

    }

    /**
     * 登录需要提交的数据
     * 用户的账号，可以是昵称或者邮箱
     * 用户密码
     * 图灵码
     * 图灵码key
     *
     * @param captcha
     * @param sobUser
     * @return
     */
    @PostMapping("/{captcha}/{captcha_key}")
    public ResponseResult login(@PathVariable("captcha_key") String captchaKey,
                                @PathVariable("captcha") String captcha,
                                @RequestBody SobUser sobUser,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        return userService.doLogin(captcha, captchaKey, sobUser, request, response);
    }


    /**
     * 获取图灵验证码
     *
     * @return
     */
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, @RequestParam("captcha_key") String captchaKey) {
        try {
            userService.getCaptcha(response, captchaKey);
        } catch (Exception e) {
            log.error(e.toString());
        }

    }


    /**
     * 发送邮件
     * 三种使用场景，注册，找回密码，修改邮箱
     *
     * @param emailAddress
     * @return
     */
    @GetMapping("/verify_code")
    public ResponseResult sendVerifyCode(HttpServletRequest request, @RequestParam("type") String type,
                                         @RequestParam("email") String emailAddress) {
        log.info("email========>" + emailAddress);
        return userService.sendEmail(type, request, emailAddress);
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
        return userService.getUserInfo(userId);
    }

    /**
     * 允许用户修改的信息
     * 头像
     * username（唯一）
     * password（单独接口）
     * sign
     * email（单独接口）（唯一）
     * <p>
     * 需要校验用户权限
     *
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseResult updateUserInfo(HttpServletRequest request, HttpServletResponse response,
                                         @RequestBody SobUser sobUser, @PathVariable("userId") String userId) {
        return userService.upDateUserInfo(request, response, sobUser, userId);
    }

    /**
     * 获取用户列表(需要管理员权限)
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listUsers(@RequestParam("page") int page, @RequestParam("size") int size,
                                    HttpServletRequest request, HttpServletResponse response) {
        return userService.listUser(page, size, request, response);
    }
    /**
     * 该api为删除用户的api，也就是修改状态
     * 需要管理员权限
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseResult deleteUser(HttpServletResponse response, HttpServletRequest request,
                                     @PathVariable("userId") String userId) {
        //判断当前操作的用户是谁
        //TODO 使用注解的方式开控制权限
        return userService.deleteUserById(userId, request, response);
    }


    @ApiResponses({
            @ApiResponse(code = 200, message = "当前邮箱已经注册"),
            @ApiResponse(code = 400, message = "当前邮箱没注册")
    })
    @GetMapping("/email")
    public ResponseResult checkEmail(@RequestParam("email") String email) {
        return userService.CheckEmail(email);
    }


    @ApiResponses({
            @ApiResponse(code = 200, message = "当前用户名已经注册"),
            @ApiResponse(code = 400, message = "当前用户名没注册")
    })
    @GetMapping("/user_name")
    public ResponseResult checkUserName(@RequestParam("user_name") String userName) {
        return userService.CheckUserName(userName);
    }

}
