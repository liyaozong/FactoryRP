package cn.tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 测试VO
 */
@ApiModel
public class TestVo implements Serializable{
    private static final long serialVersionUID = 2220046886717838938L;

    @ApiModelProperty(value = "用户姓名",required = true,notes = "用户姓名",example = "张三12132131212")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
