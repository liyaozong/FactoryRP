package tech.yozo.factoryrp.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.RepairDetailActivity;
import tech.yozo.factoryrp.ui.dialog.LoadingDialog;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.device.trouble.WaitAuditWorkOrderVo;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * 维修工单列表页
 * A simple {@link Fragment} subclass.
 * Use the {@link RepairRecordListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepairRecordListFragment extends BaseFragment implements HttpClient.OnHttpListener {
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "id";

    private int param_mode;
    private long param_id;

    private ListView mRepairRecordListView;
    private List<WaitAuditWorkOrderVo> troubles;

    private RepairRecordListAdapter mRepairRecordListAdapter;
    private LoadingDialog dialog;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

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
    public static RepairRecordListFragment newInstance(int param1, long param2) {
        RepairRecordListFragment fragment = new RepairRecordListFragment();
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
            param_mode = getArguments().getInt(ARG_PARAM1);
            param_id = getArguments().getLong(ARG_PARAM2);
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
                intent.putExtra(RepairDetailActivity.TROUBLE_ID, troubles.get(count).getId());
                intent.putExtra(RepairDetailActivity.OPERATE_MODE, param_mode);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void buildUI() {
//        if(troubles == null) {
            requestList();
//        } else {
//            mRepairRecordListAdapter.notifyDataSetChanged();
//        }
    }

    private void requestList() {
        LoadingDialog.Builder builder = new LoadingDialog.Builder(getContext())
                .setMessage(R.string.loading_loading);
        dialog = builder.create();
        dialog.show();
        if(Constant.FOR_DEVICE_ID == param_mode) {
            HttpClient.getInstance().requestRepairList(getContext(), this, HttpClient.REQUEST_TROUBLE_LIST_BY_DEVICEID, param_id);
        } else {
            HttpClient.getInstance().requestRepairList(getContext(), this, param_mode);
        }
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        dialog.dismiss();
        if(list != null && !list.isEmpty() && list.get(0) instanceof WaitAuditWorkOrderVo) {
            troubles = (List<WaitAuditWorkOrderVo>) list;
            mRepairRecordListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(int requestType) {
        dialog.dismiss();
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
            if(troubles != null) {
                return troubles.size();
            }
            return 0;
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
            holder.name.setText(troubles.get(i).getName());
            holder.code.setText(troubles.get(i).getCode());
            holder.time.setText(sdf.format(troubles.get(i).getHappenTime()));
            holder.repair_no.setText(String.valueOf(troubles.get(i).getOrderNo()));
            holder.fault_level.setText(troubles.get(i).getTroubleLevel());
            holder.repair_worker.setText(troubles.get(i).getRepairUser());
            holder.repair_status.setText(troubles.get(i).getStatus());

            return convertView;
        }
    }
}
