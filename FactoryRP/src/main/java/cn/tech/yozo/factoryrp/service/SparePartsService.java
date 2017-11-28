package cn.tech.yozo.factoryrp.service;

import cn.tech.yozo.factoryrp.entity.SpareParts;
import cn.tech.yozo.factoryrp.vo.req.SparePartsReq;
import org.springframework.data.domain.Page;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/28
 * @description 备件相关服务
 */
public interface SparePartsService {


    Page<SpareParts> findByPage();


    void addSpareParts(SparePartsReq sparePartsReq);


}
