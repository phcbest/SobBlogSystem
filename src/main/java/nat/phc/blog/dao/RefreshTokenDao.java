package nat.phc.blog.dao;

import nat.phc.blog.pojo.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 18:54 2020/7/29
 */
public interface RefreshTokenDao extends JpaRepository<RefreshToken, String>, JpaSpecificationExecutor<RefreshToken> {

    RefreshToken findOneByTokenKey(String tokenKey);

    int deleteAllByUserId(String userId);
}
