package nat.phc.blog.dao;

import nat.phc.blog.pojo.SobUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 15:10 2020/7/23
 */
public interface UserDao extends JpaRepository<SobUser,String>, JpaSpecificationExecutor<SobUser> {

    /**
     * 查找是否又对应用户名
     * @param userName
     * @return
     */
    SobUser findByUserName(String userName);

    /**
     * 查找有没有邮箱
     * @return
     */
    SobUser findByEmail(String emailAddress);
}
