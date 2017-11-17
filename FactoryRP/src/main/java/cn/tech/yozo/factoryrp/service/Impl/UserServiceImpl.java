package cn.tech.yozo.factoryrp.service.Impl;

import cn.tech.yozo.factoryrp.repository.UserRepository;
import cn.tech.yozo.factoryrp.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description 用户相关服务
 */
@Service("userService")
public class UserServiceImpl implements UserService{


    @Resource
    private UserRepository userRepository;




}
