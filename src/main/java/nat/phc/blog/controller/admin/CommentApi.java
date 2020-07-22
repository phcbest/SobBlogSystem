package nat.phc.blog.controller.admin;

import nat.phc.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 18:42 2020/7/21
 */
@RestController
@RequestMapping("/admin/comment")
public class CommentApi {



    @DeleteMapping("/{commentId}")
    public ResponseResult deleteComment(@PathVariable("commentId") String commentId) {
        return null;
    }



    @GetMapping("/list")
    public ResponseResult listComment(@RequestParam("page") int page, @RequestParam("size") int size) {

        return null;
    }
    @PutMapping("/top/{commentId}")
    public ResponseResult topComment(@PathVariable("commentId") String commentId){
        return null;
    }
}
