package nat.phc.blog.service.impl;

import com.google.gson.Gson;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import nat.phc.blog.dao.RefreshTokenDao;
import nat.phc.blog.dao.SettingsDao;
import nat.phc.blog.dao.UserDao;
import nat.phc.blog.pojo.RefreshToken;
import nat.phc.blog.pojo.Setting;
import nat.phc.blog.pojo.SobUser;
import nat.phc.blog.response.ResponseResult;
import nat.phc.blog.response.ResponseState;
import nat.phc.blog.service.IUserService;
import nat.phc.blog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 20:02 2020/7/22
 */

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SettingsDao settingsDao;

    @Autowired
    private RefreshTokenDao refreshTokenDao;

    @Autowired
    private Gson gson;


    @Override
    public ResponseResult initManagerAccount(SobUser sobUser, HttpServletRequest request) {
        //检查是否初始化
        Setting managerAccountState = settingsDao.findOneByKey(Constants.Settings.MANAGER_ACCOUNT_INIT_STATE);
        if (managerAccountState != null) {
            return ResponseResult.FAILED("已经初始化过了管理员账号");
        }
        //检查数据
        if (TextUtils.isEmpty(sobUser.getUserName())) {
            return ResponseResult.FAILED("用户名不能为空");
        }
        if (TextUtils.isEmpty(sobUser.getPassword())) {
            return ResponseResult.FAILED("密码不能为空");
        }
        if (TextUtils.isEmpty(sobUser.getEmail())) {
            return ResponseResult.FAILED("邮箱不能为空");
        }
        /**
         补充数据
         */
        //使用雪花算法生成id
        sobUser.setId(String.valueOf(idWorker.nextId()));
        //使用自定义常量定义用户类型
        sobUser.setRoles(Constants.User.ROLE_ADMIN);
        //设置头像
        sobUser.setAvatar(Constants.User.DEFAULT_ADMIN_AVATAR);
        //设置账号状态
        sobUser.setState(Constants.User.DEFAULT_STATE);
        //定影获取登录ip
        String remoteAddr = request.getRemoteAddr();
        log.info("当前ip=====》" + remoteAddr);
        sobUser.setLoginIp(remoteAddr);
        sobUser.setRegIp(remoteAddr);
        //设置时间
        sobUser.setCreateTime(new Date());
        sobUser.setUpdateTime(new Date());
        //对密码进行加密
        String encode = bCryptPasswordEncoder.encode(sobUser.getPassword());
        sobUser.setPassword(encode);
        //保存进数据库中
        userDao.save(sobUser);
        //更新已经添加的标记
        Setting setting = new Setting();
        setting.setId(String.valueOf(idWorker.nextId()));
        setting.setKey(Constants.Settings.MANAGER_ACCOUNT_INIT_STATE);
        setting.setCreateTime(new Date());
        setting.setUpdateTime(new Date());
        setting.setValue("1");
        settingsDao.save(setting);
        return ResponseResult.SUCCESS("初始化成功");
    }

    public static final int[] CAPTCHA_FONT_TYPE = {Captcha.FONT_1, Captcha.FONT_2, Captcha.FONT_3, Captcha.FONT_4,
            Captcha.FONT_5, Captcha.FONT_6, Captcha.FONT_7, Captcha.FONT_8, Captcha.FONT_9, Captcha.FONT_10};

    @Autowired
    private Random random;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void getCaptcha(HttpServletResponse response, String captchaKey) throws Exception {
        //需要传递进来时间，近100年的时间戳都不会大于13位
        if (captchaKey.length() < 13 | TextUtils.isEmpty(captchaKey)) {
            return;
        }
        long key;
        try {
            key = Long.parseLong(captchaKey);

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        //拿到了传入的时间戳
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //随机类型
        Captcha targetCaptcha = null;
        int captchaType = random.nextInt(3);
        if (captchaType == 0) {
            targetCaptcha = new SpecCaptcha(200, 60, 5);
        } else if (captchaType == 1) {
            targetCaptcha = new GifCaptcha(200, 60);

        } else {
            targetCaptcha = new ArithmeticCaptcha(200, 60);
            targetCaptcha.setLen(2);
        }
        //随机字体
        targetCaptcha.setFont(CAPTCHA_FONT_TYPE[random.nextInt(CAPTCHA_FONT_TYPE.length)]);
        //字母数字混合类型
        targetCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        //存入redis
        String content = targetCaptcha.text().toLowerCase();
        redisUtils.set(Constants.User.KEY_CAPTCHA_CONTENT + key, content, 60 * 10);
        //输入图片
        targetCaptcha.out(response.getOutputStream());

    }


    /**
     * 发送验证码邮件
     * 三种使用场景，注册（register），找回密码（forget），修改邮箱（update）
     *
     * @param type
     * @param request
     * @param emailAddress
     * @return
     */
    @Override
    public ResponseResult sendEmail(String type, HttpServletRequest request, String emailAddress) {
        if (emailAddress == null) {
            return ResponseResult.FAILED("邮箱地址不能为空");
        }
        //根据传递进来的类型来进行业务
        if ("register".equals(type) || "update".equals(type)) {
            SobUser userDaoByEmail = userDao.findOneByEmail(emailAddress);
            if (userDaoByEmail != null) {
                return ResponseResult.FAILED("该邮箱已经被注册");
            }
        } else if ("forget".equals(type)) {
            SobUser userDaoByEmail = userDao.findOneByEmail(emailAddress);
            if (userDaoByEmail == null) {
                return ResponseResult.FAILED("该邮箱并没注册");
            }
        }

        //防止暴力发送：60s内只能发送1封，半个小时内同一个ip最多只能发10次
        String remoteAddr = request.getRemoteAddr();
        String replaceAll = null;
        if (remoteAddr != null) {
            replaceAll = remoteAddr.replaceAll(":", "_");
        }
        log.info("申请发送email的ip地址为=====》" + replaceAll);
        //判断ip
        Object ipSendTime = redisUtils.get(Constants.User.KEY_EMAIL_SEND_IP + replaceAll);
        log.info("当前发送次数" + ipSendTime);
        if (ipSendTime != null && (int) ipSendTime > 10) {
            return ResponseResult.FAILED("发送请求太频繁，请过半小时后再尝试");
        }
        Object addressSendTime = redisUtils.get(Constants.User.KEY_EMAIL_SEND_ADDRESS + emailAddress);
        log.info("是否存再验证码对象" + addressSendTime);
        if (addressSendTime != null) {
            return ResponseResult.FAILED("您的请求太频繁了，请60秒后尝试");
        }
        //检查邮箱地址是否正确
        Boolean isEmailFormatOk = TextUtils.isEmailAddressOk(emailAddress);
        if (!isEmailFormatOk) {
            return ResponseResult.FAILED("邮箱地址格式不正确");
        }
        int code = random.nextInt(999999);
        if (code < 100000) {
            code += 100000;
        }
        log.info("申请发送email的验证码为" + code);
        //发送验证码6位数
        try {
            taskService.sendEmailVerifyCode(String.valueOf(code), emailAddress);
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseResult.FAILED("请求失败，服务器原因，发送邮件不成功，请稍后尝试");
        }
        //做记录
        if (ipSendTime == null) {
            ipSendTime = 0;
        }
        ipSendTime = (int) ipSendTime + 1;
        //保存次数 计算出时差
        long expire = redisUtils.getExpire(Constants.User.KEY_EMAIL_SEND_IP + replaceAll);
        redisUtils.set(Constants.User.KEY_EMAIL_SEND_IP + replaceAll, ipSendTime, (60 * 30) - expire);
        //防止暴力申请 倒计时1分钟
        redisUtils.set(Constants.User.KEY_EMAIL_SEND_ADDRESS + emailAddress, "true", 60);
        //保存邮箱验证码
        redisUtils.set(Constants.User.KEY_EMAIL_CODE_CONTENT + emailAddress, String.valueOf(code), 60 * 10);
        return ResponseResult.SUCCESS("发送成功，如没收到请检查垃圾箱");
    }

    @Override
    public ResponseResult register(SobUser sobUser, String emailCode, String captchaCode, String captchaKey, HttpServletRequest request) {
        //检查当前用户名是否已经注册
        String userName = sobUser.getUserName();
        if (TextUtils.isEmpty(userName)) {
            return ResponseResult.FAILED("用户名不能为空");
        }
        SobUser userDaoByUserName = userDao.findOneByUserName(userName);
        if (userDaoByUserName != null) {
            return ResponseResult.FAILED("该用户名已经注册");
        }
        //检查邮箱是否正确
        String email = sobUser.getEmail();
        if (TextUtils.isEmpty(email)) {
            return ResponseResult.FAILED("邮箱地址不能为空");
        }
        if (!TextUtils.isEmailAddressOk(email)) {
            return ResponseResult.FAILED("邮箱地址不正确");
        }
        //检查邮箱是否被注册
        SobUser userDaoByEmail = userDao.findOneByEmail(email);
        if (userDaoByEmail != null) {
            return ResponseResult.FAILED("该邮箱已经注册");
        }
        //检查邮箱验证码是否正确
        String emailVerifyCode = (String) redisUtils.get(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        if (TextUtils.isEmpty(emailVerifyCode)) {
            return ResponseResult.FAILED("邮箱验证码已经失效");
        }
        log.info("两个验证码" + emailCode + ":" + emailVerifyCode);
        if (!emailVerifyCode.equals(emailCode)) {
            return ResponseResult.FAILED("邮箱验证码错误");
        } else {
            redisUtils.del(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        }
        //检查图灵验证码
        String captchaVerifyCode = (String) redisUtils.get(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        if (TextUtils.isEmpty(captchaVerifyCode)) {
            return ResponseResult.FAILED("图灵验证码已经失效");
        }
        if (!captchaVerifyCode.equals(captchaCode)) {
            return ResponseResult.FAILED("图灵验证码不正确");
        } else {
            redisUtils.del(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        }
        //对密码进行加密
        String password = sobUser.getPassword();
        if (TextUtils.isEmpty(password)) {
            return ResponseResult.FAILED("密码不能为空");
        }
        sobUser.setPassword(bCryptPasswordEncoder.encode(password));
        //补全数据:注册ip，登录ip，角色，头像，创建时间，更新时间
        String ipAddress = request.getRemoteAddr();
        sobUser.setRegIp(ipAddress);
        sobUser.setLoginIp(ipAddress);
        sobUser.setCreateTime(new Date());
        sobUser.setUpdateTime(new Date());
        sobUser.setAvatar(Constants.User.DEFAULT_USER_AVATAR);
        sobUser.setRoles(Constants.User.ROLE_NORMAL);
        sobUser.setState("1");
        sobUser.setId(String.valueOf(idWorker.nextId()));
        //保存到数据库
        userDao.save(sobUser);
        //返回结果
        return ResponseResult.GET(ResponseState.JOIN_IN_SUCCESS);
    }

    @Override
    public ResponseResult doLogin(String captcha,
                                  String captchaKey,
                                  SobUser sobUser,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        //验证图灵验证码是否正确
        String captchaValue = (String) redisUtils.get(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        if (!captcha.equals(captchaValue)) {
            return ResponseResult.FAILED("图灵验证码不正确");
        }
        //传入的有可能是name有可能是email
        String userName = sobUser.getUserName();
        if (TextUtils.isEmpty(userName)) {
            return ResponseResult.FAILED("账号不能为空");
        }
        String password = sobUser.getPassword();
        if (TextUtils.isEmpty(password)) {
            return ResponseResult.FAILED("密码不能为空");
        }
        SobUser userFromDb = userDao.findOneByUserName(userName);
        if (userFromDb == null) {
            userFromDb = userDao.findOneByEmail(userName);
        }
        if (userFromDb == null) {
            return ResponseResult.FAILED("用户名或密码错误");
        }
        //用户存在，对比密码
        boolean matches = bCryptPasswordEncoder.matches(password, userFromDb.getPassword());
        if (!matches) {
            ResponseResult.FAILED("用户名或密码错误");
        }
        //密码正确，判断用户状态，如果不真诚返回结果（1是正常）
        if (!"1".equals(userFromDb.getState())) {
            return ResponseResult.ACCOUNT_FORBID();
        }
        createToken(response, userFromDb);
        //删除保存的验证码
        redisUtils.del(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        return ResponseResult.SUCCESS("登录成功");
    }

    private String createToken(HttpServletResponse response, SobUser userFromDb) {
        return createToken(response, userFromDb, null);
    }


    private String createToken(HttpServletResponse response, SobUser userFromDb, String oldTokenKey) {
        //从redis中删除之前的token
        redisUtils.del(oldTokenKey);
        //删除老refreshToken
        refreshTokenDao.deleteAllByUserId(userFromDb.getId());
        //用户密码是正确的，生成token
        Map<String, Object> claims = ClaimsUtils.sobUser2Claims(userFromDb);
        //两个小时的默认有效期
        String token = JwtUtils.createToken(claims);
        //返回token的md5，token保存到redis中，只给前端md5值，前端验证身份的时候给一个md5，然后后台根据md5找到对应token来验证身份
        String tokenKey = DigestUtils.md5DigestAsHex(token.getBytes());
        //token存入redis中，key是tokenKey
        redisUtils.set(Constants.User.KEY_TOKEN + tokenKey, token, Constants.TimeValue.HOUR_2_S);
        //token存储到cookies中
        CookieUtils.setUpCookie(response, Constants.User.COOKIE_TOKEN_KEY, tokenKey);
        //生成长存储的refreshToken存mysql
        String refreshTokenValue = JwtUtils.createRefreshToken(userFromDb.getId(), Constants.TimeValue.MONTH_MS);
        //保存到数据库中
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(String.valueOf(idWorker.nextId()));
        refreshToken.setRefreshToken(refreshTokenValue);
        refreshToken.setTokenKey(tokenKey);
        refreshToken.setUserId(userFromDb.getId());
        refreshToken.setCreateTime(new Date());
        refreshToken.setUpdateTime(new Date());
        refreshTokenDao.save(refreshToken);
        return tokenKey;
    }

    /**
     * 检查用户是否登录如果登录。就返回用户信息
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public SobUser checkSobUser(HttpServletRequest request, HttpServletResponse response) {
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        log.info("拿到的tokenKey为====" + tokenKey);
        SobUser sobUser = parseByTokenKey(tokenKey);
        if (sobUser == null) {
            //解析出错，可能过期了，去数据库查询refreshToken
            RefreshToken refreshToken = refreshTokenDao.findOneByTokenKey(tokenKey);
            if (refreshToken == null) {
                return null;
            }
            try {
                //查询到了refreshToken，创建新的token
                String userId = refreshToken.getUserId();
                SobUser userDaoOneById = userDao.findOneById(userId);
                //创建新的token和新的refreshToken
                String newTokenKey = createToken(response, userDaoOneById, Constants.User.KEY_TOKEN + tokenKey);
                //返回token
                return parseByTokenKey(newTokenKey);
            } catch (Exception exception) {
                return null;
            }
        }
        return sobUser;
    }

    @Override
    public ResponseResult getUserInfo(String userId) {
        //从数据库里面获取
        SobUser user = userDao.findOneById(userId);
        //判空
        if (user == null) {
            return ResponseResult.FAILED("没有该用户信息");
        }
        //筛选信息,注意不能对查找出来的user类进行修改，事务没有提交，运行起来会抹了密码
        String userJson = gson.toJson(user);
        SobUser newSobUser = gson.fromJson(userJson, SobUser.class);
        newSobUser.setPassword("");
        newSobUser.setEmail("");
        newSobUser.setRegIp("");
        newSobUser.setLoginIp("");
        return ResponseResult.SUCCESS("获取成功").setData(newSobUser);
    }

    @Override
    public ResponseResult CheckEmail(String email) {
        SobUser user = userDao.findOneByEmail(email);
        return user == null ? ResponseResult.FAILED("此邮箱未注册") : ResponseResult.SUCCESS("此邮箱已注册");
    }

    @Override
    public ResponseResult CheckUserName(String userName) {
        SobUser user = userDao.findOneByUserName(userName);
        return user == null ? ResponseResult.FAILED("此用户名未注册") : ResponseResult.SUCCESS("此用户名已经注册");
    }

    /**
     * 更新用户信息
     *
     * @param request
     * @param response
     * @param sobUser
     * @param userId
     * @return
     */
    @Override
    public ResponseResult upDateUserInfo(HttpServletRequest request, HttpServletResponse response, SobUser sobUser, String userId) {
        //需要校验权限
        SobUser userFromCookie = checkSobUser(request, response);
        if (userFromCookie == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        SobUser userFromDb = userDao.findOneById(userFromCookie.getId());
        //判断当前用户id于需要修改的用户id是否一致
        if (!userFromDb.getId().equals(userId)) {
            return ResponseResult.GET_RESOURCE_FAILED();
        }
        //用户名
        String userName = sobUser.getUserName();
        if (!TextUtils.isEmpty(userName)) {
            if (userDao.findOneByUserName(userName) != null) {
                return ResponseResult.FAILED("已存在该用户名");
            }
            userFromDb.setUserName(userName);
        }
        // 头像，
        if (!TextUtils.isEmpty(sobUser.getAvatar())) {
            userFromDb.setAvatar(sobUser.getAvatar());
        }
        //签名，
        userFromDb.setSign(sobUser.getSign());
        userDao.save(userFromDb);
        //干掉redis中的token
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        redisUtils.del(Constants.User.KEY_TOKEN + tokenKey);
        return ResponseResult.SUCCESS("用户信息修改成功");
    }

    /**
     * 删除用户，不是真的删除，是修改用户状态
     * 需要管理员权限
     *
     * @param userId
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResponseResult deleteUserById(String userId, HttpServletRequest request, HttpServletResponse response) {
        //检验当前登录的用户身份
        SobUser currentUser = checkSobUser(request, response);
        if (currentUser == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        //判断角色
        if (!Constants.User.ROLE_ADMIN.equals(currentUser.getRoles())) {
            return ResponseResult.PERMISSION_FORBID();
        }
        int result = userDao.deleteUserByState(userId);
        if (result > 0) {
            return ResponseResult.SUCCESS("删除成功");
        }
        return ResponseResult.FAILED("用户不存在");
    }

    /**
     * 列出用户列表，最少一页10组数据
     * 需要管理员权限
     * @param page
     * @param size
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResponseResult listUser(int page, int size, HttpServletRequest request, HttpServletResponse response) {
        //检验当前登录的用户身份
        SobUser currentUser = checkSobUser(request, response);
        if (currentUser == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        //判断角色
        if (!Constants.User.ROLE_ADMIN.equals(currentUser.getRoles())) {
            return ResponseResult.PERMISSION_FORBID();
        }
        //可以获取用户列表
        if (page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }

        if (size < Constants.Page.MIN_SIZE) {
            size = Constants.Page.MIN_SIZE;
        }
        //根据注册日期排序
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<SobUser> all = userDao.listAllUserNoPassWord(pageable);
        return ResponseResult.SUCCESS("获取用户列表成功").setData(all);
    }

    private SobUser parseByTokenKey(String tokenKey) {
        String token = (String) redisUtils.get(Constants.User.KEY_TOKEN + tokenKey);
        if (token != null) {
            try {
                Claims claims = JwtUtils.parseJWT(token);
                return ClaimsUtils.claims2SobUser(claims);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
