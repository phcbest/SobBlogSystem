package nat.phc.blog.dao;

import nat.phc.blog.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @Author: PengHaiChen
 * @Description: 继承的第二个类是规范执行器，继承了这个类就可以用写方法的样式写sql语句
 * @Date: Create in 11:04 2020/7/28
 */
public interface CommentDao extends JpaRepository<Comment,String>, JpaSpecificationExecutor<Comment> {
}
