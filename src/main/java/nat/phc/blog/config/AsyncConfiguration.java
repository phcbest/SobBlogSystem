package nat.phc.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 19:17 2020/7/25
 */
@Configuration
@EnableAsync
public class AsyncConfiguration {
    /**
     * 配置线程池
     * @return
     */
    @Bean
    public Executor asyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("phc_blog_task_worker-");
        executor.setQueueCapacity(30);
        return executor;
    }
}
