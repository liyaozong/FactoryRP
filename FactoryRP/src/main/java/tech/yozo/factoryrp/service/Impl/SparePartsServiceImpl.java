package tech.yozo.factoryrp.service.Impl;

import tech.yozo.factoryrp.entity.DeviceSparesType;
import tech.yozo.factoryrp.entity.SpareParts;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.repository.DeviceSparesTypeRepository;
import tech.yozo.factoryrp.repository.SparePartsRepository;
import tech.yozo.factoryrp.service.DeviceSparePartRelService;
import tech.yozo.factoryrp.service.SparePartsService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.DeviceInfoReq;
import tech.yozo.factoryrp.vo.req.SparePartsAddReq;
import tech.yozo.factoryrp.vo.req.SparePartsQueryReq;
import tech.yozo.factoryrp.vo.resp.sparepars.DeviceSparesMobileResp;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/28 备件相关服务
 * @description
 */
@Service
public class SparePartsServiceImpl implements SparePartsService {

    @Resource
    private SparePartsRepository sparePartsRepository;

    @Resource
    private DeviceSparePartRelService eviceSparePartRelService;


    /**
     * 根据条件分页查询 mobile
     * @param sparePartsQueryReq
     * @return
     */
    public Pagination<SpareParts> queryByPage(SparePartsQueryReq sparePartsQueryReq){

        return null;
    }

    /**
     * 根据条件分页查询
     * @param sparePartsQueryReq
     * @param corporateIdentify
     * @return
     */
    @Override
    public Pagination<SpareParts> findByPage(SparePartsQueryReq sparePartsQueryReq,Long corporateIdentify) {

        if (sparePartsQueryReq.getCurrentPage() > 0) {
            sparePartsQueryReq.setCurrentPage(sparePartsQueryReq.getCurrentPage()-1);
        }

        Pageable p = new PageRequest(sparePartsQueryReq.getCurrentPage(), sparePartsQueryReq.getItemsPerPage());
        Page<SpareParts> page = sparePartsRepository.findAll(new Specification<SpareParts>() {
            @Override
            public Predicate toPredicate(Root<SpareParts> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> listCon = new ArrayList<>();
                if (!CheckParam.isNull(corporateIdentify)){ //企业唯一标识
                    listCon.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class),corporateIdentify));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getCode())){ //备件编号
                    listCon.add(criteriaBuilder.equal(root.get("code").as(String.class),sparePartsQueryReq.getCode()));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getCode())){ //备件编号
                    listCon.add(criteriaBuilder.equal(root.get("code").as(String.class),sparePartsQueryReq.getCode()));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getName())){ //备件名称
                    listCon.add(criteriaBuilder.like(root.get("name").as(String.class),"%"+sparePartsQueryReq.getName()+"%"));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getManufacturer())){ //生产厂商
                    listCon.add(criteriaBuilder.equal(root.get("manufacturer").as(String.class),sparePartsQueryReq.getManufacturer()));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getSuppliers())){ //供应商
                    listCon.add(criteriaBuilder.equal(root.get("suppliers").as(Long.class),sparePartsQueryReq.getSuppliers()));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getSparePartType())){ //备件类型
                    listCon.add(criteriaBuilder.equal(root.get("sparePartType").as(Long.class),sparePartsQueryReq.getSparePartType()));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getMeasuringUnit())){ //计量单位
                    listCon.add(criteriaBuilder.equal(root.get("measuringUnit").as(Long.class),sparePartsQueryReq.getMeasuringUnit()));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getInventoryFloor())){ //库存下限
                    listCon.add(criteriaBuilder.equal(root.get("inventoryFloor").as(Integer.class),sparePartsQueryReq.getInventoryFloor()));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getMaterialProperties())){ //物料属性
                    listCon.add(criteriaBuilder.equal(root.get("materialProperties").as(String.class),sparePartsQueryReq.getMaterialProperties()));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getConversionRatio())){ //换算比例
                    listCon.add(criteriaBuilder.equal(root.get("conversionRatio").as(Double.class),sparePartsQueryReq.getConversionRatio()));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getBarCode())){ //条形码
                    listCon.add(criteriaBuilder.equal(root.get("barCode").as(String.class),sparePartsQueryReq.getBarCode()));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getReplacementCycle())){ //更换周期
                    listCon.add(criteriaBuilder.equal(root.get("replacementCycle").as(Integer.class),sparePartsQueryReq.getReplacementCycle()));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getInventoryUpperLimit())){ //库存上限
                    listCon.add(criteriaBuilder.equal(root.get("inventoryUpperLimit").as(Integer.class),sparePartsQueryReq.getInventoryUpperLimit()));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getUnitConversion())){ //换算单位
                    listCon.add(criteriaBuilder.equal(root.get("unitConversion").as(Long.class),sparePartsQueryReq.getUnitConversion()));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getReferencePrice())){ //参考价
                    listCon.add(criteriaBuilder.equal(root.get("referencePrice").as(Long.class),sparePartsQueryReq.getReferencePrice()));
                }
                if (!CheckParam.isNull(sparePartsQueryReq.getSpecificationsAndodels())){ //规格型号
                    listCon.add(criteriaBuilder.equal(root.get("specificationsAndodels").as(String.class),sparePartsQueryReq.getSpecificationsAndodels()));
                }
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
                Predicate[] predicates = new Predicate[listCon.size()];
                predicates = listCon.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        },p);

        Pagination<SpareParts> res = new Pagination<>();
        if (page.hasContent()){
            res.setList(page.getContent());
        }
        res.setCurrentPage(sparePartsQueryReq.getCurrentPage());
        res.setItemsPerPage(sparePartsQueryReq.getItemsPerPage());
        res.setTotalCount(Integer.valueOf(String.valueOf(page.getTotalElements())));

        return res;
    }


    /**
     * 根据备件id删除备件
     * @param id
     */
    public void deleteSparePartsById(Long id,Long corporateIdentify){

        sparePartsRepository.delete(id);
        List<Long> spareIds = new ArrayList<>();
        spareIds.add(id);
        eviceSparePartRelService.deleteRelInfoBySpareId(spareIds,corporateIdentify);
    }

    /**
     * 新增备件
     * @param sparePartsReq
     * @param corporateIdentify
     * @return
     */
    @Override
    public SparePartsResp addSpareParts(SparePartsAddReq sparePartsReq,Long corporateIdentify) {

        SpareParts spareParts = sparePartsRepository.findByNameAndCorporateIdentify(sparePartsReq.getName(),corporateIdentify);

        if(!CheckParam.isNull(spareParts)){
            throw new BussinessException(ErrorCode.CORPORATE_SPAREPARTS__REPET_ERROR.getCode(),ErrorCode.CORPORATE_SPAREPARTS__REPET_ERROR.getMessage());
        }

        spareParts = new SpareParts();

        spareParts.setBarCode(sparePartsReq.getBarCode());
        spareParts.setCode(sparePartsReq.getCode());
        spareParts.setName(sparePartsReq.getName());
        spareParts.setConversionRatio(sparePartsReq.getConversionRatio());


        spareParts.setExtendDateFieldOne(sparePartsReq.getExtendDateFieldOne());
        spareParts.setExtendDateFieldTwo(sparePartsReq.getExtendDateFieldTwo());
        spareParts.setExtendFieldThree(sparePartsReq.getExtendFieldThree());
        spareParts.setExtendFieldFour(spareParts.getExtendFieldFour());
        spareParts.setExtendFieldFive(sparePartsReq.getExtendFieldFive());
        spareParts.setExtendFieldSix(sparePartsReq.getExtendFieldSix());
        spareParts.setExtendFieldSeven(sparePartsReq.getExtendFieldSeven());

        spareParts.setInventoryFloor(sparePartsReq.getInventoryFloor());
        spareParts.setInventoryUpperLimit(sparePartsReq.getInventoryUpperLimit());
        spareParts.setManufacturer(sparePartsReq.getManufacturer());
        spareParts.setReferencePrice(sparePartsReq.getReferencePrice());
        spareParts.setMaterialProperties(sparePartsReq.getMaterialProperties());
        spareParts.setReplacementCycle(sparePartsReq.getReplacementCycle());
        spareParts.setMeasuringUnit(sparePartsReq.getMeasuringUnit());
        spareParts.setSparePartType(sparePartsReq.getSparePartType());
        spareParts.setSpecificationsAndodels(sparePartsReq.getSpecificationsAndodels());
        spareParts.setUnitConversion(sparePartsReq.getUnitConversion());
        spareParts.setCorporateIdentify(corporateIdentify);
        spareParts.setSuppliers(sparePartsReq.getSuppliers());
        spareParts.setSparePartType(sparePartsReq.getSparePartType());
        spareParts.setReplacementCycle(sparePartsReq.getReplacementCycle());

        sparePartsRepository.save(spareParts);

        SparePartsResp sparePartsResp = new SparePartsResp();

        sparePartsResp.setId(spareParts.getId());

        sparePartsResp.setBarCode(spareParts.getBarCode());
        sparePartsResp.setCode(spareParts.getCode());
        sparePartsResp.setConversionRatio(spareParts.getConversionRatio());
        sparePartsResp.setName(spareParts.getName());

        /*sparePartsResp.setExtendDateFieldOne(spareParts.getExtendDateFieldOne());
        sparePartsResp.setExtendDateFieldTwo(spareParts.getExtendDateFieldTwo());
        sparePartsResp.setExtendFieldFive(spareParts.getExtendFieldOne());
        sparePartsResp.setExtendFieldThree(spareParts.getExtendFieldThree());
        sparePartsResp.setExtendFieldFour(spareParts.getExtendFieldFour());
        sparePartsResp.setExtendFieldFive(spareParts.getExtendFieldFive());
        sparePartsResp.setExtendFieldSix(spareParts.getExtendFieldSix());
        sparePartsResp.setExtendFieldSeven(spareParts.getExtendFieldSeven());*/

        sparePartsResp.setInventoryFloor(spareParts.getInventoryFloor());
        sparePartsResp.setManufacturer(spareParts.getManufacturer());
        sparePartsResp.setInventoryUpperLimit(spareParts.getInventoryUpperLimit());
        sparePartsResp.setName(spareParts.getName());
        sparePartsResp.setMaterialProperties(spareParts.getMaterialProperties());
        sparePartsResp.setUnitConversion(spareParts.getUnitConversion());
        sparePartsResp.setSuppliers(spareParts.getSuppliers());
        sparePartsResp.setSparePartType(spareParts.getSparePartType());
        sparePartsResp.setSpecificationsAndodels(spareParts.getSpecificationsAndodels());
        sparePartsResp.setMeasuringUnit(spareParts.getMeasuringUnit());
        sparePartsResp.setReferencePrice(spareParts.getReferencePrice());
        sparePartsResp.setSparePartType(spareParts.getSparePartType());
        sparePartsResp.setReplacementCycle(spareParts.getReplacementCycle());

        return sparePartsResp;
    }

    @Override
    public List<SparePartsResp> findByIds(List<Long> ids) {
        List<SpareParts> list = sparePartsRepository.findAll(ids);
        List<SparePartsResp> res = new ArrayList<>();
        if (!CheckParam.isNull(list)){
            list.forEach(spareParts -> {
                SparePartsResp sr = new SparePartsResp();
                BeanUtils.copyProperties(spareParts,sr);
                res.add(sr);
            });
        }
        return res;
    }


    /**
     * 手机端查询备件信息
     * @param sparePartsQueryReq
     * @param corporateIdentify
     * @return
     */
    public Pagination<DeviceSparesMobileResp> queryMobileDeviceSpares(SparePartsQueryReq sparePartsQueryReq,Long corporateIdentify){
        if (sparePartsQueryReq.getCurrentPage() > 0) {
            sparePartsQueryReq.setCurrentPage(sparePartsQueryReq.getCurrentPage()-1);
        }

        Pageable p = new PageRequest(sparePartsQueryReq.getCurrentPage(), sparePartsQueryReq.getItemsPerPage());
        Page<SpareParts> page = sparePartsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> listCon = new ArrayList<>();
            if (!CheckParam.isNull(corporateIdentify)){ //企业唯一标识
                listCon.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class),corporateIdentify));
            }
            if (!CheckParam.isNull(sparePartsQueryReq.getName())){ //备件名称
                listCon.add(criteriaBuilder.like(root.get("name").as(String.class),"%"+sparePartsQueryReq.getName()+"%"));
            }
            if (!CheckParam.isNull(sparePartsQueryReq.getManufacturer())){ //生产厂商
                listCon.add(criteriaBuilder.equal(root.get("manufacturer").as(String.class),sparePartsQueryReq.getManufacturer()));
            }
            if (!CheckParam.isNull(sparePartsQueryReq.getSuppliers())){ //供应商
                listCon.add(criteriaBuilder.equal(root.get("suppliers").as(Long.class),sparePartsQueryReq.getSuppliers()));
            }
            if (!CheckParam.isNull(sparePartsQueryReq.getSparePartType())){ //备件类型
                listCon.add(criteriaBuilder.equal(root.get("sparePartType").as(Long.class),sparePartsQueryReq.getSparePartType()));
            }
            if (!CheckParam.isNull(sparePartsQueryReq.getMeasuringUnit())){ //计量单位
                listCon.add(criteriaBuilder.equal(root.get("measuringUnit").as(Long.class),sparePartsQueryReq.getMeasuringUnit()));
            }
            if (!CheckParam.isNull(sparePartsQueryReq.getInventoryFloor())){ //库存下限
                listCon.add(criteriaBuilder.equal(root.get("inventoryFloor").as(Integer.class),sparePartsQueryReq.getInventoryFloor()));
            }
            if (!CheckParam.isNull(sparePartsQueryReq.getMaterialProperties())){ //物料属性
                listCon.add(criteriaBuilder.equal(root.get("materialProperties").as(String.class),sparePartsQueryReq.getMaterialProperties()));
            }
            if (!CheckParam.isNull(sparePartsQueryReq.getConversionRatio())){ //换算比例
                listCon.add(criteriaBuilder.equal(root.get("conversionRatio").as(Double.class),sparePartsQueryReq.getConversionRatio()));
            }
            if (!CheckParam.isNull(sparePartsQueryReq.getBarCode())){ //条形码
                listCon.add(criteriaBuilder.equal(root.get("barCode").as(String.class),sparePartsQueryReq.getBarCode()));
            }
            if (!CheckParam.isNull(sparePartsQueryReq.getReplacementCycle())){ //更换周期
                listCon.add(criteriaBuilder.equal(root.get("replacementCycle").as(Integer.class),sparePartsQueryReq.getReplacementCycle()));
            }
            if (!CheckParam.isNull(sparePartsQueryReq.getInventoryUpperLimit())){ //库存上限
                listCon.add(criteriaBuilder.equal(root.get("inventoryUpperLimit").as(Integer.class),sparePartsQueryReq.getInventoryUpperLimit()));
            }
            if (!CheckParam.isNull(sparePartsQueryReq.getUnitConversion())){ //换算单位
                listCon.add(criteriaBuilder.equal(root.get("unitConversion").as(Long.class),sparePartsQueryReq.getUnitConversion()));
            }
            if (!CheckParam.isNull(sparePartsQueryReq.getReferencePrice())){ //参考价
                listCon.add(criteriaBuilder.equal(root.get("referencePrice").as(Long.class),sparePartsQueryReq.getReferencePrice()));
            }
            if (!CheckParam.isNull(sparePartsQueryReq.getSpecificationsAndodels())){ //规格型号
                listCon.add(criteriaBuilder.equal(root.get("specificationsAndodels").as(String.class),sparePartsQueryReq.getSpecificationsAndodels()));
            }
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
            Predicate[] predicates = new Predicate[listCon.size()];
            predicates = listCon.toArray(predicates);
            return criteriaBuilder.and(predicates);
        },p);

        Pagination<DeviceSparesMobileResp> res = new Pagination<>();
       if (page.hasContent()){

            List<SpareParts> sparePartsList = page.getContent();

            List<DeviceSparesMobileResp> deviceSparesMobileRespList = new ArrayList<>();

            sparePartsList.stream().forEach(s1 ->{
                DeviceSparesMobileResp deviceSparesMobileResp = new DeviceSparesMobileResp();
                deviceSparesMobileResp.setId(s1.getId());
                deviceSparesMobileResp.setCode(s1.getCode());
                deviceSparesMobileResp.setName(s1.getName());
                deviceSparesMobileResp.setSpecificationsAndodels(s1.getSpecificationsAndodels());

                deviceSparesMobileRespList.add(deviceSparesMobileResp);

            });

            res.setList(deviceSparesMobileRespList);
        }
        res.setCurrentPage(sparePartsQueryReq.getCurrentPage());
        res.setItemsPerPage(sparePartsQueryReq.getItemsPerPage());
        res.setTotalCount(Integer.valueOf(String.valueOf(page.getTotalElements())));

        return res;
    }

    /**
     * 批量删除备件信息
     * 先删除备件信息
     * 再删除备件信息和设备信息的关联信息
     * @param ids
     * @param corporateIdentify
     */
    public void deleteSparePartsByIds(List<Long> ids,Long corporateIdentify){

        List<SpareParts> deviceSparesTypeList = sparePartsRepository.findAll(ids);

        if(!CheckParam.isNull(deviceSparesTypeList) && !deviceSparesTypeList.isEmpty()){
            sparePartsRepository.deleteInBatch(deviceSparesTypeList);
            eviceSparePartRelService.deleteRelInfoByDeviceId(ids,corporateIdentify);
        }

    }


}
