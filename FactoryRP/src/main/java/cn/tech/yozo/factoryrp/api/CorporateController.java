package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.service.CorporateService;
import cn.tech.yozo.factoryrp.vo.base.ApiResponse;
import cn.tech.yozo.factoryrp.vo.req.CorporateReq;
import cn.tech.yozo.factoryrp.vo.resp.corporate.CorporateResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/20
 * @description
 */
@RestController
@RequestMapping(value = "api/corporate")
@Api(description = "企业相关接口相关接口")
public class CorporateController extends BaseController{


    @Resource
    private CorporateService corporateService;

    /**
     * 新增企业
     * @param corporateReq
     * @return
     */
    @ApiOperation(value = "新增企业",notes = "新增企业",httpMethod = "POST")
    @PostMapping("/addCorporate")
            @ApiImplicitParam(dataType = "CorporateReq" ,name = "corporateReq", paramType = "VO" ,
                    value = "企业新增相关信息",required = true)
    public ApiResponse<CorporateResp> queryRolesByorporateIdentify(@Valid @RequestBody CorporateReq corporateReq){
        return apiResponse(corporateReq,corporateService.addCorporate(corporateReq));
    }


}
