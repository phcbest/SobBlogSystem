import org.springframework.util.DigestUtils;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 14:08 2020/7/27
 */
public class TestCreateJwtMd5 {
    public static void main(String[] args) {
        String s = DigestUtils.md5DigestAsHex("PhCBeSt[];',./9h923hs8sBU98j937hbBSS(#@$*@H3JAB3A6JK5BH".getBytes());
        System.out.println(s);
    }
}
