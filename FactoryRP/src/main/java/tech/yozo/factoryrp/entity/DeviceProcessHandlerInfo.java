package tech.yozo.factoryrp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 流程处理人信息
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
/*@Data
@Table(name = "device_process_handler_info")
@Entity*/
public class DeviceProcessHandlerInfo extends BaseEntity implements Serializable {


    /**
     * 流程详情关联
     */
    @Column(name = "process_detail",length = 20)
    private Long processDetail;





}
