package nat.phc.blog.dao;

import nat.phc.blog.pojo.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 15:23 2020/7/23
 */
public interface SettingsDao extends JpaRepository<Setting,String>, JpaSpecificationExecutor<String> {
    /**
     * 找到账号通过key
     * @param key
     * @return
     */
    Setting findOneByKey(String key);
}
