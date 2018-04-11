package tech.yozo.factoryrp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.InspectDeviceActivity;
import tech.yozo.factoryrp.ui.MaintainListActivity;
import tech.yozo.factoryrp.ui.RepairRecordListActivity;
import tech.yozo.factoryrp.ui.TroubleReportActivity;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.MaintainTaskCount;
import tech.yozo.factoryrp.vo.resp.RoleResp;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderCountVo;
import tech.yozo.factoryrp.vo.resp.inspect.InspectTaskResp;

import java.util.List;
import java.util.Map;


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
    private static final int UPDATE_MAINTAIN_TASK = 112;

    Unbinder unbinder;
    @BindView(R.id.button_report_repair)
    Button buttonReportRepair;
    @BindView(R.id.button_exec_audit)
    Button buttonExecAudit;
    @BindView(R.id.button_exec_assign)
    Button buttonExecAssign;
    @BindView(R.id.button_exec_validate)
    Button buttonExecValidate;
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
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private int mParam_mode;
    private String mParam_id;

    private List<Map<String, Object>> list;
    private List<InspectTaskResp> inspectTaskRespList;
    private InspectTaskListAdapter mInspectTaskListAdapter;

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

        boolean paidan =false;
        boolean shenhe =false;
        boolean yanshou = false;
        List<RoleResp> roleResps = HttpClient.getInstance().getRoles();
        if(roleResps != null) {
            for (RoleResp role :
                    roleResps) {
                if (role.getRoleName().contains("派工")) {
                    paidan = true;
                } else if (role.getRoleName().contains("主任")) {
                    shenhe = true;
                } else if (role.getRoleName().contains("操作工") || role.getRoleName().contains("设备员")) {
                    yanshou = true;
                }
            }
        }
        if(!paidan) {
            buttonExecAssign.setEnabled(false);
        }
        if(!shenhe) {
            buttonExecAudit.setEnabled(false);
        }
        if(!yanshou) {
            buttonExecValidate.setEnabled(false);
        }

        refreshLayout.setColorSchemeResources(new int[]{R.color.colorHighlight, R.color.colorPrimary});
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestInspectTask();
            }
        });

        listviewCheckTask.setEmptyView(noNewCheckTask);
        mInspectTaskListAdapter = new InspectTaskListAdapter(getContext());
        listviewCheckTask.setAdapter(mInspectTaskListAdapter);
        listviewCheckTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), InspectDeviceActivity.class);
                intent.putExtra("INSPECT_TASK_ID", inspectTaskRespList.get(i).getPanId());
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
        HttpClient.getInstance().requestMaintainTaskCount(getContext(), this);
        requestInspectTask();
    }

    private void requestInspectTask() {
        HttpClient.getInstance().requestInspectTask(getContext(), this);
    }

    @OnClick({R.id.button_report_repair, R.id.button_exec_audit, R.id.button_exec_assign, R.id.button_exec_validate, R.id.textView_waitto_audit,
            R.id.textView_waitto_exec, R.id.textView_executing, R.id.textView_waitto_verify,
            R.id.tv_mtask_today, R.id.tv_mtask_tomorrow, R.id.tv_mtask_overdue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_report_repair: {
                Intent intent = new Intent(getContext(), TroubleReportActivity.class);
                startActivityForResult(intent, UPDATE_COUNT);
                break;
            }
            case R.id.button_exec_audit:
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
            case R.id.button_exec_assign: {
                Intent intent = new Intent(getContext(), RepairRecordListActivity.class);
                intent.putExtra(RepairRecordListActivity.RECORD_CATEGORY, HttpClient.REQUEST_TROUBLE_WAIT_ASSIGN);
                startActivity(intent);
                break;
            }
            case R.id.button_exec_validate:
            case R.id.textView_waitto_verify: {
                Intent intent = new Intent(getContext(), RepairRecordListActivity.class);
                intent.putExtra(RepairRecordListActivity.RECORD_CATEGORY, HttpClient.REQUEST_TROUBLE_WAIT_VALIDATE);
                startActivityForResult(intent, UPDATE_COUNT);
                break;
            }
            case R.id.tv_mtask_today: {
                Intent intent = new Intent(getContext(), MaintainListActivity.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, UPDATE_MAINTAIN_TASK);
                break;
            }
            case R.id.tv_mtask_tomorrow: {
                Intent intent = new Intent(getContext(), MaintainListActivity.class);
                intent.putExtra("type", 4);
                startActivityForResult(intent, UPDATE_MAINTAIN_TASK);
                break;
            }
            case R.id.tv_mtask_overdue: {
                Intent intent = new Intent(getContext(), MaintainListActivity.class);
                intent.putExtra("type", 7);
                startActivityForResult(intent, UPDATE_MAINTAIN_TASK);
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == UPDATE_COUNT) {
                HttpClient.getInstance().requestTroubleCount(getContext(), this);
            } else if (requestCode == UPDATE_MAINTAIN_TASK) {
                HttpClient.getInstance().requestMaintainTaskCount(getContext(), this);
            }
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
            case HttpClient.REQUEST_MAINTAIN_TASK_COUNT: {
                MaintainTaskCount count = (MaintainTaskCount) obj;
                if (count != null) {
                    tvMtaskToday.setText(String.valueOf(count.getTodayPlanNum()));
                    if (count.getTodayPlanNum() <= 0) {
                        tvMtaskToday.setEnabled(false);
                    } else {
                        tvMtaskToday.setEnabled(true);
                    }
                    tvMtaskTomorrow.setText(String.valueOf(count.getTomorrowPlanNum()));
                    if (count.getTomorrowPlanNum() <= 0) {
                        tvMtaskTomorrow.setEnabled(false);
                    } else {
                        tvMtaskTomorrow.setEnabled(true);
                    }
                    tvMtaskOverdue.setText(String.valueOf(count.getExpiredNum()));
                    if (count.getExpiredNum() <= 0) {
                        tvMtaskOverdue.setEnabled(false);
                    } else {
                        tvMtaskOverdue.setEnabled(true);
                    }
                }
                break;
            }

            case HttpClient.REQUEST_INSPECT_TASK: {
                inspectTaskRespList = (List<InspectTaskResp>) list;
                if (inspectTaskRespList != null) {
                    mInspectTaskListAdapter.notifyDataSetChanged();
                }
                refreshLayout.setRefreshing(false);
            }
            default:
                break;
        }
    }

    private static class ViewHolder {
        TextView name;
        TextView time;
    }

    private class InspectTaskListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        private InspectTaskListAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (inspectTaskRespList != null) {
                return inspectTaskRespList.size();
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
            ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = mInflater.inflate(R.layout.item_inspect_list, null);
                holder.name = (TextView) view.findViewById(R.id.et_mtask_name);
                holder.time = (TextView) view.findViewById(R.id.et_mtask_start_time);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.name.setText(String.valueOf(inspectTaskRespList.get(i).getPlanName()));
            holder.time.setText("任务开始时间： " + inspectTaskRespList.get(i).getNextExecuteTime());
            return view;
        }
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        updateUI(requestType, obj, list);
    }

    @Override
    public void onFailure(int requestType) {
        if(requestType == HttpClient.REQUEST_INSPECT_TASK) {
            refreshLayout.setRefreshing(false);
        }
    }
}
