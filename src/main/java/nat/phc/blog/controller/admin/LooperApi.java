package nat.phc.blog.controller.admin;

import nat.phc.blog.pojo.Looper;
import nat.phc.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 18:42 2020/7/21
 */
@RestController
@RequestMapping("/admin/loop")
public class LooperApi {

    @PostMapping
    public ResponseResult addLoop(@RequestBody Looper looper) {
        return null;
    }

    @DeleteMapping("/{loopId}")
    public ResponseResult deleteLoop(@PathVariable("loopId") String loopId) {
        return null;
    }

    @PutMapping("/{loopId}")
    public ResponseResult upDateLoop(@PathVariable("loopId") String loopId) {
        return null;
    }

    @GetMapping("/{loopId}")
    public ResponseResult getLooper(@PathVariable("loopId") String loopId) {
        return null;
    }

    @GetMapping("/list")
    public ResponseResult listLoop(@RequestParam("page") int page, @RequestParam("size") int size) {
        return null;
    }
}
