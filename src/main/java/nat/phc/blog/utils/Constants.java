package nat.phc.blog.utils;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 14:47 2020/7/23
 */
public interface Constants {

    int DEFAULT_SIZE = 30;

    /**
     *
     */
    interface TimeValue {
        //单位秒
        int MIN = 60;
        int HOUR = 60 * MIN;
        int DAY = 24 * HOUR;
        int WEEK = 7 * DAY;
        long HOUR_2_S = HOUR * 2;
        //单位毫秒
        int MS = 1000;

        long MONTH_MS = DAY * 30L * MS;
        long HOUR_2_MS = HOUR * 2 * MS;

    }

    interface User {
        String ROLE_ADMIN = "role_admin";
        String ROLE_NORMAL = "role_normal";
        String DEFAULT_ADMIN_AVATAR = "https://s1.ax1x.com/2020/07/23/ULBMBF.th.jpg";
        String DEFAULT_USER_AVATAR = "https://s1.ax1x.com/2020/07/26/a9YifH.th.jpg";
        String DEFAULT_STATE = "1";
        String KEY_CAPTCHA_CONTENT = "key_captcha_content_";
        String KEY_EMAIL_CODE_CONTENT = "key_email_code_content_";
        String KEY_EMAIL_SEND_IP = "key_email_send_ip_";
        String KEY_EMAIL_SEND_ADDRESS = "key_email_send_address_";
        String KEY_TOKEN = "key_token_";
        String COOKIE_TOKEN_KEY = "phc_blog_login_token";
    }

    interface Page {
        int DEFAULT_PAGE = 1;
        int MIN_SIZE = 10;
    }

    interface Settings {
        String MANAGER_ACCOUNT_INIT_STATE = "manager_account_init_state";
    }

}
