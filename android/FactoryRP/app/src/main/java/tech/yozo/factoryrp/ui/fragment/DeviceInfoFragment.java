package tech.yozo.factoryrp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.vo.DeviceInfo;

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
public class DeviceInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private DeviceInfo mParam1;
    private String mParam2;

    private ListView mDeviceInfoView;

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
    public static DeviceInfoFragment newInstance(DeviceInfo param1, String param2) {
        DeviceInfoFragment fragment = new DeviceInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (DeviceInfo) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device_info, container, false);
        //TODO
        SimpleAdapter adapter = new SimpleAdapter(getContext(), getData(), R.layout.item_info_list,
                new String[]{"name","value"},
                new int[]{R.id.tv_name,R.id.tv_value});
        mDeviceInfoView = (ListView) view.findViewById(R.id.lv_device_info);
        mDeviceInfoView.setAdapter(adapter);
        return view;
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "设备名称");
        map.put("value", mParam1.getName());
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "设备编号");
        map.put("value", mParam1.getCode());
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "购买时间");
        map.put("value", mParam1.getInstallationAddress());
        list.add(map);

        return list;
    }
}
