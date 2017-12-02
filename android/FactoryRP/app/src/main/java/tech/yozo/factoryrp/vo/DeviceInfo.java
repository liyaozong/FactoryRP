package tech.yozo.factoryrp.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 设备信息
 */
@Data
public class DeviceInfo extends BaseEntity implements Serializable{
    private String name;
    private Long id;
    private String code;
    private String installationAddress;
    private String specification;
    private String useDept;
}
