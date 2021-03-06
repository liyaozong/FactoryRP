package tech.yozo.factoryrp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.entity.mime.HttpMultipartMode;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.ByteArrayBody;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.util.EntityUtils;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.entity.Department;
import tech.yozo.factoryrp.entity.DeviceType;
import tech.yozo.factoryrp.entity.MaintenanceEngineer;
import tech.yozo.factoryrp.entity.RepairGroup;
import tech.yozo.factoryrp.vo.req.*;
import tech.yozo.factoryrp.vo.resp.*;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.device.info.SimpleDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.device.trouble.DeviceTroubleTypeVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WaitAuditWorkOrderVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderCountVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderDetailVo;
import tech.yozo.factoryrp.vo.resp.inspect.InspectDeviceDetailResp;
import tech.yozo.factoryrp.vo.resp.inspect.InspectDevicesResp;
import tech.yozo.factoryrp.vo.resp.inspect.InspectTaskResp;
import tech.yozo.factoryrp.vo.resp.sparepars.DeviceSparesTypeResp;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class HttpClient {
//    private static final String BASE_URL = "http://192.168.6.100:9550/";
//    private static final String BASE_URL = "http://factoryrp.yozo.tech:9550/";  //研发环境
    private static final String BASE_URL = "http://39.104.71.127:9550/";  //银河灵动测试环境
    public static final String IMAGE_URL = "http://factoryrp.oss-cn-hangzhou.aliyuncs.com/"; //文件存储

    private static final String CONTENT_TYPE = "application/json";
    private static final int COMMON_TIMEOUT = 8000;

    private static final String LOGIN = "api/authorization/login"; //登录
    private static final String DATA_DICT = "api/deviceParameterDictionary/findByCode"; //数据字典
    private static final String CONTACT_COMPANY = "api/contactCompany/list"; //来往单位
    private static final String DEVICE_LIST = "api/deviceInfo/listSimpleInfo"; //设备列表
    private static final String DEVICE_SAVE = "api/deviceInfo/save"; //设备添加
    private static final String DEVICE_GET = "api/deviceInfo/get"; //设备详细信息
    private static final String PARTS_LIST = "api/spareParts/queryMobileDeviceSpares"; //备件列表
    private static final String TROUBLE_ADD = "troubleRecord/add"; //故障报修
    private static final String TROUBLE_COUNT = "troubleRecord/countWorkOrderList"; //故障分类统计数值
    private static final String TROUBLE_WAIT_AUDIT = "troubleRecord/waitAuditList"; //待审核的故障
    private static final String TROUBLE_WAIT_REPAIR = "troubleRecord/waitRepairList"; //待维修的故障
    private static final String TROUBLE_REPAIRING = "troubleRecord/repairingList"; //维修中的故障
    private static final String TROUBLE_WAIT_VALIDATE = "troubleRecord/waitValidateList"; //待验证的故障
    private static final String REPAIR_GRAB_URL = "troubleRecord/obtainOrder"; //我来修
    private static final String REPAIR_GIVEUP_URL = "troubleRecord/cancelOrder"; //我不修了
    private static final String REPAIR_DETAIL_URL = "troubleRecord/get"; //故障详细信息
    private static final String START_REPAIR_ACTION = "troubleRecord/startRepair"; //开始维修
    private static final String END_REPAIR_ACTION = "troubleRecord/endRepair"; //开始维修
    private static final String SUBMIT_REPAIR_ACTION = "troubleRecord/submitRepair"; //完成维修并提交维修信息
    private static final String VALIDATE_REPAIR_ACTION = "troubleRecord/validate"; //验证维修
    private static final String FIND_PARTS_FOR_DEVICE = "deviceSpareRel/findRelSparts"; //设备关联的备件
    private static final String REPAIR_GROUP_URL = "api/repairGroup/list"; //维修工段/班组列表
    private static final String DEVICE_TYPE_URL = "api/deviceType/list"; //设备类型字典
    private static final String DEVICE_TROUBLE_TYPE_URL = "api/deviceTroubleType/queryAlldDeviceTroubleType"; //故障类型字典
    private static final String PARTS_TYPE_URL = "api/deviceSparesType/queryAllDeviceSparesType"; //备件类型字典
    private static final String DEPARTMENT_LIST_URL = "api/department/list"; //部门列表
    private static final String DEVICE_GET_BY_CODE = "api/deviceInfo/getByCode"; //根据条形码查询设备
    private static final String TROUBLE_LIST_BY_DEVICEID = "troubleRecord/list"; //根据设备ID获取故障列表
    private static final String MEMBER_LIST_BY_ROLE = "api/authorization/queryUserByRoleCode"; //根据角色查询员工列表
    private static final String INSPECT_TASK = "api/spotInspectionPlan/queryMobilePlan";  //获取当前用户的巡检任务
    private static final String INSPECT_DEVICES = "api/spotInspectionPlan/querySpotInspectionPlanDevices";  //获取巡检任务关联的设备
    private static final String INSPECT_ITEM = "api/spotInspectionStandard/queryMobileInspectionItemByPlanIdAndDeviceId";  //获取某个设备的巡检项目
    private static final String SUBMIT_INSPECT_RESULT = "api/spotInspectionRecord/spotInspectionItemsRecordMobileAdd";  //提交巡检结果
    private static final String MAINTAIN_TASK = "maintainPlan/simpleList";  //获取保养计划列表
    private static final String MAINTAIN_TASK_COUNT = "maintainPlan/getCount";  //获取保养计划统计数字
    private static final String MAINTAIN_DETAIL = "maintainPlan/getDetail";  //保养计划详情
    private static final String MAINTAIN_SUBMIT = "maintainPlan/submit";  //保养任务提交
    private static final String UPLOAD_FILE = "api/itemUpload/uploadImageToOSS"; //上传文件
    private static final String TROUBLE_WAIT_ASSIGN = "troubleRecord/waitAllocateRepairList"; //待派工的故障
    private static final String TROUBLE_EXEC_ASSIGN = "troubleRecord/allocateWorker"; //执行故障派工
    private static final String TROUBLE_EXEC_AUDIT = "troubleRecord/audit"; //执行故障审核
    private static final String PASSWD_RESET = "api/authorization/updateCurrentUserPassword";  //修改密码
    private static final String USER_ROLE = "api/authorization/queryRoleByUserId"; //获取用户角色

    public static final int REQUEST_LOGIN = 1;
    public static final int REQUEST_DATA_DICT = 2;
    public static final int REQUEST_CONTACT_COMPANY = 3;
    public static final int REQUEST_DEVICE_LIST = 4;
    public static final int REQUEST_DEVICE_SAVE = 5;
    public static final int REQUEST_DEVICE_GET = 6;
    public static final int REQUEST_PARTS_LIST = 7;
    public static final int REQUEST_TROUBLE_ADD = 8;
    public static final int REQUEST_TROUBLE_COUNT = 10;
    public static final int REQUEST_TROUBLE_WAIT_AUDIT = 11;
    public static final int REQUEST_TROUBLE_WAIT_REPAIR = 12;
    public static final int REQUEST_TROUBLE_REPAIRING = 13;
    public static final int REQUEST_TROUBLE_WAIT_VALIDATE = 14;
    public static final int REQUEST_REPAIR_GRAB_URL = 15;
    public static final int REQUEST_REPAIR_GIVEUP_URL = 16;
    public static final int REQUEST_REPAIR_DETAIL_URL = 17;
    public static final int REQUEST_START_REPAIR_ACTION = 18;
    public static final int REQUEST_END_REPAIR_ACTION = 19;
    public static final int REQUEST_SUBMIT_REPAIR_ACTION = 20;
    public static final int REQUEST_VALIDATE_REPAIR_ACTION = 21;
    public static final int REQUEST_FIND_PARTS_FOR_DEVICE = 22;
    public static final int REQUEST_REPAIR_GROUP_URL = 23;
    public static final int REQUEST_DEVICE_TYPE_URL = 24;
    public static final int REQUEST_DEVICE_TROUBLE_TYPE_URL = 25;
    public static final int REQUEST_PARTS_TYPE_URL = 26;
    public static final int REQUEST_DEPARTMENT_LIST_URL = 27;
    public static final int REQUEST_DEVICE_GET_BY_CODE = 28;
    public static final int REQUEST_TROUBLE_LIST_BY_DEVICEID = 29;
    public static final int REQUEST_MEMBER_LIST_BY_ROLE = 30;
    public static final int REQUEST_INSPECT_TASK = 31;
    public static final int REQUEST_INSPECT_DEVICES = 32;
    public static final int REQUEST_INSPECT_ITEM = 33;
    public static final int REQUEST_SUBMIT_INSPECT_RESULT = 34;
    public static final int REQUEST_MAINTAIN_TASK = 35;
    public static final int REQUEST_MAINTAIN_TASK_COUNT = 36;
    public static final int REQUEST_MAINTAIN_DETAIL = 37;
    public static final int REQUEST_MAINTAIN_SUBMIT = 38;
    public static final int REQUEST_UPLOAD_FILE = 39;
    public static final int REQUEST_TROUBLE_WAIT_ASSIGN = 40;
    public static final int REQUEST_TROUBLE_EXEC_ASSIGN = 41;
    public static final int REQUEST_TROUBLE_EXEC_AUDIT = 42;
    public static final int REQUEST_PASSWD_RESET = 43;
    public static final int REQUEST_USER_ROLE = 44;

    private AsyncHttpClient client;
    private List<Header> headers = new ArrayList<>();

    @Getter
    private AuthUser authUser;

    @Getter
    private List<RoleResp> roles;

//    @Getter
//    @Setter
//    private WorkOrderCountVo mTroubleCount;

    @Getter
    @Setter
    private List<SimpleDeviceInfoResp> simpleDeviceInfoList;

    @Getter
    @Setter
    private List<FullDeviceInfoResp> fullDeviceInfoList = new ArrayList<>();

    @Getter
    @Setter
    private List<SparePartsResp> sparePartsList;

//    private List<WaitAuditWorkOrderVo> waitAuditWorkOrderVoList;
//
//    private List<WaitAuditWorkOrderVo> waitRepairWorkOrderVoList;
//
//    private List<WaitAuditWorkOrderVo> repairingWorkOrderVoList;
//
//    private List<WaitAuditWorkOrderVo> waitVerifyWorkOrderVoList;

    @Getter
    @Setter
    private List<ContactCompany> contactCompanies;

    @Setter @Getter
    private List<RepairGroup> repairGroupList;

    @Getter @Setter
    private List<DeviceType> deviceTypeList;

    @Setter @Getter
    private List<DeviceTroubleTypeVo> troubleTypeVoList;

    @Getter @Setter
    private List<DeviceSparesTypeResp> partsTypeList;

    @Setter @Getter
    private List<Department> departmentList;

    private List<DeviceParamDicEnumResp> deviceUseStatusDict;

    private List<DeviceParamDicEnumResp> deviceRunningStatusDict;

    private List<DeviceParamDicEnumResp> deviceIdentifyDict;

    private List<DeviceParamDicEnumResp> troubleLevelDict;

    private List<DeviceParamDicEnumResp> troubleReasonDict;

    private List<DeviceParamDicEnumResp> repairLevelDict;

    private List<DeviceParamDicEnumResp> maintainLevelDict;

    private List<DeviceParamDicEnumResp> verifyCommentDict;

    private List<DeviceParamDicEnumResp> repairStatusDict;

    private HttpClient() {
        client = new AsyncHttpClient();
    }

    private static class HttpClientHolder {
        private static HttpClient httpclient = new HttpClient();
    }

    public static HttpClient getInstance() {
        return HttpClientHolder.httpclient;
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }


    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public void login(Context context, OnHttpListener listener, RequestParams params) {
        client.get(context, getAbsoluteUrl(LOGIN), null, params, new FactoryHttpResponseHandler(context, listener, REQUEST_LOGIN));
    }

    public void requestDeviceDict(Context context, OnHttpListener listener, int dict) {
        RequestParams params = new RequestParams();
        switch (dict) {
            case Constant.DICT_DEVICE_STATUS:
                params.add("code", "device_use_status");
                break;
            case Constant.DICT_DEVICE_IDENTIFY:
                params.add("code", "device_device_flag");
                break;
            case Constant.DICT_TROUBLE_LEVEL:
                params.add("code", "device_trouble_level");
                break;
            case Constant.DICT_TROUBLE_REASON:
                params.add("code", "device_trouble_reason");
                break;
            case Constant.DICT_MAINTAIN_LEVEL:
                params.add("code", "device_maintenance_level");
                break;
            case Constant.DICT_REPAIR_LEVEL:
                params.add("code", "device_repair_level");
                break;
            case Constant.DICT_VERIFY_COMMENT:
                params.add("code", "device_bad_review_reason");
                break;
            case Constant.DICT_DEVICE_RUNNING_STATUS:
                params.add("code", "device_running_status");
                break;
            case Constant.DICT_REPAIR_STATUS:
                params.add("code", "repair_status");
                break;
            default:
                return;
        }
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(DATA_DICT), headers.toArray(new Header[headers.size()]), params, new FactoryHttpResponseHandler(context, listener, REQUEST_DATA_DICT, dict));
    }

    public List<DeviceParamDicEnumResp> getDictEnum(int requestType) {
        switch (requestType) {
            case Constant.DICT_DEVICE_STATUS:
                return deviceUseStatusDict;
            case Constant.DICT_DEVICE_IDENTIFY:
                return deviceIdentifyDict;
            case Constant.DICT_TROUBLE_LEVEL:
                return troubleLevelDict;
            case Constant.DICT_TROUBLE_REASON:
                return troubleReasonDict;
            case Constant.DICT_MAINTAIN_LEVEL:
                return maintainLevelDict;
            case Constant.DICT_REPAIR_LEVEL:
                return repairLevelDict;
            case Constant.DICT_VERIFY_COMMENT:
                return verifyCommentDict;
            case Constant.DICT_DEVICE_RUNNING_STATUS:
                return deviceRunningStatusDict;
            case Constant.DICT_REPAIR_STATUS:
                return repairStatusDict;
            default:
                return null;
        }
    }

    public void requestContactCompany(Context context, OnHttpListener listener) {
        ContactCompanyReq req = new ContactCompanyReq();
        req.setCurrentPage(0);
        req.setItemsPerPage(1000);
        StringEntity params = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(CONTACT_COMPANY), headers.toArray(new Header[headers.size()]), params, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, REQUEST_CONTACT_COMPANY));
    }

    public void requestRepairGroup(Context context, OnHttpListener listener) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(REPAIR_GROUP_URL), headers.toArray(new Header[headers.size()]), null, new FactoryHttpResponseHandler(context, listener, REQUEST_REPAIR_GROUP_URL));
    }

    public void requestDeviceType(Context context, OnHttpListener listener) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(DEVICE_TYPE_URL), headers.toArray(new Header[headers.size()]), null, new FactoryHttpResponseHandler(context, listener, REQUEST_DEVICE_TYPE_URL));
    }

    public void requestTroubleType(Context context, OnHttpListener listener) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(DEVICE_TROUBLE_TYPE_URL), headers.toArray(new Header[headers.size()]), null, new FactoryHttpResponseHandler(context, listener, REQUEST_DEVICE_TROUBLE_TYPE_URL));
    }

    public void requestPartsType(Context context, OnHttpListener listener) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(PARTS_TYPE_URL), headers.toArray(new Header[headers.size()]), null, new FactoryHttpResponseHandler(context, listener, REQUEST_PARTS_TYPE_URL));
    }

    public void requestDeviceByCode(Context context, OnHttpListener listener, RequestParams params) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(DEVICE_GET_BY_CODE), headers.toArray(new Header[headers.size()]), params, new FactoryHttpResponseHandler(context, listener, REQUEST_DEVICE_GET_BY_CODE));
    }

    public void requestDeviceOfParts(Context context, OnHttpListener listener, QueryDeviceSpareRelReq req) {
        StringEntity param = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(HttpClient.FIND_PARTS_FOR_DEVICE), headers.toArray(new Header[headers.size()]), param, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, REQUEST_FIND_PARTS_FOR_DEVICE));
    }

    public void requestDeptList(Context context, OnHttpListener listener) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(DEPARTMENT_LIST_URL), headers.toArray(new Header[headers.size()]), null, new FactoryHttpResponseHandler(context, listener, REQUEST_DEPARTMENT_LIST_URL));
    }

    public void requestTroubleCount(Context context, OnHttpListener listener) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(TROUBLE_COUNT), headers.toArray(new Header[headers.size()]), null, new FactoryHttpResponseHandler(context, listener, REQUEST_TROUBLE_COUNT));
    }

    public void requestMemberByRole(Context context, OnHttpListener listener, RequestParams params) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(MEMBER_LIST_BY_ROLE), headers.toArray(new Header[headers.size()]), params, new FactoryHttpResponseHandler(context, listener, REQUEST_MEMBER_LIST_BY_ROLE));
    }

    public void requestResetPasswd(Context context, OnHttpListener listener, RequestParams params) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(PASSWD_RESET), headers.toArray(new Header[headers.size()]), params, new FactoryHttpResponseHandler(context, listener, REQUEST_PASSWD_RESET));
    }

    public void requestDeviceList(Context context, OnHttpListener listener, DeviceInfoReq req) {
        StringEntity param = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(DEVICE_LIST), headers.toArray(new Header[headers.size()]), param, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, REQUEST_DEVICE_LIST));
    }

    public void requestDeviceById(Context context, OnHttpListener listener, RequestParams params) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(DEVICE_GET), headers.toArray(new Header[headers.size()]), params, new FactoryHttpResponseHandler(context, listener, REQUEST_DEVICE_GET));
    }

    public void requestPartsList(Context context, OnHttpListener listener, SparePartsQueryReq req) {
        StringEntity param = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(PARTS_LIST), headers.toArray(new Header[headers.size()]), param, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, REQUEST_PARTS_LIST));
    }

    public void requestDeviceAdd(Context context, OnHttpListener listener, AddDeviceReq req) {
        StringEntity param = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(HttpClient.DEVICE_SAVE), headers.toArray(new Header[headers.size()]), param, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, REQUEST_DEVICE_SAVE));
    }

    public void requestReportFault(Context context, OnHttpListener listener, AddTroubleRecordReq req) {
        StringEntity param = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(HttpClient.TROUBLE_ADD), headers.toArray(new Header[headers.size()]), param, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, REQUEST_TROUBLE_ADD));
    }

    public void requestExecAudit(Context context, OnHttpListener listener, AuditTroubleReq req) {
        StringEntity param = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(HttpClient.TROUBLE_EXEC_AUDIT), headers.toArray(new Header[headers.size()]), param, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, REQUEST_TROUBLE_EXEC_AUDIT));
    }

    public void requestExecAssign(Context context, OnHttpListener listener, AssignTroubleReq req) {
        StringEntity param = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(HttpClient.TROUBLE_EXEC_ASSIGN), headers.toArray(new Header[headers.size()]), param, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, REQUEST_TROUBLE_EXEC_ASSIGN));

    }

    public void requestUserRole(Context context, OnHttpListener listener, RequestParams params) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(USER_ROLE), headers.toArray(new Header[headers.size()]), params, new FactoryHttpResponseHandler(context, listener, REQUEST_USER_ROLE));
    }

    public void requestRepairList(Context context, OnHttpListener listener, int requestType, TroubleListReq req) {
        String url = null;
        switch (requestType) {
            case REQUEST_TROUBLE_WAIT_AUDIT:
                url = TROUBLE_WAIT_AUDIT;
                break;
            case REQUEST_TROUBLE_WAIT_REPAIR:
                url = TROUBLE_WAIT_REPAIR;
                break;
            case REQUEST_TROUBLE_REPAIRING:
                url = TROUBLE_REPAIRING;
                break;
            case REQUEST_TROUBLE_WAIT_VALIDATE:
                url = TROUBLE_WAIT_VALIDATE;
                break;
            case Constant.FOR_DEVICE_ID:
            case REQUEST_TROUBLE_LIST_BY_DEVICEID:
                url = TROUBLE_LIST_BY_DEVICEID;
                break;
            case REQUEST_TROUBLE_WAIT_ASSIGN:
                url = TROUBLE_WAIT_ASSIGN;
                break;
            default:
                break;
        }
        StringEntity param = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(url), headers.toArray(new Header[headers.size()]), param, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, requestType));
    }

//    public List<WaitAuditWorkOrderVo> getRepairList(int requestType) {
//        switch (requestType) {
//            case REQUEST_TROUBLE_WAIT_AUDIT:
//                return waitAuditWorkOrderVoList;
//            case REQUEST_TROUBLE_WAIT_REPAIR:
//                return waitRepairWorkOrderVoList;
//            case REQUEST_TROUBLE_REPAIRING:
//                return repairingWorkOrderVoList;
//            case REQUEST_TROUBLE_WAIT_VALIDATE:
//                return waitVerifyWorkOrderVoList;
//            default:
//                return null;
//        }
//    }

    public void requestRepairDetail(Context context, OnHttpListener listener, RequestParams params) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(REPAIR_DETAIL_URL), headers.toArray(new Header[headers.size()]), params, new FactoryHttpResponseHandler(context, listener, REQUEST_REPAIR_DETAIL_URL));
    }

    public void requestGrabRepairTask(Context context, OnHttpListener listener, RequestParams params) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(REPAIR_GRAB_URL), headers.toArray(new Header[headers.size()]), params, new FactoryHttpResponseHandler(context, listener, REQUEST_REPAIR_GRAB_URL));
    }

    public void requestGiveUpRepairTask(Context context, OnHttpListener listener, RequestParams params) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(REPAIR_GIVEUP_URL), headers.toArray(new Header[headers.size()]), params, new FactoryHttpResponseHandler(context, listener, REQUEST_REPAIR_GIVEUP_URL));
    }

    public void requestStartRepairTask(Context context, OnHttpListener listener, StartRepairReq req) {
        StringEntity params = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(START_REPAIR_ACTION), headers.toArray(new Header[headers.size()]), params, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, REQUEST_START_REPAIR_ACTION));
    }

    public void requestEndRepairTask(Context context, OnHttpListener listener, EndRepairReq req) {
        StringEntity params = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(END_REPAIR_ACTION), headers.toArray(new Header[headers.size()]), params, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, REQUEST_END_REPAIR_ACTION));
    }

    public void requestSubmitRepairTask(Context context, OnHttpListener listener, SubmitRepairReq req) {
        StringEntity params = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(SUBMIT_REPAIR_ACTION), headers.toArray(new Header[headers.size()]), params, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, REQUEST_SUBMIT_REPAIR_ACTION));
    }

    public void requestValidateRepairTask(Context context, OnHttpListener listener, ValidateRepairReq req) {
        StringEntity params = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(VALIDATE_REPAIR_ACTION), headers.toArray(new Header[headers.size()]), params, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, REQUEST_VALIDATE_REPAIR_ACTION));
    }

    public void requestInspectTask(Context context, OnHttpListener listener) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(INSPECT_TASK), headers.toArray(new Header[headers.size()]), null, new FactoryHttpResponseHandler(context,listener, REQUEST_INSPECT_TASK));
    }

    public void requestInspectDevices(Context context, OnHttpListener listener, RequestParams params) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(INSPECT_DEVICES), headers.toArray(new Header[headers.size()]), params, new FactoryHttpResponseHandler(context, listener, REQUEST_INSPECT_DEVICES));
    }

    public void requestInspectItem(Context context, OnHttpListener listener, RequestParams params) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(INSPECT_ITEM), headers.toArray(new Header[headers.size()]), params, new FactoryHttpResponseHandler(context, listener, REQUEST_INSPECT_ITEM));
    }

    public void requestInspectSubmit(Context context, OnHttpListener listener, InspectRecordSubmitReq req) {
        StringEntity params = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(SUBMIT_INSPECT_RESULT), headers.toArray(new Header[headers.size()]), params, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, REQUEST_SUBMIT_INSPECT_RESULT));
    }

    public void requestMaintainTask(Context context, OnHttpListener listener, MaintainTaskReq req) {
        StringEntity params = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(MAINTAIN_TASK), headers.toArray(new Header[headers.size()]), params, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, REQUEST_MAINTAIN_TASK));
    }

    public void requestMaintainTaskCount(Context context, OnHttpListener listener) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(MAINTAIN_TASK_COUNT), headers.toArray(new Header[headers.size()]), null, new FactoryHttpResponseHandler(context,listener, REQUEST_MAINTAIN_TASK_COUNT));
    }

    public void requestMaintainDetail(Context context, OnHttpListener listener, RequestParams params) {
        client.setTimeout(COMMON_TIMEOUT);
        client.get(context, getAbsoluteUrl(MAINTAIN_DETAIL), headers.toArray(new Header[headers.size()]), params, new FactoryHttpResponseHandler(context,listener, REQUEST_MAINTAIN_DETAIL));
    }

    public void requestMaintainSubmit(Context context, OnHttpListener listener, MaintainDetailSubmitReq req) {
        StringEntity params = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.setTimeout(COMMON_TIMEOUT);
        client.post(context, getAbsoluteUrl(MAINTAIN_SUBMIT), headers.toArray(new Header[headers.size()]), params, CONTENT_TYPE, new FactoryHttpResponseHandler(context, listener, REQUEST_MAINTAIN_SUBMIT));
    }

    public void requestUploadFile(Context context, OnHttpListener listener, String type, Bitmap image) {
        UploadFileTask task = new UploadFileTask(type, image, new FactoryHttpResponseHandler(context, listener, REQUEST_UPLOAD_FILE));
        task.execute();

//        FileBody fileBody = new FileBody(file, ContentType.MULTIPART_FORM_DATA,"temp.jpg");
//        StringBody stringBody = new StringBody(type,ContentType.MULTIPART_FORM_DATA);
//        HttpEntity httpEntity = MultipartEntityBuilder
//                .create()
//                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
//                .addPart("file", fileBody)
//                .addPart("type",stringBody).build();
//
//        client.addHeader("token", authUser.getToken());
//        client.post(context, getAbsoluteUrl(UPLOAD_FILE), httpEntity, "multipart/form-data", new FactoryHttpResponseHandler(context, listener, REQUEST_UPLOAD_FILE));
    }

    private class UploadFileTask extends AsyncTask {
        private String type;
        private Bitmap image;
        private FactoryHttpResponseHandler handler;

        public UploadFileTask(String type, Bitmap image, FactoryHttpResponseHandler handler) {
            this.type = type;
            this.image = image;
            this.handler = handler;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Object result = null;
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(getAbsoluteUrl(UPLOAD_FILE));
            try {
                //构建文件体
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, baos);
                ByteArrayBody fileBody = new ByteArrayBody(baos.toByteArray(), ContentType.MULTIPART_FORM_DATA,"temp.jpg");
                baos.close();
//                FileBody fileBody = new FileBody(file, ContentType.MULTIPART_FORM_DATA,"temp.jpg");
                StringBody stringBody = new StringBody(type,ContentType.MULTIPART_FORM_DATA);
                HttpEntity httpEntity = MultipartEntityBuilder
                        .create()
                        .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                        .addPart("file", fileBody)
                        .addPart("type",stringBody).build();
                httpPost.setEntity(httpEntity);
                httpPost.setHeader("token",authUser.getToken());
                //发送请求
                HttpResponse response = client.execute(httpPost);
                String jsonString = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
                result = new JSONTokener(jsonString).nextValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(Object result) {
            if(result == null) {
                result = "";
            }
            handler.onSuccess(handler.getRequestType(), null, (JSONObject) result);
        }
    }

    public interface OnHttpListener {
        void onHttpSuccess(int requestType, Object obj, List<?> list);
        void onFailure(int requestType);
    }

    private class FactoryHttpResponseHandler extends JsonHttpResponseHandler {
        private OnHttpListener mListener;
        private Context mContext;
        private int mRequestType;
        private int mParam;

        public FactoryHttpResponseHandler(Context context, OnHttpListener listener, int requestType) {
            mContext = context;
            mListener = listener;
            mRequestType = requestType;
        }

        public FactoryHttpResponseHandler(Context context, OnHttpListener listener, int requestType, int param) {
            mContext = context;
            mListener = listener;
            mRequestType = requestType;
            mParam = param;
        }

        public int getRequestType() {
            return mRequestType;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Log.d("FactoryRP", response.toString());
            try {
                String errorCode = response.getString("errorCode");
                if (ErrorCode.SUCCESS.getCode().equals(errorCode)) {
                    switch (mRequestType) {
                        case REQUEST_LOGIN:
                            authUser = JSON.parseObject(response.getString("data"), AuthUser.class);
                            setAuthUser(authUser);
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_PASSWD_RESET:
                            Toast.makeText(mContext, R.string.hint_change_passwd_success, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_REPAIR_GRAB_URL:
                            Toast.makeText(mContext, R.string.hint_catch_repair_success, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_REPAIR_GIVEUP_URL:
                            Toast.makeText(mContext, R.string.hint_give_up_repair_success, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_START_REPAIR_ACTION:
                            Toast.makeText(mContext, R.string.action_start_repair, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_END_REPAIR_ACTION:
                            Toast.makeText(mContext, R.string.action_finish_repair, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_SUBMIT_REPAIR_ACTION:
                            Toast.makeText(mContext, R.string.hint_finish_repair_success, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_TROUBLE_EXEC_AUDIT:
                            Toast.makeText(mContext, R.string.hint_finish_audit_success, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_TROUBLE_EXEC_ASSIGN:
                            Toast.makeText(mContext, R.string.hint_finish_assign_success, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_VALIDATE_REPAIR_ACTION:
                            Toast.makeText(mContext, R.string.hint_finish_validate_success, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_USER_ROLE:
                            roles = JSONArray.parseArray(response.getString("data"), RoleResp.class);
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_DEVICE_LIST:
                            if(simpleDeviceInfoList == null) {
                                simpleDeviceInfoList = JSONArray.parseArray(response.getJSONObject("data").getString("list"), SimpleDeviceInfoResp.class);
                            } else {
                                simpleDeviceInfoList.addAll(JSONArray.parseArray(response.getJSONObject("data").getString("list"), SimpleDeviceInfoResp.class));
                            }
                            mListener.onHttpSuccess(mRequestType, null, simpleDeviceInfoList);
                            break;
                        case REQUEST_TROUBLE_ADD:
                            Toast.makeText(mContext, R.string.hint_report_fault_success, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_REPAIR_DETAIL_URL:
                            WorkOrderDetailVo detailVo = JSON.parseObject(response.getString("data"), WorkOrderDetailVo.class);
                            mListener.onHttpSuccess(mRequestType, detailVo, null);
                            break;
                        case REQUEST_DEVICE_SAVE:
                            Toast.makeText(mContext, R.string.hint_device_save_success, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_TROUBLE_COUNT:
                            WorkOrderCountVo mTroubleCount = JSON.parseObject(response.getString("data"), WorkOrderCountVo.class);
                            mListener.onHttpSuccess(mRequestType, mTroubleCount, null);
                            break;
                        case REQUEST_PARTS_LIST:
                            if(sparePartsList == null) {
                                sparePartsList = JSONArray.parseArray(response.getJSONObject("data").getString("list"), SparePartsResp.class);
                            } else {
                                sparePartsList.addAll(JSONArray.parseArray(response.getJSONObject("data").getString("list"), SparePartsResp.class));
                            }
                            mListener.onHttpSuccess(mRequestType, null, sparePartsList);
                            break;
                        case REQUEST_MEMBER_LIST_BY_ROLE:
                            List<MaintenanceEngineer> users = JSONArray.parseArray(response.getJSONObject("data").getString("userRespList"), MaintenanceEngineer.class);
                            mListener.onHttpSuccess(mRequestType, null, users);
                            break;
                        case REQUEST_DEVICE_GET:
                        case REQUEST_DEVICE_GET_BY_CODE:
                            FullDeviceInfoResp device = JSON.parseObject(response.getString("data"), FullDeviceInfoResp.class);
                            fullDeviceInfoList.add(device);
                            mListener.onHttpSuccess(mRequestType, device, null);
                            break;
                        case REQUEST_TROUBLE_WAIT_AUDIT:
                        case REQUEST_TROUBLE_WAIT_REPAIR:
                        case REQUEST_TROUBLE_REPAIRING:
                        case REQUEST_TROUBLE_WAIT_VALIDATE:
                        case REQUEST_TROUBLE_WAIT_ASSIGN:
                        case REQUEST_TROUBLE_LIST_BY_DEVICEID:
                        case Constant.FOR_DEVICE_ID:
                            List<WaitAuditWorkOrderVo> list = JSONArray.parseArray(response.getJSONObject("data").getString("list"), WaitAuditWorkOrderVo.class);
                            mListener.onHttpSuccess(mRequestType, null, list);
                            break;
                        case REQUEST_REPAIR_GROUP_URL:
                            repairGroupList = JSONArray.parseArray(response.getString("data"), RepairGroup.class);
                            mListener.onHttpSuccess(mRequestType, null, repairGroupList);
                            break;
                        case REQUEST_DEVICE_TYPE_URL:
                            deviceTypeList = JSONArray.parseArray(response.getString("data"), DeviceType.class);
                            mListener.onHttpSuccess(mRequestType, null, deviceTypeList);
                            break;
                        case REQUEST_PARTS_TYPE_URL:
                            partsTypeList = JSONArray.parseArray(response.getString("data"), DeviceSparesTypeResp.class);
                            mListener.onHttpSuccess(mRequestType, null, partsTypeList);
                            break;
                        case REQUEST_DEVICE_TROUBLE_TYPE_URL:
                            troubleTypeVoList = JSONArray.parseArray(response.getString("data"), DeviceTroubleTypeVo.class);
                            mListener.onHttpSuccess(mRequestType, null, troubleTypeVoList);
                            break;
                        case REQUEST_FIND_PARTS_FOR_DEVICE:
                            List<SparePartsResp> sparePartsRespList = JSONArray.parseArray(response.getJSONObject("data").getString("list"), SparePartsResp.class);
                            mListener.onHttpSuccess(mRequestType, null, sparePartsRespList);
                            break;
                        case REQUEST_DEPARTMENT_LIST_URL:
                            departmentList = JSONArray.parseArray(response.getString("data"), Department.class);
                            mListener.onHttpSuccess(mRequestType, null, departmentList);
                            break;
                        case REQUEST_CONTACT_COMPANY:
//                            SharedPreferences sharedPreferences = mContext.getSharedPreferences("private_data", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("contactCompanies", response.getJSONObject("data").getString("list"));
//                            editor.apply();
                            contactCompanies = JSONArray.parseArray(response.getJSONObject("data").getString("list"), ContactCompany.class);
                            mListener.onHttpSuccess(mRequestType, null, contactCompanies);
                            break;
                        case REQUEST_DATA_DICT:
                            switch (mParam) {
                                case Constant.DICT_DEVICE_STATUS:
                                    deviceUseStatusDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                case Constant.DICT_DEVICE_IDENTIFY:
                                    deviceIdentifyDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                case Constant.DICT_TROUBLE_LEVEL:
                                    troubleLevelDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                case Constant.DICT_TROUBLE_REASON:
                                    troubleReasonDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                case Constant.DICT_MAINTAIN_LEVEL:
                                    maintainLevelDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                case Constant.DICT_REPAIR_LEVEL:
                                    repairLevelDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                case Constant.DICT_VERIFY_COMMENT:
                                    verifyCommentDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                case Constant.DICT_DEVICE_RUNNING_STATUS:
                                    deviceRunningStatusDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                case Constant.DICT_REPAIR_STATUS:
                                    repairStatusDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                default:
                                    break;
                            }
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_INSPECT_TASK:
                            List<InspectTaskResp> inspectTaskRespList = JSONArray.parseArray(response.getString("data"), InspectTaskResp.class);
                            mListener.onHttpSuccess(mRequestType, null, inspectTaskRespList);
                            break;
                        case REQUEST_INSPECT_DEVICES:
                            List<InspectDevicesResp> inspectDevicesRespList = JSONArray.parseArray(response.getString("data"), InspectDevicesResp.class);
                            mListener.onHttpSuccess(mRequestType, null, inspectDevicesRespList);
                            break;
                        case REQUEST_INSPECT_ITEM:
                            InspectDeviceDetailResp inspectItemResp = JSON.parseObject(response.getString("data"), InspectDeviceDetailResp.class);
                            mListener.onHttpSuccess(mRequestType, inspectItemResp, null);
                            break;
                        case REQUEST_SUBMIT_INSPECT_RESULT:
                            Toast.makeText(mContext, R.string.hint_finish_repair_success, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_MAINTAIN_TASK_COUNT:
                            MaintainTaskCount count = JSON.parseObject(response.getString("data"), MaintainTaskCount.class);
                            mListener.onHttpSuccess(mRequestType, count, null);
                            break;
                        case REQUEST_MAINTAIN_TASK:
                            List<MaintainTaskResp> maintainTaskRespList = JSONArray.parseArray(response.getJSONObject("data").getString("list"), MaintainTaskResp.class);
                            mListener.onHttpSuccess(mRequestType, null, maintainTaskRespList);
                            break;
                        case REQUEST_MAINTAIN_DETAIL:
                            MaintainDetailResp maintainTaskResp = JSON.parseObject(response.getString("data"), MaintainDetailResp.class);
                            mListener.onHttpSuccess(mRequestType, maintainTaskResp, null);
                            break;
                        case REQUEST_MAINTAIN_SUBMIT:
                            Toast.makeText(mContext, R.string.hint_finish_repair_success, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        case REQUEST_UPLOAD_FILE:
                            Toast.makeText(mContext, R.string.hint_upload_success, Toast.LENGTH_SHORT).show();
                            UploadImageResp uploadImageResp = JSON.parseObject(response.getString("data"), UploadImageResp.class);
                            mListener.onHttpSuccess(mRequestType, uploadImageResp, null);
                            break;
                        default:
                            break;
                    }
                } else {
                    String errorMessage = response.getString("errorMessage");
                    Toast.makeText(mContext, errorMessage + ": " + errorCode, Toast.LENGTH_SHORT).show();
                    mListener.onFailure(mRequestType);
                }
            } catch (JSONException e) {
//                Toast.makeText(mContext, R.string.failure_data_parse, Toast.LENGTH_SHORT).show();
                mListener.onFailure(mRequestType);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Toast.makeText(mContext, R.string.failure_request, Toast.LENGTH_SHORT).show();
//            if(errorResponse != null) {
//                Log.d("FactoryRP", errorResponse.toString());
//            }
            mListener.onFailure(mRequestType);
        }
    }

    public void setAuthUser(AuthUser auth) {
        headers.clear();
        if (auth != null) {
            authUser = auth;
            headers.add(new BasicHeader("Content-Type", CONTENT_TYPE));
            headers.add(new BasicHeader("token", auth.getToken()));
        }
    }
}
