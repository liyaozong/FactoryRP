package tech.yozo.factoryrp.vo.req;

import tech.yozo.factoryrp.vo.base.ApiCorporateIdentifyRequest;
import tech.yozo.factoryrp.vo.validation.NotEmpty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/21
 * @description
 */
@Data
@ApiModel
public class MenuReq extends ApiCorporateIdentifyRequest implements Serializable {

    private static final long serialVersionUID = 1857845075285025045L;

    /**
     * 父级菜单Id
     * 若为0则为一级菜单
     */
    @ApiModelProperty(value = "父级菜单Id,若为0则为一级菜单",required = true,notes = "父级菜单Id",example = "5")
    //@NotEmpty(message = "父级菜单Id不能为空")
    private Long parentId;

    /**
     * 菜单访问URL
     */
    @ApiModelProperty(value = "菜单访问URL",required = true,notes = "菜单访问URL",example = "/api/user/list")
    @NotEmpty(message = "菜单访问URL不能为空")
    private String url;


    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称",required = true,notes = "菜单名称",example = "菜单A")
    @NotEmpty(message = "菜单名称不能为空")
    private String name;


    /**
     * 菜单排序号
     */
    @ApiModelProperty(value = "菜单排序号",required = true,notes = "菜单排序号",example = "1")
    //@NotEmpty(message = "菜单排序号不能为空")
    private Integer orderNumber;


    /**
     * 菜单备注
     */
    @ApiModelProperty(value = "菜单备注",required = true,notes = "菜单备注",example = "阿里巴巴集团某菜单")
    @NotEmpty(message = "菜单备注不能为空")
    private String remark;

}
