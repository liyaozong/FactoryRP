package cn.tech.yozo.factoryrp.service.Impl;

import cn.tech.yozo.factoryrp.entity.DeviceInfo;
import cn.tech.yozo.factoryrp.page.Pagination;
import cn.tech.yozo.factoryrp.repository.DeviceInfoRepository;
import cn.tech.yozo.factoryrp.service.DeviceInfoService;
import cn.tech.yozo.factoryrp.vo.req.DeviceInfoReq;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DeviceInfoServiceImpl implements DeviceInfoService{
    @Autowired
    private DeviceInfoRepository deviceInfoRepository;


    @Override
    public Pagination<DeviceInfo> findByPage(DeviceInfoReq param) {
        Integer currentPage = param.getCurrentPage();
        Integer itemsPerPage = param.getItemsPerPage();
        if(null==currentPage){
            currentPage=0;
        }
        if (null==itemsPerPage){
            itemsPerPage=10;
        }
        if (currentPage > 0) {
            currentPage-=1;
        }
        Pageable p = new PageRequest(currentPage, itemsPerPage);
        Page<DeviceInfo> page = deviceInfoRepository.findAll(new Specification<DeviceInfo>() {
            @Override
            public Predicate toPredicate(Root<DeviceInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> listCon = new ArrayList<>();
                if (!StringUtils.isEmpty(param.getCode())){
                    listCon.add(criteriaBuilder.equal(root.get("code").as(String.class),param.getCode()));
                }
                if (!StringUtils.isEmpty(param.getName())){
                    listCon.add(criteriaBuilder.like(root.get("name").as(String.class),"%"+param.getName()+"%"));
                }
                if (null!=param.getManufacturer() && param.getManufacturer()!=0){
                    listCon.add(criteriaBuilder.equal(root.get("manufacturer").as(Long.class),param.getManufacturer()));
                }
                if (null!=param.getSupplier() && param.getSupplier()!=0){
                    listCon.add(criteriaBuilder.equal(root.get("supplier").as(Long.class),param.getSupplier()));
                }
                if (null!=param.getDeviceType() && param.getDeviceType()!=0){
                    listCon.add(criteriaBuilder.equal(root.get("deviceType").as(Long.class),param.getDeviceType()));
                }
                if (null!=param.getBuyDate()){
                    listCon.add(criteriaBuilder.equal(root.get("buyDate").as(Date.class),param.getBuyDate()));
                }
                if (!StringUtils.isEmpty(param.getHead())){
                    listCon.add(criteriaBuilder.like(root.get("head").as(String.class),"%"+param.getHead()+"%"));
                }
                if (null!=param.getUseStatus() && param.getUseStatus()!=0){
                    listCon.add(criteriaBuilder.equal(root.get("useStatus").as(Integer.class),param.getUseStatus()));
                }
                if (null!=param.getUseDept() && param.getSupplier()!=0){
                    listCon.add(criteriaBuilder.equal(root.get("useDept").as(Long.class),param.getUseDept()));
                }
                listCon.add(criteriaBuilder.equal(root.get("statusFlag").as(Integer.class),1));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
                Predicate[] predicates = new Predicate[listCon.size()];
                predicates = listCon.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        },p);
        Pagination<DeviceInfo> res = new Pagination<>();
        if (page.hasContent()){
            res.setList(page.getContent());
        }
        res.setCurrentPage(currentPage);
        res.setItemsPerPage(itemsPerPage);
        res.setTotalCount((int)page.getTotalElements());
        return res;
    }

    @Override
    public DeviceInfo save(DeviceInfo param) {
        if (null!=param.getId()&&0!=param.getId()){
            param.setUpdateTime(new Date());
        }
        return deviceInfoRepository.save(param);
    }
}
