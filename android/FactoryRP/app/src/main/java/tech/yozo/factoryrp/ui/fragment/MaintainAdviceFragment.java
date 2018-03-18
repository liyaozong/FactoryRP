package tech.yozo.factoryrp.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.dialog.TimePickerDialog;
import tech.yozo.factoryrp.vo.req.MaintainDetailSubmitReq;
import tech.yozo.factoryrp.vo.resp.MaintainDetailResp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MaintainAdviceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MaintainAdviceFragment extends Fragment {
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "obj";
    @BindView(R.id.tv_maintain_group)
    TextView tvMaintainGroup;
    @BindView(R.id.spinner_maintain_status)
    Spinner spinnerMaintainStatus;
    @BindView(R.id.spinner_maintain_level)
    Spinner spinnerMaintainLevel;
    @BindView(R.id.rg_need_stop)
    RadioGroup rgNeedStop;
    @BindView(R.id.editText_stop_time)
    EditText editTextStopTime;
    @BindView(R.id.editText_maintain_cost)
    EditText editTextMaintainCost;
    @BindView(R.id.editText_maintain_desc)
    EditText editTextMaintainDesc;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.editText_total_time)
    EditText editTextTotalTime;
    Unbinder unbinder;

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private int mParam_mode;
    private MaintainDetailResp mParam_obj;
    private MaintainDetailSubmitReq advice;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    private TimePickerDialog mTimePickerDialog;

    public MaintainAdviceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MaintainAdviceFragment.
     */
    public static MaintainAdviceFragment newInstance(String param1, String param2) {
        MaintainAdviceFragment fragment = new MaintainAdviceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam_mode = getArguments().getInt(ARG_PARAM1);
            mParam_obj = (MaintainDetailResp) getArguments().getSerializable(ARG_PARAM2);
        }

        if (mContext instanceof OnFragmentInteractionListener) {
            advice = new MaintainDetailSubmitReq();
            mListener = (OnFragmentInteractionListener) mContext;
            mListener.onModifyAdvice(advice);
        } else {
            throw new RuntimeException(mContext.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maintain_advice, container, false);

        unbinder = ButterKnife.bind(this, view);
        tvMaintainGroup.setText(mParam_obj.getRepairGroupName());
        rgNeedStop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_stop_yes:
                        advice.setStoped(1);
                        break;
                    case R.id.rb_stop_no:
                        advice.setStoped(0);
                        break;
                }
            }
        });
        editTextTotalTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0) {
                    advice.setCostHour(Integer.decode(editable.toString()));
                }
            }
        });
        editTextStopTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0) {
                    advice.setStopedHour(Integer.decode(editable.toString()));
                }
            }
        });
        editTextMaintainCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                advice.setMaintainAmount(editable.toString());
            }
        });
        editTextMaintainDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                advice.setMaintainRemark(editable.toString());
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener {
        void onModifyAdvice(MaintainDetailSubmitReq advice);
    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
            case R.id.tv_end_time:
                openDateDialog(view.getId());
                break;
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
                        advice.setStartTime(buffer.toString());
                        calcWorkTime(tvStartTime.getText(), tvEndTime.getText());
                        break;
                    case R.id.tv_end_time:
                        tvEndTime.setText(buffer);
                        advice.setEndTime(buffer.toString());
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
            advice.setCostHour((int) useTime);
        }
    }
}
