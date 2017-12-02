package cn.tech.yozo.factoryrp.service;

import cn.tech.yozo.factoryrp.entity.RepairGroup;

import java.util.List;

public interface RepairGroupService {

    public List<RepairGroup> listAll(Long corporateIdentify);

    public RepairGroup add(String code,String name,Long corporateIdentify);

    public void deleteRgs(List<Long> ids);
}
