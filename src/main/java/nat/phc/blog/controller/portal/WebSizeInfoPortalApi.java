package nat.phc.blog.controller.portal;

import nat.phc.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 19:22 2020/7/22
 */
@RestController
@RequestMapping("/portal/web_size_info")
public class WebSizeInfoPortalApi {

    @GetMapping("/categories")
    public ResponseResult getCategories(){
        return null;
    }
    @GetMapping("/title")
    public ResponseResult getWebSizeTitle(){
        return null;
    }
    @GetMapping("/view_count")
    public ResponseResult getWebSizeViewCount(){
        return null;
    }
    @GetMapping("/seo")
    public ResponseResult getWebSizeSeoInfo(){
        return null;
    }
    @GetMapping("/loop")
    public ResponseResult getLoops(){
        return null;
    }
    @GetMapping("/friend_link")
    public ResponseResult getFriendLink(){
        return null;
    }
}
