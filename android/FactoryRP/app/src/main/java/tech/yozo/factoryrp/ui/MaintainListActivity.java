package tech.yozo.factoryrp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.req.MaintainTaskReq;
import tech.yozo.factoryrp.vo.resp.MaintainTaskResp;

import java.util.List;

public class MaintainListActivity extends AppCompatActivity implements HttpClient.OnHttpListener {

    @BindView(R.id.lv_maintain_record)
    ListView lvMaintainRecord;

    private List<MaintainTaskResp> maintainTaskRespList;
    private MaintainListAdapter maintainListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_list);
        ButterKnife.bind(this);

        int type = getIntent().getIntExtra("type", 1);
        MaintainTaskReq req = new MaintainTaskReq();
        req.setPlanType(type);
        HttpClient.getInstance().requestMaintainTask(this, this, req);

        maintainListAdapter = new MaintainListAdapter(this);
        lvMaintainRecord.setEmptyView(findViewById(R.id.tv19));
        lvMaintainRecord.setAdapter(maintainListAdapter);
        lvMaintainRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MaintainListActivity.this, MaintainDetailActivity.class);
                intent.putExtra("MAINTAIN_ID", maintainTaskRespList.get(i).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        if(requestType == HttpClient.REQUEST_MAINTAIN_TASK) {
            maintainTaskRespList = (List<MaintainTaskResp>) list;
            maintainListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(int requestType) {

    }

    private static class ViewHolder
    {
        TextView name;
        TextView code;
        TextView type;
        TextView level;
        TextView maintainer;
        TextView next_time;
    }

    private class MaintainListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        private MaintainListAdapter(Context context)
        {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if(maintainTaskRespList != null) {
                return maintainTaskRespList.size();
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
                convertView = mInflater.inflate(R.layout.item_maintain_list, null);
                holder.name = (TextView)convertView.findViewById(R.id.tv_maintain_device_name);
                holder.code = (TextView)convertView.findViewById(R.id.tv_maintain_device_code);
                holder.type = (TextView) convertView.findViewById(R.id.tv_maintain_device_type);
                holder.level = (TextView) convertView.findViewById(R.id.tv_maintain_level);
                holder.maintainer = (TextView) convertView.findViewById(R.id.tv_maintainer);
                holder.next_time = (TextView) convertView.findViewById(R.id.tv_next_time);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.name.setText(maintainTaskRespList.get(i).getDeviceName());
            holder.code.setText(maintainTaskRespList.get(i).getDeviceCode());
            holder.type.setText(maintainTaskRespList.get(i).getDeviceSpec());
            holder.level.setText(String.valueOf(maintainTaskRespList.get(i).getMaintainLevel()));
            holder.maintainer.setText(maintainTaskRespList.get(i).getPlanManagerName());
            holder.next_time.setText(maintainTaskRespList.get(i).getNexMaintainTime());

            return convertView;
        }
    }
}
