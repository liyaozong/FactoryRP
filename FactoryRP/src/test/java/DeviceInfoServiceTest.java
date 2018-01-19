import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import tech.yozo.factoryrp.WebApplication;
import tech.yozo.factoryrp.enums.TroubleStatusEnum;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.service.DeviceInfoService;
import tech.yozo.factoryrp.service.TroubleRecordService;
import tech.yozo.factoryrp.vo.req.StartRepairReq;
import tech.yozo.factoryrp.vo.req.WorkOrderListReq;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.device.trouble.WaitAuditWorkOrderVo;

/**
 * @author chenxiang
 * @create 2018-01-04 上午9:38
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WebApplication.class})
@WebAppConfiguration
public class DeviceInfoServiceTest {
    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private TroubleRecordService troubleRecordService;

    @Test
    public void getByCodeTest(){
        FullDeviceInfoResp fi = deviceInfoService.getByCode("123114",1l);
        if (null!=fi){
            System.out.println(fi.getName());
        }
    }

    @Test
    public void findWorkOrderByPageTest(){
        WorkOrderListReq req = new WorkOrderListReq();
        req.setCurrentPage(1);
        req.setItemsPerPage(10);
        Pagination<WaitAuditWorkOrderVo> vo = troubleRecordService.findWorkOrderByPage(req,1l, TroubleStatusEnum.WAIT_AUDIT.getCode(),null);
        if (null!=vo){
            String js = JSON.toJSONString(vo);
            System.out.println(js);
        }
    }

    @Test
    public void dealWorkOrderTest(){
        StartRepairReq req = new StartRepairReq();
        req.setTroubleRecordId(19l);
        AuthUser user = new AuthUser();
        user.setUserId(943113773294551041l);
        user.setUserName("李四");
        troubleRecordService.startRepair(req,user);
    }
}
