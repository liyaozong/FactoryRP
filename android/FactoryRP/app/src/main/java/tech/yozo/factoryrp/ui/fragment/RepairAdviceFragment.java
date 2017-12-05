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
 * 维修意见页
 * A simple {@link Fragment} subclass.
 * Use the {@link RepairAdviceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepairAdviceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView mRepairAdviceView;

    public RepairAdviceFragment() {
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
    public static RepairAdviceFragment newInstance(String param1, String param2) {
        RepairAdviceFragment fragment = new RepairAdviceFragment();
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
        View view = inflater.inflate(R.layout.fragment_repair_advice, container, false);
        //TODO
        SimpleAdapter adapter = new SimpleAdapter(getContext(), getData(), R.layout.item_info_list,
                new String[]{"name","value"},
                new int[]{R.id.tv_name,R.id.tv_value});
        mRepairAdviceView = (ListView) view.findViewById(R.id.lv_repair_advice_info);
        mRepairAdviceView.setAdapter(adapter);
        return view;
    }

    public void onStartRepair(View view) {
        Toast.makeText(getContext(), "开始处理这个工单", Toast.LENGTH_SHORT).show();
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "维修班组：");
        map.put("value", "电气班");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "维修人员：");
        map.put("value", "工程师A");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "维修状态：");
        map.put("value", "正在维修");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "故障原因：");
        map.put("value", "线头松动");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "维修级别：");
        map.put("value", "突发性故障");
        list.add(map);

        return list;
    }
}
