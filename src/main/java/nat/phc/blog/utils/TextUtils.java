package nat.phc.blog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 11:59 2020/7/23
 */
public class TextUtils {

    public static final String regEx = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";


    public static boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }

    public static Boolean isEmailAddressOk(String emailAddress){
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(emailAddress);
        return m.matches();
    }
}
