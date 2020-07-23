package nat.phc.blog.controller.portal;

import nat.phc.blog.pojo.Comment;
import nat.phc.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 11:42 2020/7/22
 */
@RestController
@RequestMapping("/portal/comment")
public class CommentPortalApi {

    @PostMapping
    public ResponseResult postComment(@RequestBody Comment comment) {
        return null;
    }

    @DeleteMapping("/{commentId}")
    public ResponseResult deleteComment(@PathVariable("commentId") String commentId) {
        return null;
    }

    @GetMapping("list/{articleId}")
    public ResponseResult listComments(@PathVariable("articleId") String articleId) {
        return null;
    }
}
