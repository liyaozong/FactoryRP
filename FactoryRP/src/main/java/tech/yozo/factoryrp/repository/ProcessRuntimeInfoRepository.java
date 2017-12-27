package tech.yozo.factoryrp.repository;

import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.ProcessRuntimeInfo;

/**
 * 流程运行信息Repository
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
@Repository
public interface ProcessRuntimeInfoRepository extends BaseRepository<ProcessRuntimeInfo,Long> {
}
