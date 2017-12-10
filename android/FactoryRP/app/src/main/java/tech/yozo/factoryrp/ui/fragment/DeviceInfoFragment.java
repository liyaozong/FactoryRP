package tech.yozo.factoryrp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;

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
    private Long mParam1;
    private String mParam2;

    private List<Map<String, Object>> data = new ArrayList<>();;
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
    public static DeviceInfoFragment newInstance(Long param1, String param2) {
        DeviceInfoFragment fragment = new DeviceInfoFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getLong(ARG_PARAM1);
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
        RequestParams params = new RequestParams();
        params.put("id", mParam1);
        HttpClient client = HttpClient.getInstance();
        client.get(getActivity(), HttpClient.DEVICE_GET, params, requestDeviceDetailResponse);
    }

    private JsonHttpResponseHandler requestDeviceDetailResponse = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Log.d("INFO", response.toString());
            if(response.has("data")) {
                try {
                    FullDeviceInfoResp device = JSON.parseObject(response.getString("data"), FullDeviceInfoResp.class);
                    getData(device);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Log.d("ERROR", errorResponse.toString());
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
        map.put("value", sdf.format(device.getBuyDate()));
        data.add(map);
    }
}
