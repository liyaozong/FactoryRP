package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.entity.SpareParts;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.vo.req.SparePartsAddReq;
import tech.yozo.factoryrp.vo.req.SparePartsQueryReq;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;

import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/28
 * @description 备件相关服务
 */
public interface SparePartsService {


    /**
     * 根据备件id删除备件
     * @param id
     */
    void deleteSparePartsById(Long id,Long corporateIdentify);

    /**
     * 根据条件分页查询
     * @param sparePartsQueryReq
     * @return
     */
    Pagination<SpareParts> findByPage(SparePartsQueryReq sparePartsQueryReq);


    /**
     * 新增备件
     * @param sparePartsReq
     * @return
     */
    SparePartsResp addSpareParts(SparePartsAddReq sparePartsReq);

    /**
     * 根据主键批量查询
     * @param ids
     * @return
     */
    List<SparePartsResp> findByIds(List<Long> ids);
}
