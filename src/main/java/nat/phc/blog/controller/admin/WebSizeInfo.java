package nat.phc.blog.controller.admin;

import nat.phc.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 18:42 2020/7/21
 */
@RestController
@RequestMapping("/admin/web_size_info")
public class WebSizeInfo {

    @GetMapping("/title")
    public ResponseResult getWebSizeTitle() {
        return null;
    }

}
