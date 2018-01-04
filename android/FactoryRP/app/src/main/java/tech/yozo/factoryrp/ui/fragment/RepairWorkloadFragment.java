package tech.yozo.factoryrp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import tech.yozo.factoryrp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 维修工作量页
 * A simple {@link Fragment} subclass.
 * Use the {@link RepairWorkloadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepairWorkloadFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "id";

    private int mParam_mode;
    private long mParam_id;

    private ListView mRepairWorkloadView;

    public RepairWorkloadFragment() {
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
    public static RepairWorkloadFragment newInstance(int param1, long param2) {
        RepairWorkloadFragment fragment = new RepairWorkloadFragment();
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
        View view = inflater.inflate(R.layout.fragment_repair_workload, container, false);
        //TODO
        SimpleAdapter adapter = new SimpleAdapter(getContext(), getData(), R.layout.item_info_list,
                new String[]{"name","value"},
                new int[]{R.id.tv_name,R.id.tv_value});
        mRepairWorkloadView = (ListView) view.findViewById(R.id.lv_repair_workload);
        mRepairWorkloadView.setAdapter(adapter);
        return view;
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "工程师B：");
        map.put("value", "维修用时：4小时");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "工程师C：");
        map.put("value", "维修用时：4小时");
        list.add(map);

        return list;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void buildUI() {

    }
}
