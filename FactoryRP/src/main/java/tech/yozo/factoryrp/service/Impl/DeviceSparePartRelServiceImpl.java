package tech.yozo.factoryrp.service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.DeviceSparePartRel;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.repository.DeviceSparePartRelRepository;
import tech.yozo.factoryrp.service.DeviceInfoService;
import tech.yozo.factoryrp.service.DeviceSparePartRelService;
import tech.yozo.factoryrp.service.SparePartsService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.req.QueryDeviceSpareRelReq;
import tech.yozo.factoryrp.vo.req.SaveRelDeviceReq;
import tech.yozo.factoryrp.vo.req.SaveRelSparePartReq;
import tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.device.rel.DeviceSpartRelResp;
import tech.yozo.factoryrp.vo.resp.device.rel.SpartDeviceRelResp;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @author chenxiang
 * @create 2017-12-02 上午11:48
 **/
@Service
public class DeviceSparePartRelServiceImpl implements DeviceSparePartRelService {
    @Autowired
    private DeviceSparePartRelRepository deviceSparePartRelRepository;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private SparePartsService sparePartsService;

    @Override
    public void saveRelSparePart(SaveRelSparePartReq param, Long corporateIdentify) {
        if (null==param.getDeviceId() || 0 ==param.getDeviceId()){
            return;
        }
       List<Long> ids = param.getSparePartIds();
       if (CheckParam.isNull(ids)){
           return;
       }else {
           //排重
           List<DeviceSparePartRel> old = deviceSparePartRelRepository.findAll(new Specification<DeviceSparePartRel>() {
               @Override
               public Predicate toPredicate(Root<DeviceSparePartRel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                   List<Predicate> listCon = new ArrayList<>();
                   CriteriaBuilder.In<Long> in = criteriaBuilder.in(root.get("sparePartId"));
                   ids.forEach(i->{
                       in.value(i);
                   });
                   listCon.add(in);
                   listCon.add(criteriaBuilder.equal(root.get("deviceId").as(Long.class),param.getDeviceId()));
                   listCon.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class),corporateIdentify));
                   Predicate[] predicates = new Predicate[listCon.size()];
                   predicates = listCon.toArray(predicates);
                   return criteriaBuilder.and(predicates);
               }
           });
           Set<Long> oids = new HashSet<>();
           if (!CheckParam.isNull(old)){
               old.forEach(o->{
                   oids.add(o.getSparePartId());
               });
           }


           List<DeviceSparePartRel> saveList = new ArrayList<>();
           ids.forEach(spid->{
               if (!oids.contains(spid)){
                   DeviceSparePartRel o = new DeviceSparePartRel();
                   o.setDeviceId(param.getDeviceId());
                   o.setSparePartId(spid);
                   o.setCorporateIdentify(corporateIdentify);
                   saveList.add(o);
               }
           });
           if (!CheckParam.isNull(saveList)){
               deviceSparePartRelRepository.save(saveList);
           }
       }
    }

    @Override
    public void saveRelDevice(SaveRelDeviceReq param,Long corporateIdentify) {
        if (null==param.getSparePartId() || 0 ==param.getSparePartId()){
            return;
        }
        List<Long> ids = param.getDeviceIds();
        if (CheckParam.isNull(ids)){
            return;
        }else {
            //排重
            List<DeviceSparePartRel> old = deviceSparePartRelRepository.findAll(new Specification<DeviceSparePartRel>() {
                @Override
                public Predicate toPredicate(Root<DeviceSparePartRel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> listCon = new ArrayList<>();
                    CriteriaBuilder.In<Long> in = criteriaBuilder.in(root.get("deviceId"));
                    ids.forEach(i->{
                        in.value(i);
                    });
                    listCon.add(in);
                    listCon.add(criteriaBuilder.equal(root.get("sparePartId").as(Long.class),param.getSparePartId()));
                    listCon.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class),corporateIdentify));
                    Predicate[] predicates = new Predicate[listCon.size()];
                    predicates = listCon.toArray(predicates);
                    return criteriaBuilder.and(predicates);
                }
            });
            Set<Long> oids = new HashSet<>();
            if (!CheckParam.isNull(old)){
                old.forEach(o->{
                    oids.add(o.getDeviceId());
                });
            }

            List<DeviceSparePartRel> saveList = new ArrayList<>();
            ids.forEach(did->{
                if (!oids.contains(did)){
                    DeviceSparePartRel o = new DeviceSparePartRel();
                    o.setDeviceId(did);
                    o.setSparePartId(param.getSparePartId());
                    o.setCorporateIdentify(corporateIdentify);
                    saveList.add(o);
                }
            });
            if (!CheckParam.isNull(saveList)){
                deviceSparePartRelRepository.save(saveList);
            }
        }
    }

    @Override
    public Pagination<DeviceSpartRelResp> findRelDeviceInfoByPage(QueryDeviceSpareRelReq param, Long corporateIdentify) {
        Page<DeviceSparePartRel> page = getDeviceSparePartRels(param, corporateIdentify);
        Pagination<DeviceSpartRelResp> res = new Pagination(param.getCurrentPage(),param.getItemsPerPage(),page.getTotalElements());
        if (page.hasContent()){
            Map<Long,Long> rm = new HashMap<>();
            List<Long> deviceIds = new ArrayList<>();
            page.getContent().forEach(partRel -> {
                deviceIds.add(partRel.getDeviceId());
                rm.put(partRel.getDeviceId(),partRel.getId());
            });
            if (!CheckParam.isNull(deviceIds)){
                List<FullDeviceInfoResp> fullList = deviceInfoService.findByIds(deviceIds,corporateIdentify);
                List<DeviceSpartRelResp> reList = new ArrayList<>();
                if (!CheckParam.isNull(fullList)){
                    fullList.forEach(fullDeviceInfoResp -> {
                        DeviceSpartRelResp dr = new DeviceSpartRelResp();
                        BeanUtils.copyProperties(fullDeviceInfoResp,dr);
                        dr.setRelId(rm.get(fullDeviceInfoResp.getId()));
                        reList.add(dr);
                    });
                }
                res.setList(reList);
            }
        }
        return res;
    }

    @Override
    public Pagination<SpartDeviceRelResp> findRelSpareInfoByPage(QueryDeviceSpareRelReq param, Long corporateIdentify) {
        Page<DeviceSparePartRel> page = getDeviceSparePartRels(param, corporateIdentify);
        Pagination<SpartDeviceRelResp> res = new Pagination(param.getCurrentPage(),param.getItemsPerPage(),page.getTotalElements());
        if (page.hasContent()){
            List<Long> spareIds = new ArrayList<>();
            Map<Long,Long> rm = new HashMap<>();
            page.getContent().forEach(partRel -> {
                spareIds.add(partRel.getSparePartId());
                rm.put(partRel.getSparePartId(),partRel.getId());
            });
            if (!CheckParam.isNull(spareIds)){
                List<SparePartsResp> list = sparePartsService.findByIds(spareIds);
                List<SpartDeviceRelResp> reList = new ArrayList<>();
                if (!CheckParam.isNull(list)){
                    list.forEach(sparePartsResp -> {
                        SpartDeviceRelResp sr = new SpartDeviceRelResp();
                        BeanUtils.copyProperties(sparePartsResp,sr);
                        sr.setRelId(rm.get(sparePartsResp.getId()));
                        reList.add(sr);
                    });
                }
                res.setList(reList);
            }
        }
        return res;
    }

    /**
     * 公共查询关联信息
     * @param param
     * @param corporateIdentify
     * @return
     */
    private Page<DeviceSparePartRel> getDeviceSparePartRels(QueryDeviceSpareRelReq param, Long corporateIdentify) {
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
        return deviceSparePartRelRepository.findAll(new Specification<DeviceSparePartRel>() {
            @Override
            public Predicate toPredicate(Root<DeviceSparePartRel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> listCon = new ArrayList<>();
                if (null!=param.getSparePartId() && 0!=param.getSparePartId()){
                    listCon.add(criteriaBuilder.equal(root.get("sparePartId").as(Long.class),param.getSparePartId()));
                }
                if (null!=param.getDeviceId() && 0!=param.getDeviceId()){
                    listCon.add(criteriaBuilder.equal(root.get("deviceId").as(Long.class),param.getDeviceId()));
                }
                listCon.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class),corporateIdentify));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
                Predicate[] predicates = new Predicate[listCon.size()];
                predicates = listCon.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        },p);
    }

    @Override
    public void deleteRelInfoByIds(List<Long> ids) {
        List<DeviceSparePartRel> list = deviceSparePartRelRepository.findAll(ids);
        if (!CheckParam.isNull(list)){
            deviceSparePartRelRepository.deleteInBatch(list);
        }
    }
}
