package cn.tech.yozo.factoryrp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;


/**
 * Dubbo配置
 */
//@Configuration
//@PropertySource("classpath:dubbo-provider.properties")
//@ImportResource({ "classpath:dubbo-provider.xml" })
public class DubboConfig {


    /**
     * 注册器配置
     * @return
     */
  /*  @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("127.0.0.1:2181");
        registryConfig.setProtocol("zookeeper");

        return registryConfig;
    }*/

    /**
     * 配置dubbo应用名称
     * @return
     */
    /*@Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("SpringBoot");

        return applicationConfig;
    }*/

    /**
     * 配置监听器
     * @return
     */
   /* @Bean
    public MonitorConfig monitorConfig(){
        MonitorConfig monitorConfig = new MonitorConfig();
        monitorConfig.setProtocol("registry");

        return monitorConfig;
    }*/


   /* @Bean
    public ReferenceConfig  referenceConfig(){
        ReferenceConfig referenceConfig = new ReferenceConfig();
        referenceConfig.setMonitor(monitorConfig());

        return referenceConfig;
    }*/


  /*  @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig  = new ProtocolConfig();
        protocolConfig.setPort(20880);

        return protocolConfig;
    }*/

   /* @Bean
    public ProviderConfig providerConfig(){
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setMonitor(monitorConfig());

        return providerConfig();
    }*/

}
