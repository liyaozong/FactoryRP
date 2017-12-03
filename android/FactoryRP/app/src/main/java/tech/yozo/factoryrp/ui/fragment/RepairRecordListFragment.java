package tech.yozo.factoryrp.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.RepairDetailActivity;
import tech.yozo.factoryrp.vo.DeviceInfo;

import java.util.List;


/**
 * 维修工单列表页
 * A simple {@link Fragment} subclass.
 * Use the {@link RepairRecordListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepairRecordListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView mRepairRecordListView;

    private List<DeviceInfo> mRepairRecordList;

    private RepairRecordListAdapter mRepairRecordListAdapter;

    public RepairRecordListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RepairRecordListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RepairRecordListFragment newInstance(String param1, String param2) {
        RepairRecordListFragment fragment = new RepairRecordListFragment();
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
        View view = inflater.inflate(R.layout.fragment_repair_record_list, container, false);
        mRepairRecordListView = (ListView) view.findViewById(R.id.lv_repair_record);
        mRepairRecordListView.setEmptyView(view.findViewById(R.id.textView19));
        mRepairRecordListAdapter = new RepairRecordListAdapter(getContext());
        mRepairRecordListView.setAdapter(mRepairRecordListAdapter);
        mRepairRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int count,
                                    long arg3) {
                Intent intent = new Intent(getActivity(), RepairDetailActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private static class ViewHolder
    {
        TextView name;
        TextView code;
        TextView time;
        TextView repair_no;
        TextView fault_level;
        TextView repair_worker;
        TextView repair_status;
    }

    private class RepairRecordListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        private RepairRecordListAdapter(Context context)
        {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if(mRepairRecordList != null) {
                return mRepairRecordList.size();
            }
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_repair_list, null);
                holder.name = (TextView)convertView.findViewById(R.id.tv_repair_device_name);
                holder.code = (TextView)convertView.findViewById(R.id.tv_repair_device_code);
                holder.time = (TextView) convertView.findViewById(R.id.tv_repair_find_time);
                holder.repair_no = (TextView) convertView.findViewById(R.id.tv_repair_no);
                holder.fault_level = (TextView) convertView.findViewById(R.id.tv_repair_fault_level);
                holder.repair_worker = (TextView) convertView.findViewById(R.id.tv_repair_worker);
                holder.repair_status = (TextView) convertView.findViewById(R.id.tv_repair_status);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.name.setText("测试name");
            holder.code.setText("测试code");
            holder.time.setText("测试time");
            holder.repair_no.setText("测试单号");
            holder.fault_level.setText("带兵运行");
            holder.repair_worker.setText("测试人员");
            holder.repair_status.setText("维修中");

            return convertView;
        }
    }
}
