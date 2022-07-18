import com.luobin.utils.DateUtil;
import org.junit.Test;

import java.util.Date;

/*************************
 *@author : YIMENG
 *@date : 2022/7/16 16:52
 *************************/

public class DateTest {

    @Test
    public void testDate() {
        System.out.println(DateUtil.now());
        System.out.println(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
