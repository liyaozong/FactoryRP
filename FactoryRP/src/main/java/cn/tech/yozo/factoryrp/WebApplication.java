package cn.tech.yozo.factoryrp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * SpringBoot的启动类
 * Created by Singer on 2017/7/25.
 */
@Configuration
//@EnableAutoConfiguration
@ComponentScan
@EnableAspectJAutoProxy
//@EnableCaching
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@SpringBootApplication
@EnableTransactionManagement
public class WebApplication {

    private static Logger logger = LoggerFactory.getLogger(WebApplication.class);


    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args) {
        logger.info("启动SpringBoot---------");
        SpringApplication.run(WebApplication.class, args);
    }

}
