package tech.yozo.factoryrp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.DeviceAddActivity;
import tech.yozo.factoryrp.ui.RepairRecordListActivity;
import tech.yozo.factoryrp.ui.ReportFaultActivity;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderCountVo;


/**
 * 用户主页
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkBenchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkBenchFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.button_ask_repair)
    Button buttonAskRepair;
    @BindView(R.id.button_add_repair)
    Button buttonAddRepair;
    @BindView(R.id.button_ask_parts)
    Button buttonAskParts;
    @BindView(R.id.button_add_device)
    Button buttonAddDevice;
    @BindView(R.id.textView_waitto_audit)
    TextView textViewWaittoAudit;
    @BindView(R.id.textView_waitto_exec)
    TextView textViewWaittoExec;
    @BindView(R.id.textView_executing)
    TextView textViewExecuting;
    @BindView(R.id.textView_waitto_verify)
    TextView textViewWaittoVerify;
    @BindView(R.id.listview_maintain_task)
    ListView listviewMaintainTask;
    @BindView(R.id.listview_check_task)
    ListView listviewCheckTask;
    @BindView(R.id.noNewMaintainTask)
    TextView noNewMaintainTask;
    @BindView(R.id.noNewCheckTask)
    TextView noNewCheckTask;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    private WorkOrderCountVo mTroubleCount;


    public WorkBenchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkBenchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkBenchFragment newInstance(int param1, String param2) {
        WorkBenchFragment fragment = new WorkBenchFragment();
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
        View view = inflater.inflate(R.layout.fragment_workbench, container, false);
        unbinder = ButterKnife.bind(this, view);
        listviewMaintainTask.setEmptyView(noNewMaintainTask);
        listviewCheckTask.setEmptyView(noNewCheckTask);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void loadData() {
        HttpClient client = HttpClient.getInstance();
        client.get(getContext(), HttpClient.TROUBLE_COUNT, null, getTroubleCountResponse);
    }

    @Override
    protected void buildUI() {
        refreshTroubleCount();
    }

    @OnClick({R.id.button_ask_repair, R.id.button_add_repair, R.id.button_ask_parts, R.id.button_add_device, R.id.textView_waitto_audit, R.id.textView_waitto_exec, R.id.textView_executing, R.id.textView_waitto_verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_ask_repair: {
                Intent intent = new Intent(getContext(), ReportFaultActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button_add_repair:
                //TODO
                break;
            case R.id.button_ask_parts:
                break;
            case R.id.button_add_device: {
                Intent intent = new Intent(getContext(), DeviceAddActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.textView_waitto_audit: {
                Intent intent = new Intent(getContext(), RepairRecordListActivity.class);
                intent.putExtra(RepairRecordListActivity.RECORD_CATEGORY, Constant.FOR_WAIT_AUDIT);
                startActivity(intent);
                break;
            }
            case R.id.textView_waitto_exec: {
                Intent intent = new Intent(getContext(), RepairRecordListActivity.class);
                intent.putExtra(RepairRecordListActivity.RECORD_CATEGORY, Constant.FOR_WAIT_REPAIR);
                startActivity(intent);
                break;
            }
            case R.id.textView_executing: {
                Intent intent = new Intent(getContext(), RepairRecordListActivity.class);
                intent.putExtra(RepairRecordListActivity.RECORD_CATEGORY, Constant.FOR_REPAIRING);
                startActivity(intent);
                break;
            }
            case R.id.textView_waitto_verify: {
                Intent intent = new Intent(getContext(), RepairRecordListActivity.class);
                intent.putExtra(RepairRecordListActivity.RECORD_CATEGORY, Constant.FOR_WAIT_VERIFY);
                startActivity(intent);
                break;
            }
        }
    }

    private JsonHttpResponseHandler getTroubleCountResponse = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                if (ErrorCode.SUCCESS.getCode().equals(response.getString("errorCode"))) {
                    mTroubleCount = JSON.parseObject(response.getString("data"), WorkOrderCountVo.class);
                    refreshTroubleCount();
                } else {
                    Toast.makeText(getContext(), R.string.failure_service, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), R.string.failure_data_parse, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Toast.makeText(getContext(), R.string.failure_request, Toast.LENGTH_SHORT).show();
        }
    };

    private void refreshTroubleCount() {
        if (mTroubleCount != null) {
            textViewWaittoAudit.setText(String.valueOf(mTroubleCount.getWaitAuditNum()));
            if(mTroubleCount.getWaitAuditNum() <= 0) {
                textViewWaittoAudit.setEnabled(false);
            } else {
                textViewWaittoAudit.setEnabled(true);
            }
            textViewWaittoExec.setText(String.valueOf(mTroubleCount.getWaitRepairNum()));
            if(mTroubleCount.getWaitRepairNum() <= 0) {
                textViewWaittoExec.setEnabled(false);
            } else {
                textViewWaittoExec.setEnabled(true);
            }
            textViewExecuting.setText(String.valueOf(mTroubleCount.getRepairingNum()));
            if(mTroubleCount.getRepairingNum() <= 0) {
                textViewExecuting.setEnabled(false);
            } else {
                textViewExecuting.setEnabled(true);
            }
            textViewWaittoVerify.setText(String.valueOf(mTroubleCount.getWaitValidateNum()));
            if(mTroubleCount.getWaitValidateNum() <= 0) {
                textViewWaittoVerify.setEnabled(false);
            } else {
                textViewWaittoVerify.setEnabled(true);
            }
        }
    }
}
