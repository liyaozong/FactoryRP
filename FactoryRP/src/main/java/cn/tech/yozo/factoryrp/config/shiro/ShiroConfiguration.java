package cn.tech.yozo.factoryrp.config.shiro;

import cn.tech.yozo.factoryrp.config.redis.RedisCacheManager;
import cn.tech.yozo.factoryrp.config.redis.RedisObjectSerializer;
import cn.tech.yozo.factoryrp.config.redis.RedisSessionRepository;
import cn.tech.yozo.factoryrp.config.shiro.filter.AuthenticationFilter;
import cn.tech.yozo.factoryrp.config.shiro.filter.StatelessAuthcFilter;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description Shiro配置类
 */


@Configuration
@EnableCaching
public class ShiroConfiguration {


    //@Value("${spring.redis.host}")
    //private String host = "10.108.44.15";

    //@Value("${spring.redis.port}")
    //private int port = 6379;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Autowired
    private RedisSessionRepository redisSessionRepository;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     */
   /* @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }*/

    /**
     * HashedCredentialsMatcher，这个类是为了对密码进行编码的，
     * 防止密码在数据库里明码保存，当然在登陆认证的时候，
     * 这个类也负责对form里输入的密码进行编码。
     */
    /**StateLessShiroRealm，这是个自定义的认证类，继承自AuthorizingRealm，
     * 负责用户的认证和权限的处理，可以参考JdbcRealm的实现。
     */

    //@DependsOn("lifecycleBeanPostProcessor")
    @Bean(name = "shiroRealm")
    public StateLessShiroRealm shiroRealm() {
        StateLessShiroRealm realm = new StateLessShiroRealm();
        //realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    /*@Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }*/

    /**
     * SecurityManager，权限管理，这个类组合了登陆，登出，权限，session的处理，是个比较重要的类。
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        securityManager.setCacheManager(shiroRedisCacheManager());
        securityManager.setSessionManager(sessionManager());
        //securityManager.setSessionManager(defaultSessionManager());
        //securityManager.setSubjectFactory(subjectFactory());
        return securityManager;
    }

    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionRepository);
        sessionManager.setGlobalSessionTimeout(1800);
        sessionManager.setCacheManager(shiroRedisCacheManager());
        return sessionManager;
    }

    /**
     * cacheManager 缓存 redis实现
     *
     * @return
     */@Bean(name = "shiroRedisCacheManager")
   public RedisCacheManager shiroRedisCacheManager() {
       RedisCacheManager redisCacheManager = new RedisCacheManager();
       redisCacheManager.setRedisTemplate(redisTemplate());
       return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     *
     * @return
     */
   /* public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(1800);// 配置过期时间
        // redisManager.setTimeout(timeout);
        // redisManager.setPassword(password);
        return redisManager;
    }*/

    /**
     * 相当于调用SecurityUtils.setSecurityManager(securityManager)
     * @return
     */
    @Bean(name = "methodInvokingFactoryBean")
    public MethodInvokingFactoryBean methodInvokingFactoryBean(){
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        List<DefaultWebSecurityManager> securityManagerList = new ArrayList<>();
        securityManagerList.add(securityManager());
        methodInvokingFactoryBean.setArguments(securityManagerList.toArray());

        return methodInvokingFactoryBean;
    }


    /**
     * 注入无状态的认证Filter
     * @return
     */
    /*@Bean(name = "statelessAuthcFilter")
    public StatelessAuthcFilter statelessAuthcFilter(){
        return new StatelessAuthcFilter();
    }*/


    /**
     * 认证过滤器
     * @return
     */
    @Bean(name = "authenticationFilter")
    public AuthenticationFilter AuthenticationFilter(){
        return new AuthenticationFilter();
    }

    /**
     * 注入ShiroFilterFactoryBean
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("api/login");
        Map<String, Filter> filters = new LinkedHashMap<>();
        //filters.put("statelessAuthc",statelessAuthcFilter());
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login");
        //filters.put("logout",null);
        shiroFilterFactoryBean.setFilters(filters);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();


        //filterChainDefinitionMap.put("/logout", "logout");
        //filterChainDefinitionMap.put("/user/**", "authc,roles[ROLE_USER]");//用户为ROLE_USER 角色可以访问。由用户角色控制用户行为。
        //filterChainDefinitionMap.put("/events/**", "authc,roles[ROLE_ADMIN]");

        //filterChainDefinitionMap.put("/**", "statelessAuthc");
        filterChainDefinitionMap.put("/**", "authc");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        return shiroFilterFactoryBean;
    }


    @Bean(name = "defaultSessionManager")
    public DefaultSessionManager defaultSessionManager(){
        DefaultSessionManager defaultSessionManager = new DefaultSessionManager();
        defaultSessionManager.setSessionDAO(redisSessionRepository);
        defaultSessionManager.setCacheManager(shiroRedisCacheManager());
        return defaultSessionManager;
    }

    @Bean(name = "statelessAuthcFilter")
    public StatelessAuthcFilter statelessAuthcFilter(){
        return new StatelessAuthcFilter();
    }

    /**
     * ShiroFilterFactoryBean，是个factorybean，为了生成ShiroFilter。
     * 它主要保持了三项数据，securityManager，filters，filterChainDefinitionManager。
     */
   /* @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        Map<String, Filter> filters = new LinkedHashMap<>();
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login");
        //filters.put("logout",null);
        shiroFilterFactoryBean.setFilters(filters);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/user/**", "authc,roles[ROLE_USER]");//用户为ROLE_USER 角色可以访问。由用户角色控制用户行为。
        filterChainDefinitionMap.put("/events/**", "authc,roles[ROLE_ADMIN]");
        //filterChainDefinitionMap.put("/user/edit/**", "authc,perms[user:edit]");// 这里为了测试，固定写死的值，也可以从数据库或其他配置中读取，此处是用权限控制

        filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);


        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        return shiroFilterFactoryBean;
    }*/

    /**
     *     @ConditionalOnMissingBean
     * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }


    /**
     * AuthorizationAttributeSourceAdvisor，shiro里实现的Advisor类，
     * 内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor来拦截用以下注解的方法。
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        System.out.println("开启了Shiro注解支持");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }


   /* @Bean
    public CacheManager redisCacheManager() {
        org.springframework.data.redis.cache.RedisCacheManager cacheManager = new org.springframework.data.redis.cache.RedisCacheManager(redisTemplate());
        cacheManager.setDefaultExpiration(1800);
        return cacheManager;
    }*/

    @Bean(name = "redisTemplate")
    public RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setDefaultSerializer(new RedisObjectSerializer());

        return redisTemplate;
    }

    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(6);
        jedisPoolConfig.setMaxTotal(32);
        jedisPoolConfig.setMaxWaitMillis(15000);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(60000L);
        jedisPoolConfig.setNumTestsPerEvictionRun(3);

        return jedisPoolConfig;

    }

    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setUsePool(true);

        return jedisConnectionFactory;
    }



}
