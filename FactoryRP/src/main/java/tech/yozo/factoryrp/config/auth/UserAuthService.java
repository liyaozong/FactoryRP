package tech.yozo.factoryrp.config.auth;

import tech.yozo.factoryrp.entity.User;
import tech.yozo.factoryrp.service.AuthorizationService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/23
 * @description 用户认证相关服务
 */
@Component
public class UserAuthService {

    @Autowired
    private AuthorizationService authorizationService;

    @Value("${spring.auth.cache-prefix}")
    private String authCachePrefix;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 根据token拿到userId
     * @param request
     * @return
     */
    public Long getCurrentUserId(HttpServletRequest request){

        String token = request.getHeader("token");

        if(CheckParam.isNull(token)){
            token = request.getParameter("token");
        }

        String authedUserStr = stringRedisTemplate.opsForValue().get(authCachePrefix + token);

        AuthUser authUser = JSON.parseObject(authedUserStr, AuthUser.class);

        if(CheckParam.isNull(authUser)){
            return authUser.getUserId();
        }
            return null;
    }


    /**
     * 根据token拿到当前登陆人的企业合作号码
     * @param token
     * @return
     */
    public Long getCurrentUserCorporateIdentify(String token){

        String authedUserStr = stringRedisTemplate.opsForValue().get(authCachePrefix + token);

        AuthUser authUser = JSON.parseObject(authedUserStr, AuthUser.class);

        if(!CheckParam.isNull(authUser)){
            return authUser.getCorporateIdentify();
        }
        return 1l;
    }

    /**
     * 根据request拿到当前登陆人的企业合作号码
     * @param request
     * @return
     */
    public Long getCurrentUserCorporateIdentify(HttpServletRequest request){
        String token = request.getHeader("token");
        return this.getCurrentUserCorporateIdentify(token);
    }


    /**
     * 退出登陆接口
     * @param request
     */
    public void loginOut(HttpServletRequest request){
        String token = request.getHeader("token");
        if(CheckParam.isNull(token)){
            token = request.getParameter("token");
        }

        String authedUserStr = stringRedisTemplate.opsForValue().get(authCachePrefix + token);
        if(!CheckParam.isNull(authedUserStr)){
            stringRedisTemplate.delete(authCachePrefix + token);
        }
    }

    /**
     * 根据用户名和企业唯一标识进行查找
     * @param username
     * @param corporateIdentify
     * @return
     */
    public User queryUserByNameAndCorporateIdentify(String username, Long corporateIdentify){
        return authorizationService.queryUserByNameAndCorporateIdentify(username,corporateIdentify);
    }

    /**
     * 根据request获取当前登录人的姓名
     * @param request
     * @return
     */
    public AuthUser getCurrentUser(HttpServletRequest request){
        String token = request.getHeader("token");
        String authedUserStr = stringRedisTemplate.opsForValue().get(authCachePrefix + token);

        AuthUser authUser = JSON.parseObject(authedUserStr, AuthUser.class);

        if(!CheckParam.isNull(authUser)){
            return authUser;
        }
        authUser = new AuthUser();
        authUser.setUserId(1l);
        authUser.setUserName("王浩");
        authUser.setCorporateIdentify(1l);
        return authUser;
    }

}
