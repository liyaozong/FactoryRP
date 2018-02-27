package tech.yozo.factoryrp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.*;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderCountVo;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 用户主页
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkBenchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkBenchFragment extends BaseFragment implements HttpClient.OnHttpListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "id";

    private static final int UPDATE_COUNT = 110;
    private static final int UPDATE_INSPECT_TASK = 111;

    Unbinder unbinder;
    @BindView(R.id.button_ask_repair)
    Button buttonAskRepair;
    @BindView(R.id.button_add_repair)
    Button buttonAddRepair;
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
    @BindView(R.id.listview_check_task)
    ListView listviewCheckTask;
    @BindView(R.id.noNewCheckTask)
    TextView noNewCheckTask;
    @BindView(R.id.tv_mtask_today)
    TextView tvMtaskToday;
    @BindView(R.id.tv_mtask_tomorrow)
    TextView tvMtaskTomorrow;
    @BindView(R.id.tv_mtask_overdue)
    TextView tvMtaskOverdue;

    private int mParam_mode;
    private String mParam_id;

    private List<Map<String, Object>> list;

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
            mParam_mode = getArguments().getInt(ARG_PARAM1);
            mParam_id = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workbench, container, false);
        unbinder = ButterKnife.bind(this, view);


        listviewCheckTask.setEmptyView(noNewCheckTask);
        String[] from = {"title", "time"};
        int[] to = {R.id.et_mtask_name, R.id.et_mtask_start_time};
        list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", "任务：" + i);
            map.put("time",
                    new SimpleDateFormat("计划开始时间：yyyy年MM月dd日").format(new Date()));
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(getContext(), list, R.layout.item_inspect_list, from, to);
        listviewCheckTask.setAdapter(adapter);
        listviewCheckTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO
                Intent intent = new Intent(getActivity(), InspectDeviceActivity.class);
                startActivityForResult(intent, UPDATE_INSPECT_TASK);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void buildUI() {
        HttpClient.getInstance().requestTroubleCount(getContext(), this);
    }

    @OnClick({R.id.button_ask_repair, R.id.button_add_repair, R.id.button_add_device, R.id.textView_waitto_audit,
            R.id.textView_waitto_exec, R.id.textView_executing, R.id.textView_waitto_verify,
            R.id.tv_mtask_today, R.id.tv_mtask_tomorrow, R.id.tv_mtask_overdue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_ask_repair: {
                Intent intent = new Intent(getContext(), ReportFaultActivity.class);
                startActivityForResult(intent, UPDATE_COUNT);
                break;
            }
            case R.id.button_add_repair: {
                Intent intent = new Intent(getContext(), RepairAddActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button_add_device: {
                Intent intent = new Intent(getContext(), DeviceAddActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.textView_waitto_audit: {
                Intent intent = new Intent(getContext(), RepairRecordListActivity.class);
                intent.putExtra(RepairRecordListActivity.RECORD_CATEGORY, HttpClient.REQUEST_TROUBLE_WAIT_AUDIT);
                startActivityForResult(intent, UPDATE_COUNT);
                break;
            }
            case R.id.textView_waitto_exec: {
                Intent intent = new Intent(getContext(), RepairRecordListActivity.class);
                intent.putExtra(RepairRecordListActivity.RECORD_CATEGORY, HttpClient.REQUEST_TROUBLE_WAIT_REPAIR);
                startActivityForResult(intent, UPDATE_COUNT);
                break;
            }
            case R.id.textView_executing: {
                Intent intent = new Intent(getContext(), RepairRecordListActivity.class);
                intent.putExtra(RepairRecordListActivity.RECORD_CATEGORY, HttpClient.REQUEST_TROUBLE_REPAIRING);
                startActivityForResult(intent, UPDATE_COUNT);
                break;
            }
            case R.id.textView_waitto_verify: {
                Intent intent = new Intent(getContext(), RepairRecordListActivity.class);
                intent.putExtra(RepairRecordListActivity.RECORD_CATEGORY, HttpClient.REQUEST_TROUBLE_WAIT_VALIDATE);
                startActivityForResult(intent, UPDATE_COUNT);
                break;
            }
            case R.id.tv_mtask_today: {
                Intent intent = new Intent(getContext(), RepairRecordListActivity.class);
                intent.putExtra(RepairRecordListActivity.RECORD_CATEGORY, HttpClient.REQUEST_TROUBLE_REPAIRING);
                startActivityForResult(intent, UPDATE_COUNT);
                break;
            }
            case R.id.tv_mtask_tomorrow: {
                Intent intent = new Intent(getContext(), RepairRecordListActivity.class);
                intent.putExtra(RepairRecordListActivity.RECORD_CATEGORY, HttpClient.REQUEST_TROUBLE_REPAIRING);
                startActivityForResult(intent, UPDATE_COUNT);
                break;
            }
            case R.id.tv_mtask_overdue: {
                Intent intent = new Intent(getContext(), RepairRecordListActivity.class);
                intent.putExtra(RepairRecordListActivity.RECORD_CATEGORY, HttpClient.REQUEST_TROUBLE_REPAIRING);
                startActivityForResult(intent, UPDATE_COUNT);
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UPDATE_COUNT && resultCode == Activity.RESULT_OK) {
            HttpClient.getInstance().requestTroubleCount(getContext(), this);
        }
    }

    private void updateUI(int requestType, Object obj, List<?> list) {
        switch (requestType) {
            case HttpClient.REQUEST_TROUBLE_COUNT: {
                WorkOrderCountVo mTroubleCount = (WorkOrderCountVo) obj;
                if (mTroubleCount != null) {
                    textViewWaittoAudit.setText(String.valueOf(mTroubleCount.getWaitAuditNum()));
                    if (mTroubleCount.getWaitAuditNum() <= 0) {
                        textViewWaittoAudit.setEnabled(false);
                    } else {
                        textViewWaittoAudit.setEnabled(true);
                    }
                    textViewWaittoExec.setText(String.valueOf(mTroubleCount.getWaitRepairNum()));
                    if (mTroubleCount.getWaitRepairNum() <= 0) {
                        textViewWaittoExec.setEnabled(false);
                    } else {
                        textViewWaittoExec.setEnabled(true);
                    }
                    textViewExecuting.setText(String.valueOf(mTroubleCount.getRepairingNum()));
                    if (mTroubleCount.getRepairingNum() <= 0) {
                        textViewExecuting.setEnabled(false);
                    } else {
                        textViewExecuting.setEnabled(true);
                    }
                    textViewWaittoVerify.setText(String.valueOf(mTroubleCount.getWaitValidateNum()));
                    if (mTroubleCount.getWaitValidateNum() <= 0) {
                        textViewWaittoVerify.setEnabled(false);
                    } else {
                        textViewWaittoVerify.setEnabled(true);
                    }
                }
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        updateUI(requestType, obj, list);
    }

    @Override
    public void onFailure(int requestType) {

    }
}
