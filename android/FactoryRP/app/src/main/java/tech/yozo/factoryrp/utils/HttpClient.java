package tech.yozo.factoryrp.utils;

import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

import java.util.ArrayList;
import java.util.List;


public class HttpClient {
    private static final String BASE_URL = "http://factoryrp.yozo.tech:9550/";

    public static final String LOGININ = "api/authorization/login";
    public static final String DEVICE_LIST = "deviceInfo/list";

    private AsyncHttpClient client;
    private List<Header> headers;

    private HttpClient() {
        client = new AsyncHttpClient();
        headers = new ArrayList<Header>();
    }

    private static class HttpClientHolder {
        private static HttpClient httpclient = new HttpClient();
    }

    public static HttpClient getInstance() {
        return HttpClientHolder.httpclient;
    }

    public void setHeaderParam(String headerName, String headerValue) {
        headers.add(new BasicHeader(headerName, headerValue));
    }

    public Header[] getHeaders() {
        return headers.toArray(new Header[headers.size()]);
    }

    public void get(Context context, String url, Header[] headers, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(context, getAbsoluteUrl(url), headers, params, responseHandler);
    }

    public void post(Context context, String url, Header[] headers, RequestParams params,  String contentType, AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url), headers, params, contentType, responseHandler);
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
