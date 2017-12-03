package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.entity.Role;
import cn.tech.yozo.factoryrp.repository.RoleRepository;
import cn.tech.yozo.factoryrp.repository.UserRepository;
import cn.tech.yozo.factoryrp.vo.base.ApiResponse;
import cn.tech.yozo.factoryrp.vo.req.TestVo;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试前端控制器
 */
@RestController
@RequestMapping(value = "/test")
@Api(description = "测试接口名称")
public class TestController  extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Resource
    private RoleRepository roleRepository;


    @Resource
    private UserRepository userRepository;

    /**
     * 测试API
     *  @ApiImplicitParam 接口参数说明
     *  @ApiOperation 接口说明
     *
     *
     * @return
     */
    @ApiOperation(value = "测试API接口",notes = "测试API",httpMethod = "POST")
    //@ApiImplicitParam(dataType = "TestVo大赛打啊打" ,name = "testVo", value = "测试API",required = true)
    @RequestMapping(value = "/testAPI",method = RequestMethod.POST)
    public ApiResponse<TestVo> testAPI(@RequestBody TestVo testVo){

        List<Role> all = roleRepository.findAll();

        System.out.println(JSON.toJSONString(all));

        List<Role> all2 = roleRepository.findAll();

        System.out.println(JSON.toJSONString(all2));


        logger.info("用户姓名123");
        return apiResponse(testVo);
    }

   /* @ApiOperation(value = "测试API接口",notes = "测试API",httpMethod = "POST")
    @ApiImplicitParam(dataType = "TestVo" ,name = "testVo", value = "测试API",required = true)*/


}
