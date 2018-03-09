package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.validation.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * OSS文件批量删除接口
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/9
 * @description
 */
@ApiModel
@Data
public class BatchDeleteOSSItemReq implements Serializable {


    /**
     * 文件ID集合
     */
    @ApiModelProperty(value = "文件ID集合",required = true,notes = "文件ID集合",example = "List")
    @NotEmpty(message = "")
    private List<String> itemList;

}
