package tech.yozo.factoryrp.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MaintenanceEngineer implements Serializable{
    private Long userId;
    private String userName;
}
