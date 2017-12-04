/*
import WebApplication;
import Role;
import RoleRepository;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;

*/
/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
//指定SpringBoot工程的Application启动类
//支持web项目
@WebAppConfiguration
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class TestJunit {

    @Resource
    private RoleRepository roleRepository;

    @Test
    public void test(){

        List<Role> all = roleRepository.findAll();

        System.out.println(JSON.toJSONString(all));

    }

}
*/
