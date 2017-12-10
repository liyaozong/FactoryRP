package tech.yozo.factoryrp.utils;

import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import lombok.Getter;
import lombok.Setter;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;

import java.util.ArrayList;
import java.util.List;


public class HttpClient {
    private static final String BASE_URL = "http://factoryrp.yozo.tech:9550/";

    public static final String LOGIN = "api/authorization/login";
    public static final String DEVICE_LIST = "api/deviceInfo/listSimpleInfo";
    public static final String DEVICE_SAVE = "api/deviceInfo/save";
    public static final String DEVICE_GET = "api/deviceInfo/get";

    public static final String PARTS_LIST = "api/spareParts/findByPage";

    public static final String FAULT_LIST = "troubleRecord/list";

    private AsyncHttpClient client;
    private List<Header> headers = new ArrayList<>();

    @Getter
    private AuthUser authUser;

    private HttpClient() {
        client = new AsyncHttpClient();
    }

    private static class HttpClientHolder {
        private static HttpClient httpclient = new HttpClient();
    }

    public static HttpClient getInstance() {
        return HttpClientHolder.httpclient;
    }

    public void get(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(context, getAbsoluteUrl(url), headers.toArray(new Header[headers.size()]), params, responseHandler);
    }

    public void post(Context context, String url, HttpEntity params, AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url), headers.toArray(new Header[headers.size()]), params, "application/json", responseHandler);
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public void setAuthUser(AuthUser auth) {
        authUser = auth;
        headers.clear();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        headers.add(new BasicHeader("token", auth.getToken()));
    }
}
