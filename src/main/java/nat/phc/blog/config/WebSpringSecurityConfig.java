package nat.phc.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * 这里是spring的安全配置
 * 使用rbac模型
 * role-base-access-control，基于角色的访问控制
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 11:55 2020/7/24
 */
@Configuration
@EnableWebSecurity
public class WebSpringSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 安全配置类
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //所有都放行
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .and().csrf().disable();
    }
}
