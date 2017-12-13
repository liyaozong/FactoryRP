import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import tech.yozo.factoryrp.WebApplication;
import tech.yozo.factoryrp.repository.DeviceParameterDictionaryRepository;

import javax.annotation.Resource;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/13
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = WebApplication .class) // 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class Test {


    @Resource
    private DeviceParameterDictionaryRepository deviceParameterDictionaryRepository;

   @org.junit.Test
    public void   findMax(){

    }

}
