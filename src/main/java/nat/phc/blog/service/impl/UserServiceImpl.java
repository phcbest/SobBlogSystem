package nat.phc.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import nat.phc.blog.dao.SettingsDao;
import nat.phc.blog.dao.UserDao;
import nat.phc.blog.pojo.Setting;
import nat.phc.blog.pojo.SobUser;
import nat.phc.blog.response.ResponseResult;
import nat.phc.blog.service.IUserService;
import nat.phc.blog.utils.Constants;
import nat.phc.blog.utils.IdWorker;
import nat.phc.blog.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;

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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SettingsDao settingsDao;

    @Override
    public ResponseResult initManagerAccount(  SobUser sobUser, HttpServletRequest request) {
        //检查是否初始化
        Setting managerAccountState = settingsDao.findOneByKey(Constants.Settings.MANAGER_ACCOUNT_INIT_STATE);
        if (managerAccountState != null) {
            return ResponseResult.FAILED("已经初始化过了管理员账号");
        }
        //检查数据
        if (TextUtils.isEmpty(sobUser.getUser_name())) {
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
        sobUser.setAvatar(Constants.User.DEFAULT_AVATAR);
        //设置账号状态
        sobUser.setState(Constants.User.DEFAULT_STATE);
        //定影获取登录ip
        String remoteAddr = request.getRemoteAddr();
        log.info("当前ip=====》"+remoteAddr);
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
}
