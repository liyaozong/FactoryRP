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
import tech.yozo.factoryrp.entity.RepairGroup;
import tech.yozo.factoryrp.ui.dialog.TimePickerDialog;
import tech.yozo.factoryrp.utils.*;
import tech.yozo.factoryrp.vo.req.EndRepairReq;
import tech.yozo.factoryrp.vo.req.StartRepairReq;
import tech.yozo.factoryrp.vo.resp.DeviceParamDicEnumResp;
import tech.yozo.factoryrp.vo.resp.device.trouble.DeviceTroubleTypeVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderDetailVo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    Spinner tvRepairGroup;
    @BindView(R.id.spinner_repair_status)
    Spinner spinnerRepairStatus;
    @BindView(R.id.spinner_trouble_type)
    Spinner spinnerTroubleType;
    @BindView(R.id.spinner_trouble_reason)
    Spinner spinnerTroubleReason;
    @BindView(R.id.spinner_repair_level)
    Spinner spinnerRepairLevel;
    @BindView(R.id.rg_need_stop)
    RadioGroup rgNeedStop;
    @BindView(R.id.editText_stop_time)
    EditText editTextStopTime;
    @BindView(R.id.editText_repair_cost)
    EditText editTextRepairCost;
    @BindView(R.id.editText_repair_desc)
    EditText editTextRepairDesc;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.editText_total_time)
    EditText editTextTotalTime;
    Unbinder unbinder;

    private int mParam_mode;
    private WorkOrderDetailVo mParam_obj;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
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

        switch (mParam_mode) {
            case HttpClient.REQUEST_TROUBLE_WAIT_REPAIR:
                bFinishRepair.setVisibility(View.GONE);
                break;
            case HttpClient.REQUEST_TROUBLE_REPAIRING:
                bStartRepair.setVisibility(View.GONE);
                break;
            case HttpClient.REQUEST_TROUBLE_WAIT_AUDIT:
            case HttpClient.REQUEST_TROUBLE_WAIT_VALIDATE:
            case Constant.FOR_REPAIR_ADD:
            case Constant.FOR_DEVICE_ID:
                bStartRepair.setVisibility(View.GONE);
                bFinishRepair.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        return view;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void buildUI() {
        HttpClient client = HttpClient.getInstance();
        if (client.getDictEnum(Constant.DICT_TROUBLE_REASON) == null) {
            client.requestDeviceDict(getContext(), this, Constant.DICT_TROUBLE_REASON);
        } else {
            updateUI(HttpClient.REQUEST_DATA_DICT);
        }

        if (client.getDictEnum(Constant.DICT_REPAIR_LEVEL) == null) {
            client.requestDeviceDict(getContext(), this, Constant.DICT_REPAIR_LEVEL);
        } else {
            updateUI(HttpClient.REQUEST_DATA_DICT);
        }

        if (client.getDictEnum(Constant.DICT_REPAIR_STATUS) == null) {
            client.requestDeviceDict(getContext(), this, Constant.DICT_REPAIR_STATUS);
        } else {
            updateUI(HttpClient.REQUEST_DATA_DICT);
        }

        if (client.getRepairGroupList() == null) {
            client.requestRepairGroup(getContext(), this);
        } else {
            updateUI(HttpClient.REQUEST_REPAIR_GROUP_URL);
        }

        if (client.getTroubleTypeVoList() == null) {
            client.requestTroubleType(getContext(), this);
        } else {
            updateUI(HttpClient.REQUEST_DEVICE_TROUBLE_TYPE_URL);
        }

        if(mParam_obj != null) {
            if (mParam_obj.getStoped() != null && mParam_obj.getStoped() == 1) {
                rgNeedStop.check(R.id.rb_stop_yes);
            } else {
                rgNeedStop.check(R.id.rb_stop_no);
            }
            if(mParam_obj.getStopedHour() != null) {
                editTextStopTime.setText(String.valueOf(mParam_obj.getStopedHour()));
            } else {
                editTextStopTime.setText("0");
            }
            editTextRepairCost.setText(mParam_obj.getRepairAmount());
            editTextRepairDesc.setText(mParam_obj.getWorkRemark());
            tvStartTime.setText(mParam_obj.getStartTime());
            tvEndTime.setText(mParam_obj.getEndTime());
            if(mParam_obj.getCostHour() != null) {
                editTextTotalTime.setText(String.valueOf(mParam_obj.getCostHour()));
            } else {
                editTextTotalTime.setText("0");
            }
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
            case R.id.tv_end_time:
                openDateDialog(view.getId());
                break;
            case R.id.b_start_repair: {
                HttpClient client = HttpClient.getInstance();
                StartRepairReq req = new StartRepairReq();
                req.setTroubleRecordId(mParam_obj.getTroubleRecordId());
                req.setRepairGroupId(tvRepairGroup.getSelectedItemId());
                req.setRepairStatus((int) spinnerRepairStatus.getSelectedItemId());
                req.setTroubleTypeId(spinnerTroubleType.getSelectedItemId());
                req.setTroubleReason((int) spinnerTroubleReason.getSelectedItemId());
                req.setRepairLevel((int) spinnerRepairLevel.getSelectedItemId());
                switch (rgNeedStop.getCheckedRadioButtonId()) {
                    case R.id.rb_stop_yes:
                        req.setStoped(1);
                        req.setStopedHour(editTextStopTime.getText().toString());
                        break;
                    case R.id.rb_stop_no:
                        req.setStoped(0);
                        break;
                    default:
                        break;
                }
                req.setRepairAmount(editTextRepairCost.getText().toString());
                req.setStartTime(tvStartTime.getText().toString());
                req.setWorkRemark(editTextRepairDesc.getText().toString());
                client.requestStartRepairTask(getContext(), this, req);
                break;
            }
            case R.id.b_finish_repair: {
                HttpClient client = HttpClient.getInstance();
                EndRepairReq req = new EndRepairReq();
                req.setTroubleRecordId(mParam_obj.getTroubleRecordId());
                req.setRepairGroupId(tvRepairGroup.getSelectedItemId());
                req.setTroubleTypeId(spinnerTroubleType.getSelectedItemId());
                req.setTroubleReason((int) spinnerTroubleReason.getSelectedItemId());
                req.setRepairLevel((int) spinnerRepairLevel.getSelectedItemId());
                switch (rgNeedStop.getCheckedRadioButtonId()) {
                    case R.id.rb_stop_yes:
                        req.setStoped(1);
                        req.setStopedHour(Integer.decode(editTextStopTime.getText().toString()));
                        break;
                    case R.id.rb_stop_no:
                        req.setStoped(0);
                        break;
                    default:
                        break;
                }
                req.setRepairAmount(editTextRepairCost.getText().toString());
                req.setEndTime(tvEndTime.getText().toString());
                req.setWorkRemark(editTextRepairDesc.getText().toString());
                req.setCostHour(Integer.decode(editTextTotalTime.getText().toString()));
                client.requestEndRepairTask(getContext(), this, req);
                break;
            }
            default:
                break;
        }
    }

    private void openDateDialog(final int id) {
        mTimePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.TimePickerDialogInterface() {
            @Override
            public void positiveListener() {
                StringBuffer buffer = new StringBuffer();
                buffer.append(mTimePickerDialog.getYear()).append("年")
                        .append(mTimePickerDialog.getMonth()).append("月")
                        .append(mTimePickerDialog.getDay()).append("日 ")
                        .append(mTimePickerDialog.getHour()).append(":")
                        .append(mTimePickerDialog.getMinute());
                switch (id) {
                    case R.id.tv_start_time:
                        tvStartTime.setText(buffer);
                        calcWorkTime(tvStartTime.getText(), tvEndTime.getText());
                        break;
                    case R.id.tv_end_time:
                        tvEndTime.setText(buffer);
                        calcWorkTime(tvStartTime.getText(), tvEndTime.getText());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void negativeListener() {

            }
        });
        mTimePickerDialog.showDateAndTimePickerDialog();
    }

    private void calcWorkTime(CharSequence start, CharSequence end) {
        Date hasStart = null;
        Date hasEnd = null;
        try {
            hasStart = df.parse(start.toString());
            hasEnd = df.parse(end.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(hasStart != null && hasEnd != null) {
            long useTime = (hasEnd.getTime() - hasStart.getTime())/3600000;
            editTextTotalTime.setText(String.valueOf(useTime));
        }
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        updateUI(requestType);
    }

    @Override
    public void onFailure(int requestType) {

    }

    private void updateUI(int requestType) {
        HttpClient client = HttpClient.getInstance();
        switch (requestType) {
            case HttpClient.REQUEST_DATA_DICT:
                List<DeviceParamDicEnumResp> troubleReason = client.getDictEnum(Constant.DICT_TROUBLE_REASON);
                if (troubleReason != null) {
                    spinnerTroubleReason.setAdapter(new DictSpinnerAdapter(getContext(), android.R.layout.simple_list_item_1, troubleReason));
                    if(mParam_obj != null) {
                        for (int i = 0; i < troubleReason.size(); i++) {
                            if (troubleReason.get(i).getId().equals(mParam_obj.getTroubleReason())) {
                                spinnerTroubleReason.setSelection(i, false);
                                break;
                            }
                        }
                    }
                }
                List<DeviceParamDicEnumResp> repairLevel = client.getDictEnum(Constant.DICT_REPAIR_LEVEL);
                if (repairLevel != null) {
                    spinnerRepairLevel.setAdapter(new DictSpinnerAdapter(getContext(), android.R.layout.simple_list_item_1, repairLevel));
                    if(mParam_obj != null) {
                        for (int i = 0; i < repairLevel.size(); i++) {
                            if (repairLevel.get(i).getId().equals(mParam_obj.getRepairLevel())) {
                                spinnerRepairLevel.setSelection(i, false);
                                break;
                            }
                        }
                    }
                }
                List<DeviceParamDicEnumResp> repairStatus = client.getDictEnum(Constant.DICT_REPAIR_STATUS);
                if (repairStatus != null) {
                    spinnerRepairStatus.setAdapter(new DictSpinnerAdapter(getContext(), android.R.layout.simple_list_item_1, repairStatus));
                    if(mParam_obj != null) {
                        for (int i = 0; i < repairStatus.size(); i++) {
                            if (repairStatus.get(i).getId().equals(mParam_obj.getRepairStatus())) {
                                spinnerRepairStatus.setSelection(i, false);
                                break;
                            }
                        }
                    }
                }
                break;
            case HttpClient.REQUEST_DEVICE_TROUBLE_TYPE_URL:
                List<DeviceTroubleTypeVo> troubleTypeVoList = client.getTroubleTypeVoList();
                if (troubleTypeVoList != null) {
                    spinnerTroubleType.setAdapter(new TroubleTypeSpinnerAdapter(getContext(), android.R.layout.simple_list_item_1, troubleTypeVoList));
                    if(mParam_obj != null) {
                        for (int i = 0; i < troubleTypeVoList.size(); i++) {
                            if (troubleTypeVoList.get(i).getName().equals(mParam_obj.getTroubleType())) {
                                spinnerTroubleType.setSelection(i, false);
                                break;
                            }
                        }
                    }
                }
                break;
            case HttpClient.REQUEST_REPAIR_GROUP_URL:
                List<RepairGroup> repairGroupList = client.getRepairGroupList();
                if (repairGroupList != null) {
                    tvRepairGroup.setAdapter(new GroupSpinnerAdapter(getContext(), android.R.layout.simple_list_item_1, repairGroupList));
                    if(mParam_obj != null) {
                        for (int i = 0; i < repairGroupList.size(); i++) {
                            if (repairGroupList.get(i).getId().equals(mParam_obj.getRepairGroupId())) {
                                tvRepairGroup.setSelection(i, false);
                                break;
                            }
                        }
                    }
                }
                break;
            case HttpClient.REQUEST_START_REPAIR_ACTION:
                bStartRepair.setEnabled(false);
                Intent intent = new Intent();
                intent.putExtra("id", mParam_obj.getTroubleRecordId());
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
                break;
            case HttpClient.REQUEST_END_REPAIR_ACTION:
                bFinishRepair.setEnabled(false);
                break;
            default:
                break;
        }
    }
}
