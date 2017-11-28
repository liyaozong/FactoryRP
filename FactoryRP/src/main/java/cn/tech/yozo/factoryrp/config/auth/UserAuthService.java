package cn.tech.yozo.factoryrp.config.auth;

import cn.tech.yozo.factoryrp.entity.User;
import cn.tech.yozo.factoryrp.service.AuthorizationService;
import cn.tech.yozo.factoryrp.utils.CheckParam;
import cn.tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

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
     * @param token
     * @return
     */
    public Long getCurrentUserId(String token){

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

        if(CheckParam.isNull(authUser)){
            return authUser.getCorporateIdentify();
        }
        return null;
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



}