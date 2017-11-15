package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.vo.req.TestVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 测试前端控制器
 */
@RestController
@RequestMapping(value = "api/test")
@Api(description = "测试接口名称")
public class TestController  extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

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
    public TestVo testAPI(@RequestBody TestVo testVo){
        logger.info("用户姓名123");
        return testVo;
    }

   /* @ApiOperation(value = "测试API接口",notes = "测试API",httpMethod = "POST")
    @ApiImplicitParam(dataType = "TestVo" ,name = "testVo", value = "测试API",required = true)*/


}
