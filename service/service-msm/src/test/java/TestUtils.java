import com.luobin.msmservice.utils.MailUtil163;
import org.junit.Test;

/*************************
 *@author : YIMENG
 *@date : 2022/7/6 12:02
 *************************/

public class TestUtils {
    @Test
    public static void main(String[] args) {
        boolean flag = MailUtil163.sendMail("cugbluobin@163.com", "测试邮件", "hello world111");
        if (flag) {
            System.out.println("发送成功");
        } else {
            System.out.println("发送失败");
        }
    }
}
