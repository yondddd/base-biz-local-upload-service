import com.ruijing.base.local.upload.AppRootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * spring boot 测试基本类，业务测试类可以继承此类使用
 * @author 锐竞-模板生成工具
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRootApplication.class)
@WebAppConfiguration
public class ApplicationBaseTest {

    @Test
    public void testHelloWorld() {
        System.out.println("hello world");
    }
}
