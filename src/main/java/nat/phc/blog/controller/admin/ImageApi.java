package nat.phc.blog.controller.admin;

import nat.phc.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 18:42 2020/7/21
 */
@RestController
@RequestMapping("/admin/image")
public class ImageApi {

    /**
     * 上传图片
     *
     * @return
     */
    @PostMapping
    public ResponseResult upLoadImage() {
        return null;
    }

    @DeleteMapping("/{imageId}")
    public ResponseResult deleteImage(@PathVariable("imageId") String imageId) {
        return null;
    }

    @PutMapping("/{imageId}")
    public ResponseResult updateImage(@PathVariable("imageId") String imageId) {
        return null;
    }

    @GetMapping("/{imageId}")
    public ResponseResult getImage(@PathVariable("imageId") String imageId) {
        return null;
    }

    @GetMapping("/list")
    public ResponseResult listImages(@RequestParam("page") int page, @RequestParam("size") int size) {
        return null;
    }


}
