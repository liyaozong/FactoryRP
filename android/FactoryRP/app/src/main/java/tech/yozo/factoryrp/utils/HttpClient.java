package tech.yozo.factoryrp.utils;

import android.content.Context;
import android.support.v4.media.session.PlaybackStateCompat;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.vo.req.ContactCompanyReq;
import tech.yozo.factoryrp.vo.resp.ContactCompany;
import tech.yozo.factoryrp.vo.resp.DeviceParamDicEnumResp;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.device.info.SimpleDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.device.trouble.WaitAuditWorkOrderVo;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpClient {
    private static final String BASE_URL = "http://factoryrp.yozo.tech:9550/";
//    private static final String BASE_URL = "http://192.168.6.100:9550/";

    public static final String LOGIN = "api/authorization/login";

    public static final String DATA_DICT = "api/deviceParameterDictionary/findByCode";

    public static final String CONTACT_COMPANY = "api/contactCompany/list";

    public static final String DEVICE_LIST = "api/deviceInfo/listSimpleInfo";
    public static final String DEVICE_SAVE = "api/deviceInfo/save";
    public static final String DEVICE_GET = "api/deviceInfo/get";

    public static final String PARTS_LIST = "api/spareParts/queryMobileDeviceSpares";

    public static final String TROUBLE_ADD = "troubleRecord/add";
    public static final String TROUBLE_LIST = "troubleRecord/list";
    public static final String TROUBLE_COUNT = "troubleRecord/countWorkOrderList";
    public static final String TROUBLE_WAIT_AUDIT = "troubleRecord/waitAuditList";
    public static final String TROUBLE_WAIT_REPAIR = "troubleRecord/waitRepairList";
    public static final String TROUBLE_REPAIRING = "troubleRecord/repairingList";
    public static final String TROUBLE_WAIT_VALIDATE = "troubleRecord/waitValidateList";

    public static final String REPAIR_CATCH_URL = "troubleRecord/obtainOrder";
    public static final String REPAIR_START_URL = "troubleRecord/startRepair";
    public static final String REPAIR_FINISH_URL = "troubleRecord/submitRepair";

    public interface OnHttpListener {
        void onHttpSuccess();
    }

    private AsyncHttpClient client;
    private List<Header> headers = new ArrayList<>();

    @Getter
    private AuthUser authUser;

    @Getter @Setter
    private List<SimpleDeviceInfoResp> simpleDeviceList;

    @Getter @Setter
    private List<SparePartsResp> sparePartsRespList;

    @Getter @Setter
    private List<FullDeviceInfoResp> fullDeviceInfoRespList = new ArrayList<>();

    @Getter @Setter
    private List<WaitAuditWorkOrderVo> waitAuditWorkOrderVoList;

    @Getter @Setter
    private List<WaitAuditWorkOrderVo> waitRepairWorkOrderVoList;

    @Getter @Setter
    private List<WaitAuditWorkOrderVo> repairingWorkOrderVoList;

    @Getter @Setter
    private List<WaitAuditWorkOrderVo> waitVerifyWorkOrderVoList;

    @Getter @Setter
    private List<DeviceParamDicEnumResp> deviceUseStatusDict;

    @Getter @Setter
    private List<DeviceParamDicEnumResp> deviceIdentifyDict;

    @Getter @Setter
    private List<DeviceParamDicEnumResp> troubleLevelDict;

    @Getter @Setter
    private List<DeviceParamDicEnumResp> troubleReasonDict;

    @Getter @Setter
    private List<DeviceParamDicEnumResp> repairLevelDict;

    @Getter @Setter
    private List<DeviceParamDicEnumResp> maintainLevelDict;

    @Getter @Setter
    private List<DeviceParamDicEnumResp> verifyCommentDict;

    @Getter @Setter
    private List<ContactCompany> contactCompanies;

    private HttpClient() {
        client = new AsyncHttpClient();
    }

    private static class HttpClientHolder {
        private static HttpClient httpclient = new HttpClient();
    }

    public static HttpClient getInstance() {
        return HttpClientHolder.httpclient;
    }

    @Deprecated
    public void get(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(context, getAbsoluteUrl(url), headers.toArray(new Header[headers.size()]), params, responseHandler);
    }

    @Deprecated
    public void post(Context context, String url, HttpEntity params, AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url), headers.toArray(new Header[headers.size()]), params, "application/json", responseHandler);
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public void setAuthUser(AuthUser auth) {
        headers.clear();
        if(auth != null) {
            authUser = auth;
            headers.add(new BasicHeader("Content-Type", "application/json"));
            headers.add(new BasicHeader("token", auth.getToken()));
        }
    }

    public void initDeviceDict(final Context context, final OnHttpListener listener, final int dict) {
        RequestParams params = new RequestParams();
        switch (dict) {
            case Constant.DICT_DEVICE_STATUS:
                if(deviceUseStatusDict != null) {
                    listener.onHttpSuccess();
                    return;
                }
                params.add("code", "device_use_status");
                break;
            case Constant.DICT_DEVICE_IDENTIFY:
                if(deviceIdentifyDict != null) {
                    listener.onHttpSuccess();
                    return;
                }
                params.add("code", "device_device_flag");
                break;
            case Constant.DICT_TROUBLE_LEVEL:
                if(troubleLevelDict != null) {
                    listener.onHttpSuccess();
                    return;
                }
                params.add("code", "device_trouble_level");
                break;
            case Constant.DICT_TROUBLE_REASON:
                if(troubleReasonDict != null) {
                    listener.onHttpSuccess();
                    return;
                }
                params.add("code", "device_trouble_reason");
                break;
            case Constant.DICT_MAINTAIN_LEVEL:
                if(maintainLevelDict != null) {
                    listener.onHttpSuccess();
                    return;
                }
                params.add("code", "device_maintenance_level");
                break;
            case Constant.DICT_REPAIR_LEVEL:
                if(repairLevelDict != null) {
                    listener.onHttpSuccess();
                    return;
                }
                params.add("code", "device_repair_level");
                break;
            case Constant.DICT_VERIFY_COMMENT:
                if(verifyCommentDict != null) {
                    listener.onHttpSuccess();
                    return;
                }
                params.add("code", "device_bad_review_reason");
                break;
            default:
                return;
        }

        client.get(context, getAbsoluteUrl(DATA_DICT), headers.toArray(new Header[headers.size()]), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (ErrorCode.SUCCESS.getCode().equals(response.getString("errorCode"))) {
                        switch (dict) {
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
                            default:
                                return;
                        }
                        listener.onHttpSuccess();
                    } else {
                        Toast.makeText(context, R.string.failure_service, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, R.string.failure_data_parse, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(context, R.string.failure_request, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getContactCompany(final Context context, final OnHttpListener listener) {
        if(contactCompanies != null) {
            listener.onHttpSuccess();
            return;
        }
        ContactCompanyReq req = new ContactCompanyReq();
        req.setCurrentPage(1);
        req.setItemsPerPage(100);
        StringEntity params = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.post(context, getAbsoluteUrl(CONTACT_COMPANY), headers.toArray(new Header[headers.size()]), params, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (ErrorCode.SUCCESS.getCode().equals(response.getString("errorCode"))) {
                        contactCompanies = JSONArray.parseArray(response.getJSONObject("data").getString("list"), ContactCompany.class);
                        listener.onHttpSuccess();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, R.string.failure_data_parse, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(context, R.string.failure_request, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void catchRepairTask(final Context context, final OnHttpListener listener, String id) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        client.get(context, getAbsoluteUrl(REPAIR_CATCH_URL), headers.toArray(new Header[headers.size()]), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (ErrorCode.SUCCESS.getCode().equals(response.getString("errorCode"))) {
                        Toast.makeText(context, R.string.hint_catch_repair_success, Toast.LENGTH_SHORT).show();
                        listener.onHttpSuccess();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, R.string.failure_data_parse, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(context, R.string.failure_request, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startRepairTask(final Context context, final OnHttpListener listener, String id) {
        //TODO
        ContactCompanyReq req = new ContactCompanyReq();
        req.setCurrentPage(1);
        req.setItemsPerPage(100);
        StringEntity params = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));
        client.post(context, getAbsoluteUrl(REPAIR_START_URL), headers.toArray(new Header[headers.size()]), params, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (ErrorCode.SUCCESS.getCode().equals(response.getString("errorCode"))) {
                        listener.onHttpSuccess();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, R.string.failure_data_parse, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(context, R.string.failure_request, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void finishRepairTask(final Context context, final OnHttpListener listener, String id) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        client.get(context, getAbsoluteUrl(REPAIR_FINISH_URL), headers.toArray(new Header[headers.size()]), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (ErrorCode.SUCCESS.getCode().equals(response.getString("errorCode"))) {
                        Toast.makeText(context, R.string.hint_finish_repair_success, Toast.LENGTH_SHORT).show();
                        listener.onHttpSuccess();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, R.string.failure_data_parse, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(context, R.string.failure_request, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
