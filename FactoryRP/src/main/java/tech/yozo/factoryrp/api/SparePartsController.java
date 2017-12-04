package tech.yozo.factoryrp.api;

import tech.yozo.factoryrp.entity.SpareParts;
import tech.yozo.factoryrp.service.SparePartsService;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.SparePartsAddReq;
import tech.yozo.factoryrp.vo.req.SparePartsQueryReq;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;
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
 * @time 2017/11/28
 * @description
 */
@RestController
@RequestMapping(value = "api/spareParts")
@Api(description = "备件相关服务相关接口")
public class SparePartsController extends BaseController{


    @Resource
    private SparePartsService sparePartsService;


    /**
     * 新增备件
     * @param sparePartsReq
     * @return
     */
    @ApiOperation(value = "新增备件",notes = "新增备件",httpMethod = "POST")
    @PostMapping("/addSpareParts")
    @ApiImplicitParam(dataType = "SparePartsAddReq" ,name = "sparePartsReq", paramType = "VO" ,
            value = "新增备件",required = true)
    public ApiResponse<SparePartsResp> addSpareParts(@Valid @RequestBody SparePartsAddReq sparePartsReq){
        return apiResponse(sparePartsService.addSpareParts(sparePartsReq));
    }

    /**
     * 新增备件
     * @param sparePartsQueryReq
     * @return
     */
    @ApiOperation(value = "根据条件分页查询备件",notes = "根据条件分页查询备件",httpMethod = "POST")
    @PostMapping("/findByPage")
    @ApiImplicitParam(dataType = "SparePartsQueryReq" ,name = "sparePartsQueryReq", paramType = "VO" ,
            value = "根据条件分页查询",required = true)
    public ApiResponse<SpareParts> findByPage(@Valid @RequestBody SparePartsQueryReq sparePartsQueryReq){
        return apiResponse(sparePartsService.findByPage(sparePartsQueryReq));
    }

}
