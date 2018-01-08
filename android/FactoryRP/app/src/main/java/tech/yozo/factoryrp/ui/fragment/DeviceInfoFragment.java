package tech.yozo.factoryrp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.loopj.android.http.RequestParams;
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
public class DeviceInfoFragment extends BaseFragment implements HttpClient.OnHttpListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "id";

    private int mParam_mode;
    private Long mParam_id;

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
    public static DeviceInfoFragment newInstance(int param1, Long param2) {
        DeviceInfoFragment fragment = new DeviceInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putLong(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam_mode = getArguments().getInt(ARG_PARAM1);
            mParam_id = getArguments().getLong(ARG_PARAM2);
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
        for (FullDeviceInfoResp device : client.getFullDeviceInfoRespList()) {
            if (mParam_id == device.getId()) {
                exist = true;
                getData(device);
                break;
            }
        }
        if(!exist) {
            RequestParams params = new RequestParams();
            params.put("id", mParam_id);
            client.requestDeviceById(getContext(), this, params);
        }
    }

    @Override
    protected void buildUI() {
        adapter.notifyDataSetChanged();
    }

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
        map.put("name", "设备状态");
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

    private void updateUI() {
        HttpClient client = HttpClient.getInstance();

        for (FullDeviceInfoResp device : client.getFullDeviceInfoRespList()) {
            if (device.getId().equals(mParam_id)) {
                getData(device);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        updateUI();
    }

    @Override
    public void onFailure(int requestType) {

    }
}
