package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.entity.RepairGroup;
import cn.tech.yozo.factoryrp.service.RepairGroupService;
import cn.tech.yozo.factoryrp.vo.base.ApiResponse;
import com.alibaba.druid.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/repairGroup")
@Api(description = "维修工段／班组接口")
public class RepairGroupController extends BaseController{
    @Autowired
    RepairGroupService repairGroupService;

    @RequestMapping("list")
    @ApiOperation(value = "查询维修工段/班组列表",httpMethod = "GET")
    public ApiResponse<List<RepairGroup>> list(){
        return apiResponse(repairGroupService.listAll());
    }

    @RequestMapping("add")
    @ApiOperation(value = "添加维修工段/班组列表",httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",dataType = "String",name = "code",
            value = "编码",required = true,defaultValue = "001"),@ApiImplicitParam(paramType = "query",dataType = "String",name = "name",
            value = "名称",required = true,defaultValue = "主机维修"),@ApiImplicitParam(paramType = "query",dataType = "Long",name = "corporateIdentify",
            value = "企业唯一标识",required = true,defaultValue = "111")})
    public ApiResponse<RepairGroup> add(String code,String name,Long corporateIdentify){
        return apiResponse(repairGroupService.add(code,name,corporateIdentify));
    }

    @ApiOperation(value = "批量删除维修工段/班组",notes = "批量删除维修工段/班组",httpMethod = "GET")
    @RequestMapping("delete")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "String",name = "ids",
            value = "需要删除维修工段/班组主键集合，逗号分割",required = true,defaultValue = "1,2"))
    public void delete(String ids){
        if (!StringUtils.isEmpty(ids)){
            String[] idsArray = ids.split(",");
            List<Long> nd = new ArrayList<>();
            for (String id : idsArray){
                nd.add(Long.valueOf(id));
            }
            if (CollectionUtils.isNotEmpty(nd)){
                repairGroupService.deleteRgs(nd);
            }
        }
    }
    }
