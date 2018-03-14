/**
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/13
 * @description
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import tech.yozo.factoryrp.WebApplication;
import tech.yozo.factoryrp.entity.SpotInspectionRecordDetail;
import tech.yozo.factoryrp.repository.SpotInspectionRecordDetailRepository;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WebApplication.class})
@WebAppConfiguration
public class SpotInspectionRecordDetailTest {


    @Resource
    private SpotInspectionRecordDetailRepository spotInspectionRecordDetailRepository;


    @Test
    public void testUpdate(){
        SpotInspectionRecordDetail detail = new SpotInspectionRecordDetail();

        SpotInspectionRecordDetail d = spotInspectionRecordDetailRepository.findOne(50L);

        d.setId(null);
        d.setRemark("备注备注备注");

        detail.setId(51L);
        detail.setRemark("备注备注备注");
        spotInspectionRecordDetailRepository.save(d);

    }

}
