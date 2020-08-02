package nat.phc.blog;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import nat.phc.blog.utils.IdWorker;
import nat.phc.blog.utils.RedisUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Random;

/**
 * @author PengHaiChen
 */
@Slf4j
@EnableSwagger2
@SpringBootApplication
public class BlogApplication {
    public static void main(String[] args) {
        log.info("application_Run........");
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    public IdWorker createId(){
        return new IdWorker(0,0);
    }

    @Bean
    public BCryptPasswordEncoder createPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RedisUtils createRedisUtils() {
        return new RedisUtils();
    }

    @Bean
    public Random createRandom() {
        return new Random();
    }

    @Bean
    public Gson createGson() {
        return new Gson();
    }
}

