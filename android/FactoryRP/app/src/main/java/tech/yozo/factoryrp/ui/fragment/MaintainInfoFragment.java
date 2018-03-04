package tech.yozo.factoryrp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.vo.resp.MaintainDetailResp;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MaintainInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MaintainInfoFragment extends Fragment {
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "obj";
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_device_code)
    TextView tvDeviceCode;
    @BindView(R.id.tv_device_type)
    TextView tvDeviceType;
    @BindView(R.id.tv_device_dept)
    TextView tvDeviceDept;
    @BindView(R.id.tv_device_category)
    TextView tvDeviceCategory;
    @BindView(R.id.tv_maintain_level)
    TextView tvMaintainLevel;
    @BindView(R.id.tv_cycle_type)
    TextView tvCycleType;
    @BindView(R.id.tv_cycle_time)
    TextView tvCycleTime;
    @BindView(R.id.tv_before_time)
    TextView tvBeforeTime;
    @BindView(R.id.tv_next_time)
    TextView tvNextTime;
    @BindView(R.id.tv_maintain_part)
    TextView tvMaintainPart;
    @BindView(R.id.tv_maintain_standard)
    TextView tvMaintainStandard;
    @BindView(R.id.tv_maintainer)
    TextView tvMaintainer;
    @BindView(R.id.tv_task_desc)
    TextView tvTaskDesc;
    Unbinder unbinder;

    private int mParam_mode;
    private MaintainDetailResp mParam_obj;


    public MaintainInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MaintainInfoFragment.
     */
    public static MaintainInfoFragment newInstance(int param1, long param2) {
        MaintainInfoFragment fragment = new MaintainInfoFragment();
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
            mParam_obj = (MaintainDetailResp) getArguments().getSerializable(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maintian_info, container, false);

        unbinder = ButterKnife.bind(this, view);
        tvDeviceName.setText(mParam_obj.getDeviceName());
        tvDeviceCode.setText(mParam_obj.getDeviceCode());
        tvDeviceType.setText(mParam_obj.getDeviceSpec());
        tvDeviceDept.setText(mParam_obj.getDeviceUseDeptName());
        tvDeviceCategory.setText(mParam_obj.getDeviceTypeName());
        tvMaintainLevel.setText(mParam_obj.getMaintainLevelString());
        tvCycleType.setText(mParam_obj.getCycleType());
        tvCycleTime.setText(mParam_obj.getCycleTime());
        tvBeforeTime.setText(mParam_obj.getLastTime());
        tvNextTime.setText(mParam_obj.getNexMaintainTime());
        tvMaintainPart.setText(mParam_obj.getMaintainPart());
        tvMaintainStandard.setText(mParam_obj.getMaintainStandard());
        tvMaintainer.setText(mParam_obj.getPlanManagerName());
        tvTaskDesc.setText(mParam_obj.getPlanRemark());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
