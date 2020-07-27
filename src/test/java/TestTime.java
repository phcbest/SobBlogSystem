import java.util.Calendar;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 12:59 2020/7/25
 */
public class TestTime {
    public static void main(String[] args) {
//        System.out.println("当前时间"+System.currentTimeMillis());
        Calendar instance = Calendar.getInstance();
        instance.set(2100,10,13);
        long timeInMillis = instance.getTimeInMillis();
        System.out.println("当前时间"+timeInMillis+"位数"+String.valueOf(timeInMillis).length());
    }
}
