package tech.yozo.factoryrp.service.Impl;

import tech.yozo.factoryrp.entity.ContactCompany;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.repository.ContactCompanyRepository;
import tech.yozo.factoryrp.service.ContactCompanyService;
import tech.yozo.factoryrp.vo.req.ContactCompanyReq;
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
public class ContactCompanyServiceImpl implements ContactCompanyService {
    @Autowired
    private ContactCompanyRepository contactCompanyRepository;

    @Override
    public Pagination<ContactCompany> findByPage(ContactCompanyReq param, Long corporateIdentify) {
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
        Page<ContactCompany> page = contactCompanyRepository.findAll(new Specification<ContactCompany>() {
            @Override
            public Predicate toPredicate(Root<ContactCompany> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> listCon = new ArrayList<>();
                if (!StringUtils.isEmpty(param.getCode())){
                    listCon.add(criteriaBuilder.equal(root.get("code").as(String.class),param.getCode()));
                }
                if (!StringUtils.isEmpty(param.getName())){
                    listCon.add(criteriaBuilder.like(root.get("name").as(String.class),"%"+param.getName()+"%"));
                }
                if (!StringUtils.isEmpty(param.getContactName())){
                    listCon.add(criteriaBuilder.like(root.get("contactName").as(String.class),"%"+param.getContactName()+"%"));
                }
                listCon.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class),corporateIdentify));
                listCon.add(criteriaBuilder.equal(root.get("statusFlag").as(Integer.class),1));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
                Predicate[] predicates = new Predicate[listCon.size()];
                predicates = listCon.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        },p);
        Pagination<ContactCompany> res = new Pagination<>();
        if (page.hasContent()){
            res.setList(page.getContent());
        }
        res.setCurrentPage(currentPage);
        res.setItemsPerPage(itemsPerPage);
        res.setTotalCount((int)page.getTotalElements());
        return res;
    }

    @Override
    public ContactCompany getById(Long id) {
        return contactCompanyRepository.findOne(id);
    }

    @Override
    public ContactCompany save(ContactCompany param) {
        if (null!=param.getId() && 0 != param.getId()){
            //修改
            param.setUpdateTime(new Date());
        }
        return contactCompanyRepository.save(param);
    }

    @Override
    public void delete(Long id) {
        contactCompanyRepository.delete(id);
    }

    @Override
    public List<ContactCompany> list(Long corporateIdentify) {
        return contactCompanyRepository.findByCorporateIdentifyAndStatusFlag(corporateIdentify,1);
    }

}
