package nat.phc.blog.controller.portal;

import nat.phc.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 15:50 2020/7/22
 */

@RestController
@RequestMapping("/portal/article")
public class ArticlePortalApi {

    @GetMapping("/list/{page}/{size}")
    public ResponseResult listArticle(@PathVariable("page") int page,
                                      @PathVariable("size") int size) {
        return null;
    }

    @GetMapping("/list/{categoryId}/{page}/{size}")
    public ResponseResult listArticleByCategoryId(@PathVariable("categoryId") int categoryId,
                                                  @PathVariable("page") int page,
                                                  @PathVariable("size") int size) {
        return null;
    }

    @GetMapping("/{articleId}")
    public ResponseResult getArticleDetail(@PathVariable("articleId") String articleId){
        return null;
    }

    @GetMapping("/recommend/{articleId}")
    public ResponseResult getRecommendArticle(@PathVariable("articleId") String articleId){
        return null;
    }
}
