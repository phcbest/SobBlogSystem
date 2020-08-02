package nat.phc.blog.controller;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import nat.phc.blog.dao.CommentDao;
import nat.phc.blog.dao.LabelDao;
import nat.phc.blog.pojo.*;
import nat.phc.blog.response.ResponseResult;
import nat.phc.blog.response.ResponseState;
import nat.phc.blog.service.IUserService;
import nat.phc.blog.utils.Constants;
import nat.phc.blog.utils.CookieUtils;
import nat.phc.blog.utils.IdWorker;
import nat.phc.blog.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author PengHaiChen
 */
@Slf4j
@Transactional
@RestController//这个注解将类自动填入工厂并且调用api能返回参数
@RequestMapping("/test")
public class TestController {

    @Autowired
    private LabelDao labelDao;


    @Autowired
    private IdWorker idWorker;

    @GetMapping("/hello")
    public ResponseResult helloWorld() {
        String captchaContent = (String)redisUtils.get(Constants.User.KEY_CAPTCHA_CONTENT+"123456");
        log.info("captchaContent==========>"+captchaContent);
        return ResponseResult.SUCCESS().setData(captchaContent);
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

    @PostMapping("/label")
    public ResponseResult 添加表(@RequestBody Label label) {
        //权限校验
        //判断数据是否有效
        //补全数据
        label.setId(String.valueOf(idWorker.nextId()));
        label.setCreateTime(new Date());
        label.setUpdateTime(new Date());
        //保存数据
        labelDao.save(label);
        return ResponseResult.SUCCESS("测试标签添加成功");
    }

    @DeleteMapping("/label/{labelId}")
    public ResponseResult delete(@PathVariable("labelId") String labelId) {
        int deleteResult = labelDao.customDeleteLabelById(labelId);
        if (deleteResult > 0) {
            return ResponseResult.FAILED("标签删除成功");
        } else {
            return ResponseResult.FAILED("删除标签失败");
        }
    }

    @PutMapping("/label/{labelId}")
    public ResponseResult upDateLabel(@PathVariable("labelId") String labelId, @RequestBody Label label) {
        Label dbLabel = labelDao.findOneById(labelId);
        if (label == null) {
            return ResponseResult.FAILED("标签不存在");
        }
        dbLabel.setName(label.getName());
        dbLabel.setCount(label.getCount());
        dbLabel.setUpdateTime(new Date());
        labelDao.save(dbLabel);
        return ResponseResult.SUCCESS("修改成功");
    }

    @GetMapping("/label/{labelId}")
    public ResponseResult getLabelById(@PathVariable("labelId") String labelId) {
        Label dbLabel = labelDao.findOneById(labelId);
        if (dbLabel == null) {
            return ResponseResult.FAILED("标签不存在");
        }
        return ResponseResult.SUCCESS("查询成功").setData(dbLabel);
    }

    @GetMapping("/label/list/{page}/{size}")
    public ResponseResult listLabels(@PathVariable int page, @PathVariable int size) {
        if (page < 1) {
            page = 1;
        }
        if (size <= 0) {
            size = Constants.DEFAULT_SIZE;
        }
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageable = PageRequest.of(page - 1, size, sort);
        Page<Label> result = labelDao.findAll(pageable);
        return ResponseResult.SUCCESS("取回分页数据成功").setData(result);
    }

    @GetMapping("/label/search")
    public ResponseResult doLabelSearch(@RequestParam("keyword") String keyword, @RequestParam("count") int count) {
        List<Label> all = labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate namePre = cb.like(root.get("name").as(String.class), "%" + keyword + "%");
                Predicate countPre = cb.equal(root.get("count").as(Integer.class), count);
                return cb.and(namePre, countPre);
            }
        });
        if (all.size() == 0) {
            return ResponseResult.FAILED("结果为空");
        }
        return ResponseResult.SUCCESS("查找成功").setData(all);
    }

    @Autowired
    private RedisUtils redisUtils;

    //http://localhost:2333/test/captcha
    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 设置字体
//        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
        specCaptcha.setFont(Captcha.FONT_1);
        // 设置类型，纯数字、纯字母、字母数字混合
        //specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);

        String content = specCaptcha.text().toLowerCase();
        log.info("captcha content == > " + content);
        // 验证码存入session
//        request.getSession().setAttribute("captcha", content);
        //验证码存入redis//KEY_CAPTCHA_CONTENT加随机数
        redisUtils.set(Constants.User.KEY_CAPTCHA_CONTENT + "123456", content, 60 * 10);
        // 输出图片流
        specCaptcha.out(response.getOutputStream());
    }

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private IUserService userService;

    @PostMapping("/comment")
    public ResponseResult testComment(@RequestBody Comment comment, HttpServletRequest request,HttpServletResponse response) {
        String content = comment.getContent();
        log.info("评论内容===》" + content);
        //对评论身份进行验证
        String tokenKey = CookieUtils.getCookie(request,Constants.User.COOKIE_TOKEN_KEY);
        if (tokenKey == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        SobUser sobUser = userService.checkSobUser(request, response);
        if (sobUser == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        //数据填充
//        id	parent_content	article_id	content	user_id	user_avatar	user_name	state	create_time	update_time
        comment.setUserId(sobUser.getId());
        comment.setUserAvatar(sobUser.getAvatar());
        comment.setUserName(sobUser.getUserName());
        comment.setState("1");
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        comment.setContent(new Date(System.currentTimeMillis()).toString());
        comment.setId(String.valueOf(idWorker.nextId()));
        commentDao.save(comment);
        return ResponseResult.SUCCESS("评论成功");
    }
}
