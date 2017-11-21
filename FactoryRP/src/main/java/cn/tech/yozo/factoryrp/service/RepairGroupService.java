package cn.tech.yozo.factoryrp.service;

import cn.tech.yozo.factoryrp.entity.RepairGroup;

import java.util.List;

public interface RepairGroupService {

    public List<RepairGroup> listAll();

    public RepairGroup add(String code,String name,Long corporateIdentify);

    public void deleteRgs(List<Long> ids);
}
