import nat.phc.blog.utils.EmailSender;

import javax.mail.MessagingException;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 16:23 2020/7/24
 */
public class TestEmailSender {
    //发送邮件
    public static void main(String[] args) throws MessagingException {
        EmailSender.subject("测试邮件发送")
                .from("PHC博客服务器")
                .text("这是发送的内容：ab12rf")
                .to("1839233520@qq.com")
                .send();
    }

}
