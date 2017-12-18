package tech.yozo.factoryrp.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.device.info.SimpleDeviceInfoResp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 设备基本参数信息页
 * A simple {@link Fragment} subclass.
 * Use the {@link DeviceInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviceInfoFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    private List<Map<String, Object>> data = new ArrayList<>();
    private SimpleAdapter adapter;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

    public DeviceInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeviceInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeviceInfoFragment newInstance(int param1, String param2) {
        DeviceInfoFragment fragment = new DeviceInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device_info, container, false);
        ListView mDeviceInfoView = (ListView) view.findViewById(R.id.lv_device_info);
        adapter = new SimpleAdapter(getContext(), data, R.layout.item_info_list,
                new String[]{"name","value"},
                new int[]{R.id.tv_name,R.id.tv_value});
        mDeviceInfoView.setAdapter(adapter);
        return view;
    }

    @Override
    protected void loadData() {
        HttpClient client = HttpClient.getInstance();

        boolean exist = false;
        switch (mParam1) {
            case Constant.FOR_DEVICE_ID: {
                for (FullDeviceInfoResp device : client.getFullDeviceInfoRespList()) {
                    if (device.getId().equals(mParam2)) {
                        getData(device);
                        exist = true;
                        break;
                    }
                }
                if(!exist) {
                    RequestParams params = new RequestParams();
                    params.put("id", mParam2);
                    client.get(getActivity(), HttpClient.DEVICE_GET, params, requestDeviceDetailResponse);
                }
                break;
            }
            case Constant.FOR_DEVICE_CODE: {
                for (FullDeviceInfoResp device : client.getFullDeviceInfoRespList()) {
                    if (device.getCode().contentEquals(mParam2)) {
                        getData(device);
                        exist = true;
                        break;
                    }
                }
                if(!exist) {
                    if(client.getSimpleDeviceList() != null) {
                        for(SimpleDeviceInfoResp device : client.getSimpleDeviceList()) {
                            if(device.getCode().contentEquals(mParam2)) {
                                RequestParams params = new RequestParams();
                                params.put("id", device.getId());
                                client.get(getActivity(), HttpClient.DEVICE_GET, params, requestDeviceDetailResponse);
                                break;
                            }
                        }
                    } else {
                        //TODO
                    }
                }
                break;
            }
            default:
                break;
        }
    }

    @Override
    protected void buildUI() {

    }

    private JsonHttpResponseHandler requestDeviceDetailResponse = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Log.d("INFO", response.toString());
            if(response.has("data")) {
                try {
                    if (ErrorCode.SUCCESS.getCode().equals(response.getString("errorCode"))) {
                        FullDeviceInfoResp device = JSON.parseObject(response.getString("data"), FullDeviceInfoResp.class);
                        HttpClient client = HttpClient.getInstance();
                        client.getFullDeviceInfoRespList().add(device);
                        getData(device);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), R.string.failure_get, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), R.string.failure_data_parse, Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Toast.makeText(getContext(), R.string.failure_request, Toast.LENGTH_SHORT).show();
        }
    };

    private void getData(FullDeviceInfoResp device) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "设备名称");
        map.put("value", device.getName());
        data.add(map);

        map = new HashMap<>();
        map.put("name", "设备编号");
        map.put("value", device.getCode());
        data.add(map);

        map = new HashMap<>();
        map.put("name", "设备型号");
        map.put("value", device.getSpecification());
        data.add(map);

        map = new HashMap<>();
        map.put("name", "设备类别");
        map.put("value", device.getDeviceType());
        data.add(map);

        map = new HashMap<>();
        map.put("name", "设备标识");
        map.put("value", device.getDeviceFlag());
        data.add(map);

        map = new HashMap<>();
        map.put("name", "使用部门");
        map.put("value", device.getUseDept());
        data.add(map);

        map = new HashMap<>();
        map.put("name", "安装位置");
        map.put("value", device.getInstallationAddress());
        data.add(map);

        map = new HashMap<>();
        map.put("name", "使用状态");
        map.put("value", device.getUseStatus());
        data.add(map);

        map = new HashMap<>();
        map.put("name", "操作人");
        map.put("value", device.getOperator());
        data.add(map);

        map = new HashMap<>();
        map.put("name", "生产厂商");
        map.put("value", device.getManufacturer());
        data.add(map);

        map = new HashMap<>();
        map.put("name", "供应商");
        map.put("value", device.getSupplier());
        data.add(map);

        map = new HashMap<>();
        map.put("name", "购买时间");
        if(device.getBuyDate() != null) {
            map.put("value", sdf.format(device.getBuyDate()));
        } else {
            map.put("value", "");
        }
        data.add(map);
    }
}
