package nat.phc.blog.service.impl;

import nat.phc.blog.utils.EmailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 19:42 2020/7/25
 */
@Service
public class TaskService {

    @Async
    public void sendEmailVerifyCode(String verifyCode,String emailAddress) throws Exception {
        EmailSender.sendRegisterVerifyCode(verifyCode, emailAddress);
    }
}
