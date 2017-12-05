package tech.yozo.factoryrp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import tech.yozo.factoryrp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 维修信息页
 * A simple {@link Fragment} subclass.
 * Use the {@link RepairInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepairInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView mRepairInfoView;

    public RepairInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RepairInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RepairInfoFragment newInstance(String param1, String param2) {
        RepairInfoFragment fragment = new RepairInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repair_info, container, false);
        //TODO
        SimpleAdapter adapter = new SimpleAdapter(getContext(), getData(), R.layout.item_info_list,
                new String[]{"name","value"},
                new int[]{R.id.tv_name,R.id.tv_value});
        mRepairInfoView = (ListView) view.findViewById(R.id.lv_repair_fault_info);
        mRepairInfoView.setAdapter(adapter);
        return view;
    }

    public void onHandleRepair(View view) {
        Toast.makeText(getContext(), "我将处理这个工单", Toast.LENGTH_SHORT).show();
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "设备名称：");
        map.put("value", "台式钻床");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "所在部门：");
        map.put("value", "一车间");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "设备状态：");
        map.put("value", "带病运行");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "发现时间：");
        map.put("value", "2017-12-5 10：10：10");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "故障描述：");
        map.put("value", "电线损坏，夹断");
        list.add(map);

        return list;
    }
}
