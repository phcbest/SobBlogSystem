package nat.phc.blog.controller.portal;

import nat.phc.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 19:44 2020/7/22
 */
@RestController
@RequestMapping("/portal/search")
public class SearchPortalApi {

    @GetMapping
    public ResponseResult doSearch(@RequestParam("keyword") String keyword, @RequestParam("page") int page) {
        return null;
    }
}
