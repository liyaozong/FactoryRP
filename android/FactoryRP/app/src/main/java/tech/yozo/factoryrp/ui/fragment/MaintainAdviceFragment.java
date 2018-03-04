package tech.yozo.factoryrp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.vo.resp.MaintainDetailResp;

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

    private int mParam_mode;
    private MaintainDetailResp mParam_obj;


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
    // TODO: Rename and change types and number of parameters
    public static MaintainAdviceFragment newInstance(String param1, String param2) {
        MaintainAdviceFragment fragment = new MaintainAdviceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam_mode = getArguments().getInt(ARG_PARAM1);
            mParam_obj = (MaintainDetailResp) getArguments().getSerializable(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maintain_advice, container, false);

        unbinder = ButterKnife.bind(this, view);
        tvMaintainGroup.setText(mParam_obj.getRepairGroupName());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
