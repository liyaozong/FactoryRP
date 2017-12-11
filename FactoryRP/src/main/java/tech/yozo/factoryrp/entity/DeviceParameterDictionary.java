package tech.yozo.factoryrp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * 设备参数相关字典 不区分级别 只存放一个code一个value的值
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/10
 * @description
 */
@Data
@Table(name = "device_parameter_dictionary")
@Entity
public class DeviceParameterDictionary extends BaseEntity implements Serializable {


    private static final long serialVersionUID = -172121927758537475L;


    /**
     * 设备参数code，区分不同种类的设备参数 code值可以相同
     */
    @Column(name = "code",length = 20)
    private String code;


    /**
     * 设备参数name，同一个code下面的name不可相同
     */
    @Column(name = "name",length = 20)
    private String name;

}
