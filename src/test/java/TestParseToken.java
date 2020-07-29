import io.jsonwebtoken.Claims;
import nat.phc.blog.utils.JwtUtils;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 13:59 2020/7/27
 */
public class TestParseToken {
    public static void main(String[] args) {
        Claims claims = JwtUtils.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInJvbGVzIjoicm9sZV9hZG1pbiIsImlkIjoiNzM2Mjk0MzQ2NzIyMTE1NTg0IiwiYXZhdGFyIjoiaHR0cHM6Ly9zMS5heDF4LmNvbS8yMDIwLzA3LzIzL1VMQk1CRi50aC5qcGciLCJleHAiOjE1OTU4NDA0NjAsImVtYWlsIjoicGhjYmVzdDIwMTdAb3V0bG9vay5jb20ifQ.wSXQXzA6fHp7772Z2GTzWujzxELc9sdiyfOoMVZvmn0");
        System.out.println(claims.get("id"));
        System.out.println(claims.get("user_name"));
        System.out.println(claims.get("roles"));
        System.out.println(claims.get("avatar"));
        System.out.println(claims.get("email"));

    }

}
