package nat.phc.blog.controller.admin;

import nat.phc.blog.pojo.Category;
import nat.phc.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 17:18 2020/7/21
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryAdminApi {

    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category) {
        return null;
    }

    @DeleteMapping("/{categoryId}")
    public ResponseResult deleteCategory(@PathVariable("categoryId") String categoryId) {
        return null;
    }

    @PutMapping("/{categoryId}")
    public ResponseResult updateCategory(@PathVariable("categoryId") String categoryId, @RequestBody Category category) {
        return null;
    }

    @GetMapping("/{categoryId}")
    public ResponseResult getCategory(@PathVariable("categoryId") String categoryId) {
        return null;
    }

    @DeleteMapping("/list")
    public ResponseResult listCategories(@RequestParam("page") int page, @RequestParam("size") int size) {
        return null;
    }


}
