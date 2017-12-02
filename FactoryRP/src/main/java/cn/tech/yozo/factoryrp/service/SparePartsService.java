package cn.tech.yozo.factoryrp.service;

import cn.tech.yozo.factoryrp.entity.SpareParts;
import cn.tech.yozo.factoryrp.page.Pagination;
import cn.tech.yozo.factoryrp.vo.req.SparePartsAddReq;
import cn.tech.yozo.factoryrp.vo.req.SparePartsQueryReq;
import cn.tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/28
 * @description 备件相关服务
 */
public interface SparePartsService {


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
