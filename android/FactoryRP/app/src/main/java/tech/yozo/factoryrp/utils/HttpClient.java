package tech.yozo.factoryrp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;
import cz.msebera.android.httpclient.conn.ConnectionPoolTimeoutException;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.vo.req.*;
import tech.yozo.factoryrp.vo.resp.ContactCompany;
import tech.yozo.factoryrp.vo.resp.DeviceParamDicEnumResp;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.device.info.SimpleDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.device.trouble.WaitAuditWorkOrderVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderCountVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderDetailVo;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class HttpClient {
    private static final String BASE_URL = "http://factoryrp.yozo.tech:9550/";
//    private static final String BASE_URL = "http://192.168.6.100:9550/";

    private static final String CONTENT_TYPE = "application/json";

    public static final String LOGIN = "api/authorization/login"; //登录
    public static final String DATA_DICT = "api/deviceParameterDictionary/findByCode"; //数据字典
    public static final String CONTACT_COMPANY = "api/contactCompany/list"; //来往单位
    public static final String DEVICE_LIST = "api/deviceInfo/listSimpleInfo"; //设备列表
    public static final String DEVICE_SAVE = "api/deviceInfo/save"; //设备添加
    public static final String DEVICE_GET = "api/deviceInfo/get"; //设备详细信息
    public static final String PARTS_LIST = "api/spareParts/queryMobileDeviceSpares"; //备件列表
    public static final String TROUBLE_ADD = "troubleRecord/add"; //故障报修
    public static final String TROUBLE_COUNT = "troubleRecord/countWorkOrderList"; //故障分类统计数值
    public static final String TROUBLE_WAIT_AUDIT = "troubleRecord/waitAuditList"; //待审核的故障
    public static final String TROUBLE_WAIT_REPAIR = "troubleRecord/waitRepairList"; //待维修的故障
    public static final String TROUBLE_REPAIRING = "troubleRecord/repairingList"; //维修中的故障
    public static final String TROUBLE_WAIT_VALIDATE = "troubleRecord/waitValidateList"; //待验证的故障
    public static final String REPAIR_GRAB_URL = "troubleRecord/obtainOrder"; //我来修
    public static final String REPAIR_GIVEUP_URL = "troubleRecord/cancelOrder"; //我不修了
    public static final String REPAIR_DETAIL_URL = "troubleRecord/get"; //故障详细信息
    public static final String START_REPAIR_ACTION = "troubleRecord/startRepair"; //开始维修
    public static final String END_REPAIR_ACTION = "troubleRecord/endRepair"; //开始维修
    public static final String SUBMIT_REPAIR_ACTION = "troubleRecord/submitRepair"; //完成维修并提交维修信息
    public static final String VALIDATE_REPAIR_ACTION = "troubleRecord/validate"; //验证维修
    public static final String FIND_PARTS_FOR_DEVICE = "deviceSpareRel/findRelSparts"; //设备关联的备件
    public static final String REPAIR_GROUP_URL = "api/repairGroup/list"; //维修工段/班组列表
    public static final String DEVICE_TYPE_URL = "api/deviceType/list"; //设备类型字典
    public static final String DEVICE_TROUBLE_TYPE_URL = "api/deviceTroubleType/queryAlldDeviceTroubleType"; //故障类型字典
    public static final String PARTS_TYPE_URL = "api/deviceSparesType/queryAllDeviceSparesType"; //备件类型字典
    public static final String DEPARTMENT_LIST_URL = "api/department/list"; //部门列表

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

    private AsyncHttpClient client;
    private List<Header> headers = new ArrayList<>();

    @Getter
    private AuthUser authUser;

    @Getter
    @Setter
    private WorkOrderCountVo mTroubleCount;

    @Getter
    @Setter
    private List<SimpleDeviceInfoResp> simpleDeviceList;

    @Getter
    @Setter
    private List<SparePartsResp> sparePartsRespList;

    @Getter
    @Setter
    private List<FullDeviceInfoResp> fullDeviceInfoRespList = new ArrayList<>();

    private List<WaitAuditWorkOrderVo> waitAuditWorkOrderVoList;

    private List<WaitAuditWorkOrderVo> waitRepairWorkOrderVoList;

    private List<WaitAuditWorkOrderVo> repairingWorkOrderVoList;

    private List<WaitAuditWorkOrderVo> waitVerifyWorkOrderVoList;

    @Getter
    @Setter
    private List<ContactCompany> contactCompanies;

    private List<DeviceParamDicEnumResp> deviceUseStatusDict;

    private List<DeviceParamDicEnumResp> deviceIdentifyDict;

    private List<DeviceParamDicEnumResp> troubleLevelDict;

    private List<DeviceParamDicEnumResp> troubleReasonDict;

    private List<DeviceParamDicEnumResp> repairLevelDict;

    private List<DeviceParamDicEnumResp> maintainLevelDict;

    private List<DeviceParamDicEnumResp> verifyCommentDict;


    private HttpClient() {
//        AsyncHttpClient.blockRetryExceptionClass(UnknownHostException.class);
//        AsyncHttpClient.blockRetryExceptionClass(ConnectionPoolTimeoutException.class);
//        AsyncHttpClient.allowRetryExceptionClass(IOException.class);
//        AsyncHttpClient.allowRetryExceptionClass(SocketTimeoutException.class);
//        AsyncHttpClient.allowRetryExceptionClass(ConnectTimeoutException.class);
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
        client.get(context, getAbsoluteUrl(LOGIN), null, params, new YozoHttpResponseHandler(context, listener, REQUEST_LOGIN));
    }

    public void syncData(Context context, OnHttpListener listener) {
        //TODO
        requestDeviceDict(context, listener, Constant.DICT_DEVICE_STATUS);
        requestDeviceDict(context, listener, Constant.DICT_DEVICE_IDENTIFY);
        requestDeviceDict(context, listener, Constant.DICT_TROUBLE_LEVEL);
        requestDeviceDict(context, listener, Constant.DICT_TROUBLE_REASON);
        requestDeviceDict(context, listener, Constant.DICT_MAINTAIN_LEVEL);
        requestDeviceDict(context, listener, Constant.DICT_REPAIR_LEVEL);
        requestDeviceDict(context, listener, Constant.DICT_VERIFY_COMMENT);
        requestContactCompany(context, listener);
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
            default:
                return;
        }

        client.get(context, getAbsoluteUrl(DATA_DICT), headers.toArray(new Header[headers.size()]), params, new YozoHttpResponseHandler(context, listener, REQUEST_DATA_DICT, dict));
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
            default:
                return null;
        }
    }

    public void requestContactCompany(Context context, OnHttpListener listener) {
        ContactCompanyReq req = new ContactCompanyReq();
        req.setCurrentPage(1);
        req.setItemsPerPage(100);
        StringEntity params = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.post(context, getAbsoluteUrl(CONTACT_COMPANY), headers.toArray(new Header[headers.size()]), params, CONTENT_TYPE, new YozoHttpResponseHandler(context, listener, REQUEST_CONTACT_COMPANY));
    }

    public void requestRepairGroup(Context context, OnHttpListener listener) {
        client.get(context, getAbsoluteUrl(REPAIR_GROUP_URL), headers.toArray(new Header[headers.size()]), null, new YozoHttpResponseHandler(context, listener, REQUEST_REPAIR_GROUP_URL));
    }

    public void requestDeviceType(Context context, OnHttpListener listener) {
        client.get(context, getAbsoluteUrl(DEVICE_TYPE_URL), headers.toArray(new Header[headers.size()]), null, new YozoHttpResponseHandler(context, listener, REQUEST_DEVICE_TYPE_URL));
    }

    public void requestTroubleType(Context context, OnHttpListener listener) {
        client.get(context, getAbsoluteUrl(DEVICE_TROUBLE_TYPE_URL), headers.toArray(new Header[headers.size()]), null, new YozoHttpResponseHandler(context, listener, REQUEST_DEVICE_TROUBLE_TYPE_URL));
    }

    public void requestPartsType(Context context, OnHttpListener listener) {
        client.get(context, getAbsoluteUrl(PARTS_TYPE_URL), headers.toArray(new Header[headers.size()]), null, new YozoHttpResponseHandler(context, listener, REQUEST_PARTS_TYPE_URL));
    }

    public void requestDeptList(Context context, OnHttpListener listener) {
        client.get(context, getAbsoluteUrl(DEPARTMENT_LIST_URL), headers.toArray(new Header[headers.size()]), null, new YozoHttpResponseHandler(context, listener, REQUEST_DEPARTMENT_LIST_URL));
    }

    public void requestTroubleCount(Context context, OnHttpListener listener) {
        client.get(context, getAbsoluteUrl(TROUBLE_COUNT), headers.toArray(new Header[headers.size()]), null, new YozoHttpResponseHandler(context, listener, REQUEST_TROUBLE_COUNT));
    }

    public void requestDeviceList(Context context, OnHttpListener listener) {
        DeviceInfoReq req = new DeviceInfoReq();
        req.setCurrentPage(0);
        req.setItemsPerPage(100);

        StringEntity param = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.post(context, getAbsoluteUrl(DEVICE_LIST), headers.toArray(new Header[headers.size()]), param, CONTENT_TYPE, new YozoHttpResponseHandler(context, listener, REQUEST_DEVICE_LIST));
    }

    public void getDeviceById(Context context, OnHttpListener listener, RequestParams params) {
        client.get(context, getAbsoluteUrl(DEVICE_GET), headers.toArray(new Header[headers.size()]), params, new YozoHttpResponseHandler(context, listener, REQUEST_DEVICE_GET));
    }

    public void requestPartsList(Context context, OnHttpListener listener) {
        SparePartsQueryReq req = new SparePartsQueryReq();
        req.setCurrentPage(0);
        req.setItemsPerPage(100);

        StringEntity param = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.post(context, getAbsoluteUrl(PARTS_LIST), headers.toArray(new Header[headers.size()]), param, CONTENT_TYPE, new YozoHttpResponseHandler(context, listener, REQUEST_PARTS_LIST));
    }

    public void deviceAdd(Context context, OnHttpListener listener, AddDeviceReq req) {
        StringEntity param = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.post(context, getAbsoluteUrl(HttpClient.DEVICE_SAVE), headers.toArray(new Header[headers.size()]), param, CONTENT_TYPE, new YozoHttpResponseHandler(context, listener, REQUEST_DEVICE_SAVE));
    }

    public void reportFault(Context context, OnHttpListener listener, AddTroubleRecordReq req) {
        StringEntity param = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.post(context, getAbsoluteUrl(HttpClient.TROUBLE_ADD), headers.toArray(new Header[headers.size()]), param, CONTENT_TYPE, new YozoHttpResponseHandler(context, listener, REQUEST_TROUBLE_ADD));
    }

    public void requestRepairList(Context context, OnHttpListener listener, int requestType) {
        requestRepairList(context, listener, requestType, -1);
    }

    public void requestRepairList(Context context, OnHttpListener listener, int requestType, long deviceId) {
        TroubleListReq req = new TroubleListReq();
        req.setCurrentPage(0);
        req.setItemsPerPage(100);

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
                req.setDeviceId(deviceId);
            default:
                break;
        }
        StringEntity param = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.post(context, getAbsoluteUrl(url), headers.toArray(new Header[headers.size()]), param, CONTENT_TYPE, new YozoHttpResponseHandler(context, listener, requestType));
    }

    public List<WaitAuditWorkOrderVo> getRepairList(int requestType) {
        switch (requestType) {
            case REQUEST_TROUBLE_WAIT_AUDIT:
                return waitAuditWorkOrderVoList;
            case REQUEST_TROUBLE_WAIT_REPAIR:
                return waitRepairWorkOrderVoList;
            case REQUEST_TROUBLE_REPAIRING:
                return repairingWorkOrderVoList;
            case REQUEST_TROUBLE_WAIT_VALIDATE:
                return waitVerifyWorkOrderVoList;
            default:
                return null;
        }
    }

    public void requestRepairDetail(Context context, OnHttpListener listener, RequestParams params) {
        client.get(context, getAbsoluteUrl(REPAIR_DETAIL_URL), headers.toArray(new Header[headers.size()]), params, new YozoHttpResponseHandler(context, listener, REQUEST_REPAIR_DETAIL_URL));
    }

    public void grabRepairTask(Context context, OnHttpListener listener, RequestParams params) {
        client.get(context, getAbsoluteUrl(REPAIR_GRAB_URL), headers.toArray(new Header[headers.size()]), params, new YozoHttpResponseHandler(context, listener, REQUEST_REPAIR_GRAB_URL));
    }

    public void giveUpRepairTask(Context context, OnHttpListener listener, RequestParams params) {
        client.get(context, getAbsoluteUrl(REPAIR_GIVEUP_URL), headers.toArray(new Header[headers.size()]), params, new YozoHttpResponseHandler(context, listener, REQUEST_REPAIR_GIVEUP_URL));
    }

    public void startRepairTask(Context context, OnHttpListener listener, StartRepairReq req) {
        StringEntity params = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.post(context, getAbsoluteUrl(START_REPAIR_ACTION), headers.toArray(new Header[headers.size()]), params, CONTENT_TYPE, new YozoHttpResponseHandler(context, listener, REQUEST_START_REPAIR_ACTION));
    }

    public void endRepairTask(Context context, OnHttpListener listener, EndRepairReq req) {
        StringEntity params = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.post(context, getAbsoluteUrl(END_REPAIR_ACTION), headers.toArray(new Header[headers.size()]), params, CONTENT_TYPE, new YozoHttpResponseHandler(context, listener, REQUEST_END_REPAIR_ACTION));
    }

    public void submitRepairTask(Context context, OnHttpListener listener, SubmitRepairReq req) {
        StringEntity params = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.post(context, getAbsoluteUrl(SUBMIT_REPAIR_ACTION), headers.toArray(new Header[headers.size()]), params, CONTENT_TYPE, new YozoHttpResponseHandler(context, listener, REQUEST_SUBMIT_REPAIR_ACTION));
    }

    public void validateRepairTask(Context context, OnHttpListener listener, ValidateRepairReq req) {
        StringEntity params = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.post(context, getAbsoluteUrl(VALIDATE_REPAIR_ACTION), headers.toArray(new Header[headers.size()]), params, CONTENT_TYPE, new YozoHttpResponseHandler(context, listener, REQUEST_VALIDATE_REPAIR_ACTION));
    }

    public interface OnHttpListener {
        void onHttpSuccess(int requestType, Object obj, List<?> list);
        void onFailure(int requestType);
    }

    private class YozoHttpResponseHandler extends JsonHttpResponseHandler {
        private OnHttpListener mListener;
        private Context mContext;
        private int mRequestType;
        private int mParam;

        public YozoHttpResponseHandler(Context context, OnHttpListener listener, int requestType) {
            mContext = context;
            mListener = listener;
            mRequestType = requestType;
        }

        public YozoHttpResponseHandler(Context context, OnHttpListener listener, int requestType, int param) {
            mContext = context;
            mListener = listener;
            mRequestType = requestType;
            mParam = param;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                String errorCode = response.getString("errorCode");
                if (ErrorCode.SUCCESS.getCode().equals(errorCode)) {
                    switch (mRequestType) {
                        case REQUEST_LOGIN: {
                            authUser = JSON.parseObject(response.getString("data"), AuthUser.class);
                            setAuthUser(authUser);
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        }
                        case REQUEST_DATA_DICT: {
//                            SharedPreferences sharedPreferences = mContext.getSharedPreferences("private_data", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            switch (mParam) {
                                case Constant.DICT_DEVICE_STATUS:
//                                    editor.putString("deviceUseStatusDict", response.getString("data"));
                                    deviceUseStatusDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                case Constant.DICT_DEVICE_IDENTIFY:
//                                    editor.putString("deviceIdentifyDict", response.getString("data"));
                                    deviceIdentifyDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                case Constant.DICT_TROUBLE_LEVEL:
//                                    editor.putString("troubleLevelDict", response.getString("data"));
                                    troubleLevelDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                case Constant.DICT_TROUBLE_REASON:
//                                    editor.putString("troubleReasonDict", response.getString("data"));
                                    troubleReasonDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                case Constant.DICT_MAINTAIN_LEVEL:
//                                    editor.putString("maintainLevelDict", response.getString("data"));
                                    maintainLevelDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                case Constant.DICT_REPAIR_LEVEL:
//                                    editor.putString("repairLevelDict", response.getString("data"));
                                    repairLevelDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                case Constant.DICT_VERIFY_COMMENT:
//                                    editor.putString("verifyCommentDict", response.getString("data"));
                                    verifyCommentDict = JSON.parseArray(response.getString("data"), DeviceParamDicEnumResp.class);
                                    break;
                                default:
                                    break;
                            }
                            mListener.onHttpSuccess(mRequestType, null, null);
//                            editor.apply();
                            break;
                        }
                        case REQUEST_CONTACT_COMPANY: {
//                            SharedPreferences sharedPreferences = mContext.getSharedPreferences("private_data", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("contactCompanies", response.getJSONObject("data").getString("list"));
//                            editor.apply();
                            contactCompanies = JSONArray.parseArray(response.getJSONObject("data").getString("list"), ContactCompany.class);
                            mListener.onHttpSuccess(mRequestType, null, contactCompanies);
                            break;
                        }
                        case REQUEST_REPAIR_GRAB_URL: {
                            Toast.makeText(mContext, R.string.hint_catch_repair_success, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        }
                        case REQUEST_REPAIR_GIVEUP_URL: {
                            Toast.makeText(mContext, R.string.hint_give_up_repair_success, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        }
                        case REQUEST_START_REPAIR_ACTION: {
                            //TODO
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        }
                        case REQUEST_SUBMIT_REPAIR_ACTION: {
                            //TODO
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        }
                        case REQUEST_DEVICE_LIST: {
                            simpleDeviceList = JSONArray.parseArray(response.getJSONObject("data").getString("list"), SimpleDeviceInfoResp.class);
                            mListener.onHttpSuccess(mRequestType, null, simpleDeviceList);
                            break;
                        }
                        case REQUEST_TROUBLE_ADD: {
                            Toast.makeText(mContext, R.string.hint_report_fault_success, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        }
                        case REQUEST_REPAIR_DETAIL_URL: {
                            WorkOrderDetailVo detailVo = JSON.parseObject(response.getString("data"), WorkOrderDetailVo.class);
                            mListener.onHttpSuccess(mRequestType, detailVo, null);
                            break;
                        }
                        case REQUEST_DEVICE_SAVE: {
                            Toast.makeText(mContext, R.string.hint_device_save_success, Toast.LENGTH_SHORT).show();
                            mListener.onHttpSuccess(mRequestType, null, null);
                            break;
                        }
                        case REQUEST_TROUBLE_COUNT: {
                            mTroubleCount = JSON.parseObject(response.getString("data"), WorkOrderCountVo.class);
                            mListener.onHttpSuccess(mRequestType, mTroubleCount, null);
                            break;
                        }
                        case REQUEST_PARTS_LIST: {
                            sparePartsRespList = JSONArray.parseArray(response.getJSONObject("data").getString("list"), SparePartsResp.class);
                            mListener.onHttpSuccess(mRequestType, null, sparePartsRespList);
                            break;
                        }
                        case REQUEST_DEVICE_GET: {
                            FullDeviceInfoResp device = JSON.parseObject(response.getString("data"), FullDeviceInfoResp.class);
                            fullDeviceInfoRespList.add(device);
                            mListener.onHttpSuccess(mRequestType, device, null);
                            break;
                        }
                        case REQUEST_TROUBLE_WAIT_AUDIT:
                            waitAuditWorkOrderVoList = JSONArray.parseArray(response.getJSONObject("data").getString("list"), WaitAuditWorkOrderVo.class);
                            mListener.onHttpSuccess(mRequestType, null, waitAuditWorkOrderVoList);
                            break;
                        case REQUEST_TROUBLE_WAIT_REPAIR:
                            waitRepairWorkOrderVoList = JSONArray.parseArray(response.getJSONObject("data").getString("list"), WaitAuditWorkOrderVo.class);
                            mListener.onHttpSuccess(mRequestType, null, waitRepairWorkOrderVoList);
                            break;
                        case REQUEST_TROUBLE_REPAIRING:
                            repairingWorkOrderVoList = JSONArray.parseArray(response.getJSONObject("data").getString("list"), WaitAuditWorkOrderVo.class);
                            mListener.onHttpSuccess(mRequestType, null, repairingWorkOrderVoList);
                            break;
                        case REQUEST_TROUBLE_WAIT_VALIDATE:
                            waitVerifyWorkOrderVoList = JSONArray.parseArray(response.getJSONObject("data").getString("list"), WaitAuditWorkOrderVo.class);
                            mListener.onHttpSuccess(mRequestType, null, waitVerifyWorkOrderVoList);
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
                Toast.makeText(mContext, R.string.failure_data_parse, Toast.LENGTH_SHORT).show();
                mListener.onFailure(mRequestType);
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Toast.makeText(mContext, R.string.failure_request, Toast.LENGTH_SHORT).show();
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
