package cn.tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/21
 * @description
 */
@ApiModel
public class MenuQueryResp implements Serializable {


    private static final long serialVersionUID = -5433886703379728535L;
    /**
     * 菜单访问URL
     */
    @ApiModelProperty(value = "菜单id",required = true,notes = "菜单id",example = "1")
    private Long id;

    /**
     * 菜单访问URL
     */
    @ApiModelProperty(value = "菜单访问URL",required = true,notes = "菜单访问URL",example = "/api/user/list")
    private String url;


    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称",required = true,notes = "菜单名称",example = "菜单A")
    private String name;


    /**
     * 菜单排序号
     */
    @ApiModelProperty(value = "菜单排序号",required = true,notes = "菜单排序号",example = "1")
    private Integer orderNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
