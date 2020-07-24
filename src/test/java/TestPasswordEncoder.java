import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 14:03 2020/7/24
 */
public class TestPasswordEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        //数据库密文
//        $2a$10$/kgXUVWmnWR8IoLeQzabfu8DnUbigFnupAjkfRsG0NmVbKpWrejk6
//        $2a$10$/yz.xrx5vNkGiWUCg8XHceWi3e4.2aSYepiu9i4to4SR2/OiOexvy
        String originalPassword = "88888888";
        boolean matches = passwordEncoder.matches(originalPassword, "$2a$10$CBb0wlJY/3KEpk5dwOQrr.5cF/oPWHEEueZV3RtyNoKynLErBLuE.");
        System.out.println(matches);
    }
}
