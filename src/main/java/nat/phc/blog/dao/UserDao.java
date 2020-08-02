package nat.phc.blog.dao;

import nat.phc.blog.pojo.SobUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 15:10 2020/7/23
 */
public interface UserDao extends JpaRepository<SobUser,String>, JpaSpecificationExecutor<SobUser> {

    /**
     * 查找是否又对应用户名
     *
     * @param userName
     * @return
     */
    SobUser findOneByUserName(String userName);

    /**
     * 查找有没有邮箱
     *
     * @return
     */
    SobUser findOneByEmail(String emailAddress);


    SobUser findOneById(String id);


    @Modifying
    @Query(nativeQuery = true, value = "UPDATE `tb_user` SET `state` = '0' WHERE `id`= ? ")
    int deleteUserByState(String userId);



    @Query(value = "select new SobUser(u.id, u.userName,u.roles,u.avatar, u.email,u.sign, u.state,u.regIp,u.loginIp,u.createTime, u.updateTime) from SobUser as u")
    Page<SobUser> listAllUserNoPassWord(Pageable pageable);
}
