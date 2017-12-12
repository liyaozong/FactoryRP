package tech.yozo.factoryrp.api;

import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.entity.SpareParts;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.service.SparePartsService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.SparePartsAddReq;
import tech.yozo.factoryrp.vo.req.SparePartsQueryReq;
import tech.yozo.factoryrp.vo.resp.sparepars.DeviceSparesMobileResp;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
    @Autowired
    private UserAuthService userAuthService;

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
     * 查询备件
     * @param sparePartsQueryReq
     * @return
     */
    @ApiOperation(value = "根据条件分页查询备件-WEB",notes = "根据条件分页查询备件",httpMethod = "POST")
    @PostMapping("/findByPage")
    @ApiImplicitParam(dataType = "SparePartsQueryReq" ,name = "sparePartsQueryReq", paramType = "VO" ,
            value = "根据条件分页查询",required = true)
    public ApiResponse<SpareParts> findByPage(@Valid @RequestBody SparePartsQueryReq sparePartsQueryReq){
        return apiResponse(sparePartsService.findByPage(sparePartsQueryReq));
    }


    /**
     * 手机端查询备件信息
     * @param sparePartsQueryReq
     * @return
     */
    @ApiOperation(value = "手机端查询备件信息-MOBILE",notes = "手机端查询备件信息-MOBILE",httpMethod = "POST")
    @PostMapping("/queryMobileDeviceSpares")
    @ApiImplicitParam(dataType = "SparePartsQueryReq" ,name = "sparePartsQueryReq", paramType = "VO" ,
            value = "手机端查询备件信息",required = true)
    public ApiResponse<Pagination<DeviceSparesMobileResp>> queryMobileDeviceSparesMobileResp(@Valid @RequestBody SparePartsQueryReq sparePartsQueryReq){
        return apiResponse(sparePartsService.queryMobileDeviceSpares(sparePartsQueryReq));
    }

    /**
     * 根据备件id删除备件
     * @param id
     */
    @ApiOperation(value = "根据备件id删除备件",notes = "根据备件id删除备件",httpMethod = "GET")
    @GetMapping("/deleteSparePartsById")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Long" ,name = "id", paramType = "query" ,
                    value = "id",required = true,defaultValue = "1"),
    })
    public ApiResponse deleteSparePartsById(@RequestParam(value="id",required = true,defaultValue = "1") Long id
            ,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        sparePartsService.deleteSparePartsById(id,corporateIdentify);
        return apiResponse();
    }

    /**
     * 批量删除备件信息
     * @param ids
     */
    @RequestMapping("/deleteSparePartsByIds")
    @ApiOperation(value = "批量删除备件信息--WEB",notes = "批量删除备件信息--WEB",httpMethod = "GET")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "String",name = "ids",
            value = "需要删除的主键，多个主键用逗号分割",required = true,defaultValue = "1,2"))
    public ApiResponse deleteSparePartsByIds(String ids,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        List<Long> idList = new ArrayList<>();
        if (!CheckParam.isNull(ids)){
            String[] idArray =ids.split(",");
            if (idArray.length>0){
                Stream.of(idArray).forEach(s1 -> {
                        idList.add(Long.parseLong(s1));
                });
            }
        }
        sparePartsService.deleteSparePartsByIds(idList,corporateIdentify);
        return apiResponse();
    }

}
