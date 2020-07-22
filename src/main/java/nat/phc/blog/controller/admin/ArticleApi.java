package nat.phc.blog.controller.admin;

import nat.phc.blog.pojo.Article;
import nat.phc.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 18:43 2020/7/21
 */
@RestController
@RequestMapping("/admin/article")
public class ArticleApi {

    @PostMapping
    public ResponseResult addArticle(@RequestBody Article Article) {
        return null;
    }

    @DeleteMapping("/{articleId}")
    public ResponseResult deleteArticle(@PathVariable("articleId") String articleId) {
        return null;
    }

    @PutMapping("/{articleId}")
    public ResponseResult updateArticle(@PathVariable("articleId") String articleId) {
        return null;
    }

    @GetMapping("/{articleId}")
    public ResponseResult getArticle(@PathVariable("articleId") String articleId) {
        return null;
    }

    @GetMapping("/list")
    public ResponseResult listArticle(@RequestParam("page") int page, @RequestParam("size") int size) {
        return null;
    }

    @PutMapping("/sate/{articleId}/{state}")
    public ResponseResult updateArticleState(@PathVariable("state") String state, @PathVariable("articleId") String articleId) {
        return null;
    }

    @PutMapping("/top/{articleId}")
    public ResponseResult updateArticleState(@PathVariable("articleId") String articleId) {
        return null;
    }
}
