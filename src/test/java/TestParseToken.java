import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import nat.phc.blog.utils.JwtUtils;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 13:59 2020/7/27
 */
public class TestParseToken {
    public static void main(String[] args) {
        String token =
                "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3MzYyOTQzNDY3MjIxMTU1ODQiLCJpYXQiOjE1OTYyMDQ5NDF9.Q4xLM3khNsNmbUhKttusbDqf4OsBYkSwZRJc1Q9yLDU";
        Claims claims = JwtUtils.parseJWT(token);
        String x = Jwts.parser().setSigningKey("f4efef5d29f31fda04fae054d2edb133").parse(token).toString();
        System.out.println(x);
        System.out.println(claims.toString());

    }

}
