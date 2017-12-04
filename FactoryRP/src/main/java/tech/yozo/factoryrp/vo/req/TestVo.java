package tech.yozo.factoryrp.vo.req;

import tech.yozo.factoryrp.vo.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 测试VO
 */
@ApiModel
public class TestVo extends BaseRequest implements Serializable{
    private static final long serialVersionUID = 2220046886717838938L;

    @ApiModelProperty(value = "用户姓名",required = true,notes = "用户姓名",example = "张三12132131212")
    private String username;

    private String sequence;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
