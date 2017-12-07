package tech.yozo.factoryrp.service.Impl;

import tech.yozo.factoryrp.entity.ContactCompany;
import tech.yozo.factoryrp.entity.Department;
import tech.yozo.factoryrp.entity.DeviceInfo;
import tech.yozo.factoryrp.entity.DeviceType;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.repository.DeviceInfoRepository;
import tech.yozo.factoryrp.service.*;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.req.DeviceInfoReq;
import tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.device.info.SimpleDeviceInfoResp;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.BeanUtils;
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
import java.util.*;

@Service
public class DeviceInfoServiceImpl implements DeviceInfoService {
    @Autowired
    private DeviceInfoRepository deviceInfoRepository;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ContactCompanyService contactCompanyService;

    @Autowired
    private DeviceTypeService deviceTypeService;

    @Autowired
    private DeviceSparePartRelService deviceSparePartRelService;

    @Override
    public Pagination<FullDeviceInfoResp> findByPage(DeviceInfoReq param, Long corporateIdentify) {
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
                listCon.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class),corporateIdentify));
                listCon.add(criteriaBuilder.equal(root.get("statusFlag").as(Integer.class),1));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
                Predicate[] predicates = new Predicate[listCon.size()];
                predicates = listCon.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        },p);
        Pagination<FullDeviceInfoResp> res = new Pagination(currentPage+1,itemsPerPage,page.getTotalElements());
        if (page.hasContent()){
            List<DeviceInfo> listDevice = page.getContent();
            List<FullDeviceInfoResp> reList = convertDeviceInfo(corporateIdentify, listDevice);
            res.setList(reList);
        }
        return res;
    }

    @Override
    public DeviceInfo save(DeviceInfo param) {
        if (null!=param.getId()&&0!=param.getId()){
            param.setUpdateTime(new Date());
        }
        return deviceInfoRepository.save(param);
    }

    @Override
    public FullDeviceInfoResp getById(Long id,Long corporateIdentify) {
        DeviceInfo deviceInfo = deviceInfoRepository.findOne(id);
        List<DeviceInfo> list =new ArrayList<>();
        list.add(deviceInfo);
        List<FullDeviceInfoResp> reList =convertDeviceInfo(corporateIdentify,list);
        if (!CheckParam.isNull(reList)){
            return reList.get(0);
        }
        return null;
    }

    @Override
    public Pagination<SimpleDeviceInfoResp> findSimpleInfoByPage(DeviceInfoReq param, Long corporateIdentify) {
        Pagination<FullDeviceInfoResp> res = this.findByPage(param,corporateIdentify);
        Pagination<SimpleDeviceInfoResp> r = new Pagination(res.getCurrentPage(),res.getItemsPerPage(),res.getTotalCount());
        if (!CheckParam.isNull(res.getList())){
            List<SimpleDeviceInfoResp> ls = new ArrayList<>();
            res.getList().forEach(fullDeviceInfoResp -> {
                SimpleDeviceInfoResp sr = new SimpleDeviceInfoResp();
                BeanUtils.copyProperties(fullDeviceInfoResp,sr);
                ls.add(sr);
            });
            r.setList(ls);
        }
        return r;
    }

    @Override
    public List<FullDeviceInfoResp> findByIds(List<Long> ids,Long corporateIdentify) {
        List<DeviceInfo> listDevice = deviceInfoRepository.findAll(ids);
        List<FullDeviceInfoResp> reList = convertDeviceInfo(corporateIdentify, listDevice);
        return reList;
    }

    @Override
    public void deleteRelInfoByIds(List<Long> ids,Long corporateIdentify) {
        List<DeviceInfo> listDevice = deviceInfoRepository.findAll(ids);
        if (!CheckParam.isNull(listDevice)){
            deviceInfoRepository.deleteInBatch(listDevice);
            deviceSparePartRelService.deleteRelInfoByDeviceId(ids,corporateIdentify);
        }
    }

    /**
     * 转行设备信息
     * @param corporateIdentify
     * @param listDevice
     * @return
     */
    private List<FullDeviceInfoResp> convertDeviceInfo(Long corporateIdentify, List<DeviceInfo> listDevice) {
        List<FullDeviceInfoResp> reList = new ArrayList<>();
        List<Department> listDept = departmentService.list(corporateIdentify);
        Map<Long,String> deptMap = new HashMap<>();
        if(!CheckParam.isNull(listDept)){
            listDept.forEach(department -> {
                deptMap.put(department.getId(),department.getName());
            });
        }
        List<DeviceType> deviceTypeList =  deviceTypeService.list(corporateIdentify);
        Map<Long,String> dtMap = new HashMap<>();
        if (!CheckParam.isNull(deviceTypeList)){
            deviceTypeList.forEach(deviceType -> {
                dtMap.put(deviceType.getId(),deviceType.getName());
            });
        }

        List<ContactCompany> contactCompanyList =  contactCompanyService.list(corporateIdentify);
        Map<Long,String> ccMap = new HashMap<>();
        if (!CheckParam.isNull(contactCompanyList)){
            contactCompanyList.forEach(cc -> {
                ccMap.put(cc.getId(),cc.getName());
            });
        }
        listDevice.forEach(deviceInfo -> {
            FullDeviceInfoResp fdi = new FullDeviceInfoResp();
            BeanUtils.copyProperties(deviceInfo,fdi);
            fdi.setDeviceType(dtMap.get(deviceInfo.getDeviceType()));
            fdi.setManufacturer(ccMap.get(deviceInfo.getManufacturer()));
            fdi.setSupplier(ccMap.get(deviceInfo.getSupplier()));
            fdi.setUseDept(deptMap.get(deviceInfo.getUseDept()));
            reList.add(fdi);
        });
        return reList;
    }
}
