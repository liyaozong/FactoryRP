package tech.yozo.factoryrp.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.RepairDetailActivity;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.req.TroubleListReq;
import tech.yozo.factoryrp.vo.resp.device.trouble.WaitAuditWorkOrderVo;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * 维修工单列表页
 * A simple {@link Fragment} subclass.
 * Use the {@link RepairRecordListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepairRecordListFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    private ListView mRepairRecordListView;
    private List<WaitAuditWorkOrderVo> troubles;

    private RepairRecordListAdapter mRepairRecordListAdapter;

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
    // TODO: Rename and change types and number of parameters
    public static RepairRecordListFragment newInstance(int param1, String param2) {
        RepairRecordListFragment fragment = new RepairRecordListFragment();
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

    @Override
    protected void loadData() {
        HttpClient client = HttpClient.getInstance();
        //TODO
        TroubleListReq req = new TroubleListReq();
        req.setCurrentPage(0);
        req.setItemsPerPage(10);
        StringEntity param = new StringEntity(JSON.toJSONString(req), Charset.forName("UTF-8"));

        switch (mParam1) {
            case R.id.textView_waitto_audit:
                client.post(getContext(), HttpClient.TROUBLE_WAIT_AUDIT, param, getTroubleListResponse);
                break;
            case R.id.textView_waitto_exec:
                client.post(getContext(), HttpClient.TROUBLE_WAIT_REPAIR, param, getTroubleListResponse);
                break;
            case R.id.textView_executing:
                client.post(getContext(), HttpClient.TROUBLE_REPAIRING, param, getTroubleListResponse);
                break;
            case R.id.textView_waitto_verify:
                client.post(getContext(), HttpClient.TROUBLE_WAIT_VALIDATE, param, getTroubleListResponse);
                break;
            default:
                break;
        }
        client.post(getContext(), HttpClient.TROUBLE_WAIT_AUDIT, param, getTroubleListResponse);
    }

    private JsonHttpResponseHandler getTroubleListResponse = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                if (ErrorCode.SUCCESS.getCode().equals(response.getString("errorCode"))) {
                    troubles = JSONArray.parseArray(response.getJSONObject("data").getString("list"), WaitAuditWorkOrderVo.class);
                    mRepairRecordListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), R.string.failure_get, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), R.string.exception_message, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Toast.makeText(getContext(), R.string.failure_request, Toast.LENGTH_SHORT).show();
        }
    };

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
            //TODO
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
