import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import tech.yozo.factoryrp.WebApplication;
import tech.yozo.factoryrp.service.DeviceInfoService;
import tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;

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

    @Test
    public void getByCodeTest(){
        FullDeviceInfoResp fi = deviceInfoService.getByCode("123114",1l);
        if (null!=fi){
            System.out.println(fi.getName());
        }
    }
}
