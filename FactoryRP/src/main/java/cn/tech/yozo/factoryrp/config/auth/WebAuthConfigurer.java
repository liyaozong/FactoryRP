package cn.tech.yozo.factoryrp.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/23
 * @description
 */
//@EnableWebMvc
//@Configuration
//@EnableAutoConfiguration
public class WebAuthConfigurer extends WebMvcConfigurerAdapter {


    @Bean
    UserAuthService userAuthService(){
        return new UserAuthService();
    }

    @Bean
    AuthIntercepter localInterceptor() {
        return new AuthIntercepter();
    }



    @Override
   public void addInterceptors(InterceptorRegistry registry) {
        userAuthService();
        registry.addInterceptor(localInterceptor()).addPathPatterns("/api/**")
        //.excludePathPatterns("*/swagger-resources/*")
        //.excludePathPatterns("/**/swagger-ui.html")
        .excludePathPatterns("/**/*.html")
        .excludePathPatterns("/**/fonts/*")
        .excludePathPatterns("/**/*.css")
        .excludePathPatterns("/**/*.js")
        .excludePathPatterns("/**/*.png")
        .excludePathPatterns("/**/*.gif")
        .excludePathPatterns("/**/*.jpg")
        .excludePathPatterns("/**/*.jpeg")
        .excludePathPatterns("/**/*.js")
        .excludePathPatterns("/webjars/**")
        .excludePathPatterns("/**/**/testAPI/*");//暂时设置为拦截所有请求

    }
 /*.excludePathPatterns("/api/authorization/unAuthToken")
                .excludePathPatterns("/api/login")*/


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*/*")
                .allowedMethods("*")
                .maxAge(120);
    }

}
