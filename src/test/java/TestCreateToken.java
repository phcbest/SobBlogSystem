import nat.phc.blog.utils.Constants;
import nat.phc.blog.utils.JwtUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 13:59 2020/7/27
 */
public class TestCreateToken {
    public static void main(String[] args) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("id","736294346722115584");
        claims.put("user_name","admin");
        claims.put("roles","role_admin");
        claims.put("avatar","https://s1.ax1x.com/2020/07/23/ULBMBF.th.jpg");
        claims.put("email","phcbest2017@outlook.com");
//        4a4e13d071e99b87e166e1860e4f17c9
        String token = JwtUtils.createRefreshToken("123546", Constants.TimeValue.MONTH_MS);
        System.out.println(token);
    }

}
