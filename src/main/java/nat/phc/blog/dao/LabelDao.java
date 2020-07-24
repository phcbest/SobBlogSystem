package nat.phc.blog.dao;

import nat.phc.blog.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 21:27 2020/7/23
 */
public interface LabelDao extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label> {

    @Modifying
    int deleteOneById(String id);

    @Modifying
    @Query(value="DELETE FROM `tb_labels` WHERE id = ?",nativeQuery=true)
    int customDeleteLabelById(String id);


    Label findOneById(String id);
}
