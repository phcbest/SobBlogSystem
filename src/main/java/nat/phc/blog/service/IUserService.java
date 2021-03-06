package nat.phc.blog.service;

import nat.phc.blog.pojo.SobUser;
import nat.phc.blog.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 19:59 2020/7/22
 */
public interface IUserService {
    /**
     *
     * @param sobUser
     * @param request
     * @return
     */
    ResponseResult initManagerAccount(SobUser sobUser, HttpServletRequest request);

    void getCaptcha(HttpServletResponse response, String captchaKey) throws Exception;

    ResponseResult sendEmail(String type, HttpServletRequest request, String emailAddress);

    ResponseResult register(SobUser sobUser, String emailCode, String captchaCode, String captchaKey, HttpServletRequest request);

    ResponseResult doLogin(String captcha, String captchaKey, SobUser sobUser, HttpServletRequest request, HttpServletResponse response);

    SobUser checkSobUser(HttpServletRequest request,HttpServletResponse response);

    ResponseResult getUserInfo(String userId);

    ResponseResult CheckEmail(String email);

    ResponseResult CheckUserName(String userName);

    ResponseResult upDateUserInfo(HttpServletRequest request, HttpServletResponse response, SobUser sobUser, String userId);

    ResponseResult deleteUserById(String userId, HttpServletRequest request, HttpServletResponse response);

    ResponseResult listUser(int page, int size, HttpServletRequest request, HttpServletResponse response);
}
