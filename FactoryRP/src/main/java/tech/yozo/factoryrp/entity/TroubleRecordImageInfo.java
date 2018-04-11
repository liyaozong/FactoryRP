package tech.yozo.factoryrp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2018-04-11 下午2:07
 **/
@Table(name = "trouble_record_image_info")
@Entity
@Data
public class TroubleRecordImageInfo extends BaseEntity implements Serializable{

    /**
     * 故障ID
     */
    @ApiModelProperty(value = "故障ID",notes ="故障ID" )
    @Column(name = "trouble_record_id",length = 20)
    private Long troubleRecordId;


    /**
     * 图片KEY
     */
    @ApiModelProperty(value = "图片KEY",notes ="图片KEY" )
    @Column(name = "image_key",length = 20)
    private String imageKey;
}
