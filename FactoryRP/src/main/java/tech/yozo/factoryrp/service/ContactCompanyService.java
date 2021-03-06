package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.entity.ContactCompany;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.vo.req.ContactCompanyReq;

import java.util.List;

public interface ContactCompanyService {
    /**
     * 分页查询往来单位信息
     * @param param
     * @return
     */
    public Pagination<ContactCompany> findByPage(ContactCompanyReq param,Long corporateIdentify);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    public ContactCompany getById(Long id);

    /**
     * 保存往来单位信息
     * @param param
     * @return
     */
    public ContactCompany save(ContactCompany param);

    /**
     * 删除往来单位
     * @param id
     * @return
     */
    public void delete(Long id);

    /**
     * 根据企业标识查询往来单位
     * @param corporateIdentify
     * @return
     */
    public List<ContactCompany> list(Long corporateIdentify);
}
