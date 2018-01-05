package tech.yozo.factoryrp.ui.fragment;


import android.app.DatePickerDialog;
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
import tech.yozo.factoryrp.entity.RepairGroup;
import tech.yozo.factoryrp.ui.dialog.TimePickerDialog;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.req.EndRepairReq;
import tech.yozo.factoryrp.vo.req.StartRepairReq;
import tech.yozo.factoryrp.vo.resp.device.trouble.DeviceTroubleTypeVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderDetailVo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 维修意见页
 * A simple {@link Fragment} subclass.
 * Use the {@link RepairAdviceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepairAdviceFragment extends BaseFragment implements HttpClient.OnHttpListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "obj";

    @BindView(R.id.b_start_repair)
    Button bStartRepair;
    @BindView(R.id.b_finish_repair)
    Button bFinishRepair;
    @BindView(R.id.tv_repair_group)
    TextView tvRepairGroup;
    @BindView(R.id.spinner_repair_status)
    Spinner spinnerRepairStatus;
    @BindView(R.id.spinner_trouble_type)
    Spinner spinnerTroubleType;
    @BindView(R.id.spinner_trouble_reason)
    Spinner spinnerTroubleReason;
    @BindView(R.id.spinner_repair_level)
    Spinner spinnerRepairLevel;
    @BindView(R.id.spinner_is_stop)
    Spinner spinnerIsStop;
    @BindView(R.id.editText_stop_time)
    EditText editTextStopTime;
    @BindView(R.id.editText_repair_cost)
    EditText editTextRepairCost;
    @BindView(R.id.editText_repair_desc)
    EditText editTextRepairDesc;
    @BindView(R.id.tv_start_time)
    Button tvStartTime;
    @BindView(R.id.tv_end_time)
    Button tvEndTime;
    @BindView(R.id.editText_total_time)
    EditText editTextTotalTime;
    Unbinder unbinder;

    private int mParam_mode;
    private WorkOrderDetailVo mParam_obj;

    private TimePickerDialog mTimePickerDialog;

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
    public static RepairAdviceFragment newInstance(int param1, long param2) {
        RepairAdviceFragment fragment = new RepairAdviceFragment();
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
            mParam_obj = (WorkOrderDetailVo) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repair_advice, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    protected void loadData() {
        List<RepairGroup> groups = HttpClient.getInstance().getRepairGroupList();
        if (groups == null) {
            HttpClient.getInstance().requestRepairGroup(getContext(), this);
        }

        List<DeviceTroubleTypeVo> troubleTypeVos = HttpClient.getInstance().getTroubleTypeVoList();
        if (troubleTypeVos == null) {
            HttpClient.getInstance().requestTroubleType(getContext(), this);
        }

        //TODO 维修级别 故障原因 维修状态
    }

    @Override
    protected void buildUI() {
        switch (mParam_mode) {
            case Constant.FOR_REPAIR_ADD:
                bStartRepair.setVisibility(View.GONE);
                bFinishRepair.setVisibility(View.GONE);
                break;
            default:
                List<RepairGroup> groups = HttpClient.getInstance().getRepairGroupList();
                if(groups != null) {
                    for(RepairGroup group : groups) {
                        if(group.getId().equals(mParam_obj.getRepairGroupId())) {
                            tvRepairGroup.setText(group.getName());
                            break;
                        }
                    }
                }

                if(mParam_obj.getRepairStatus() != null) {
                    spinnerRepairStatus.setSelection(mParam_obj.getRepairStatus());
                }
//        spinnerTroubleType.setSelection(mParam_obj.getTroubleType());
                if(mParam_obj.getTroubleReason() != null) {
                    spinnerTroubleReason.setSelection(mParam_obj.getTroubleReason());
                }
                if(mParam_obj.getRepairLevel() != null) {
                    spinnerRepairLevel.setSelection(mParam_obj.getRepairLevel());
                }
                if(mParam_obj.getStoped() != null) {
                    spinnerIsStop.setSelection(mParam_obj.getStoped());
                }
                editTextStopTime.setText(mParam_obj.getStopedHour());
                editTextRepairCost.setText(mParam_obj.getRepairAmount());
                editTextRepairDesc.setText(mParam_obj.getWorkRemark());
                tvStartTime.setText(mParam_obj.getStartTime());
                tvEndTime.setText(mParam_obj.getEndTime());
                editTextTotalTime.setText(mParam_obj.getCostHour());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time, R.id.b_start_repair, R.id.b_finish_repair})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                mTimePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.TimePickerDialogInterface() {
                    @Override
                    public void positiveListener() {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(mTimePickerDialog.getYear()).append("年")
                                .append(mTimePickerDialog.getMonth()).append("月")
                                .append(mTimePickerDialog.getDay()).append("日 ")
                                .append(mTimePickerDialog.getHour()).append(":")
                                .append(mTimePickerDialog.getMinute());
                        tvStartTime.setText(buffer);
                    }

                    @Override
                    public void negativeListener() {

                    }
                });
                mTimePickerDialog.showDateAndTimePickerDialog();
                break;
            case R.id.tv_end_time:
                mTimePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.TimePickerDialogInterface() {
                    @Override
                    public void positiveListener() {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(mTimePickerDialog.getYear()).append("年")
                                .append(mTimePickerDialog.getMonth()).append("月")
                                .append(mTimePickerDialog.getDay()).append("日 ")
                                .append(mTimePickerDialog.getHour()).append(":")
                                .append(mTimePickerDialog.getMinute());
                        tvEndTime.setText(buffer);
                    }

                    @Override
                    public void negativeListener() {

                    }
                });
                mTimePickerDialog.showDateAndTimePickerDialog();
                break;
            case R.id.b_start_repair: {
                HttpClient client = HttpClient.getInstance();
                StartRepairReq req = new StartRepairReq();
                //TODO
                req.setStartTime(tvStartTime.getText().toString());
                req.setWorkRemark(editTextRepairDesc.getText().toString());
                client.requestStartRepairTask(getContext(), this, req);
                break;
            }
            case R.id.b_finish_repair: {
                HttpClient client = HttpClient.getInstance();
                EndRepairReq req = new EndRepairReq();
                //TODO
                req.setEndTime(tvEndTime.getText().toString());
                req.setWorkRemark(editTextRepairDesc.getText().toString());
                client.requestEndRepairTask(getContext(), this, req);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {

    }

    @Override
    public void onFailure(int requestType) {

    }
}
