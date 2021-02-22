package nat.phc.blog.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 14:26 2021/2/22
 */
@Slf4j
@Component
public class TestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();

        log.info(servletPath);
        //使用正则来匹配请求url
        if (servletPath.matches(".*interceptor.*")) {
            //在这里匹配到拦截器后可以进行权限判断
            log.info("匹配到拦截器");
        } else {
            log.info("没有匹配到拦截器");

        }
        return super.preHandle(request, response, handler);
    }
}
