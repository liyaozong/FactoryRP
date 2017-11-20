package cn.tech.yozo.factoryrp.service;

import cn.tech.yozo.factoryrp.entity.ContactCompany;
import cn.tech.yozo.factoryrp.page.Pagination;
import cn.tech.yozo.factoryrp.vo.req.ContactCompanyReq;

public interface ContactCompanyService {
    /**
     * 分页查询往来单位信息
     * @param param
     * @return
     */
    public Pagination<ContactCompany> findByPage(ContactCompanyReq param);

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
}
