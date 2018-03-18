package tech.yozo.factoryrp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.loopj.android.http.RequestParams;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.scan.Intents;
import tech.yozo.factoryrp.scan.ScanActivity;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.inspect.InspectDevicesResp;

import java.util.*;

public class InspectDeviceActivity extends AppCompatActivity implements HttpClient.OnHttpListener {
    private final static int SCAN_CODE = 1;
    private static final int UPDATE_INSPECT_TASK = 111;

    @BindView(R.id.lv_inspect_devices)
    ListView lvInspectDevices;
    @BindView(R.id.button_scan)
    Button buttonScan;

    private Long taskId;

    private List<InspectDevicesResp> inspectDevicesRespList;
    private InspectDevicesListAdapter mInspectDevicesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_device);
        ButterKnife.bind(this);

        taskId = getIntent().getLongExtra("INSPECT_TASK_ID", 0);
        RequestParams params = new RequestParams();
        params.put("planId", taskId);
        HttpClient.getInstance().requestInspectDevices(this, this, params);

        mInspectDevicesListAdapter = new InspectDevicesListAdapter(this);
        lvInspectDevices.setAdapter(mInspectDevicesListAdapter);

        lvInspectDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), R.string.failure_click, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.button_scan)
    public void onViewClicked() {
        startActivityForResult(new Intent(this, ScanActivity.class), SCAN_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SCAN_CODE:
                    Intent intent = new Intent(InspectDeviceActivity.this, InspectDetailActivity.class);
                    intent.putExtra("TASK_ID", taskId);
                    intent.putExtra("DEVICE_CODE", data.getStringExtra(Intents.Scan.RESULT));
                    startActivityForResult(intent, UPDATE_INSPECT_TASK);
                    break;
                default:
                    break;
            }
        }
    }

    private class InspectDevicesListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        private InspectDevicesListAdapter(Context context)
        {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if(inspectDevicesRespList != null) {
                return inspectDevicesRespList.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView holder;
            if(view == null) {
                view = mInflater.inflate(R.layout.item_inspect_devices, null);
                holder = (TextView) view.findViewById(R.id.tv_inspect_device_name);
                view.setTag(holder);
            }
            else {
                holder = (TextView) view.getTag();
            }

            holder.setText(inspectDevicesRespList.get(i).getName());
            return view;
        }
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        if(requestType == HttpClient.REQUEST_INSPECT_DEVICES) {
            inspectDevicesRespList = (List<InspectDevicesResp>) list;
            mInspectDevicesListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(int requestType) {

    }
}
