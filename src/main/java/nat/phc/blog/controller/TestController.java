package nat.phc.blog.controller;

import lombok.extern.slf4j.Slf4j;
import nat.phc.blog.dao.LabelDao;
import nat.phc.blog.pojo.Label;
import nat.phc.blog.pojo.testPojo;
import nat.phc.blog.pojo.testUser;
import nat.phc.blog.response.ResponseResult;
import nat.phc.blog.response.ResponseState;
import nat.phc.blog.utils.Constants;
import nat.phc.blog.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Date;

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
        if (size<=0){
            size= Constants.DEFAULT_SIZE;
        }
        PageRequest pageable = PageRequest.of(page - 1, size);
        Page<Label> result = labelDao.findAll(pageable);
        return ResponseResult.SUCCESS("取回分页数据成功").setData(result);
    }
}
