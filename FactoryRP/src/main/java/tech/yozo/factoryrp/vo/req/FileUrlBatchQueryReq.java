package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.BaseRequest;

import java.io.Serializable;
import java.util.List;

/**
 * 文件访问URL批量查询请求对象
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/9
 * @description
 */
@ApiModel(value = "文件访问URL批量查询请求Object")
@Data
public class FileUrlBatchQueryReq extends BaseRequest implements Serializable {


    /**
     * 文件ID集合
     */
    @ApiModelProperty(value = "文件ID集合",required = true,notes = "文件ID集合",example = "List")
    private List<String> fileIdList;

}
