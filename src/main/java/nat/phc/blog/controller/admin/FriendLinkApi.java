package nat.phc.blog.controller.admin;

import nat.phc.blog.pojo.FriendLink;
import nat.phc.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 18:42 2020/7/21
 */
@RestController
@RequestMapping("/admin/friend_link")
public class FriendLinkApi {

    @PostMapping
    public ResponseResult addFriendsLink(@RequestBody FriendLink friendLink) {

        return null;
    }

    @DeleteMapping("/{friendLinkId}")
    public ResponseResult deleteFriendLink(@PathVariable("friendLinkId") String friendLinkId) {
        return null;
    }

    @PutMapping("/{friendLinkId}")
    public ResponseResult updateFriendLink(@PathVariable("friendLinkId") String friendLinkId) {
        return null;
    }

    @GetMapping("/{friendLinkId}")
    public ResponseResult getFriendLink(@PathVariable("friendLinkId") String friendLinkId) {
        return null;
    }

    @GetMapping("/list")
    public ResponseResult listFriendLink(@RequestParam("page") int page, @RequestParam("size") int size) {
        return null;
    }

}
