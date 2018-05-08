package tech.yozo.factoryrp.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.loopj.android.http.RequestParams;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.RepairValidateActivity;
import tech.yozo.factoryrp.ui.dialog.ShowImagesDialog;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderDetailVo;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 维修信息页
 * A simple {@link Fragment} subclass.
 * Use the {@link RepairInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepairInfoFragment extends BaseFragment implements HttpClient.OnHttpListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mode";
    private static final String ARG_PARAM2 = "obj";

    private static final int VALIDATE_REPAIR = 110;

    @BindView(R.id.b_i_repair)
    Button bIRepair;
    @BindView(R.id.b_i_not_repair)
    Button bINotRepair;
    @BindView(R.id.b_validate_repair)
    Button bValidateRepair;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_device_code)
    TextView tvDeviceCode;
    @BindView(R.id.tv_device_type)
    TextView tvDeviceType;
    @BindView(R.id.tv_device_dept)
    TextView tvDeviceDept;
    @BindView(R.id.tv_device_place)
    TextView tvDevicePlace;
    @BindView(R.id.tv_device_running_status)
    TextView tvDeviceRunningStatus;
    @BindView(R.id.tv_happen_time)
    TextView tvHappenTime;
    @BindView(R.id.tv_repair_id)
    TextView tvRepairId;
    @BindView(R.id.tv_device_operator)
    TextView tvDeviceOperator;
    @BindView(R.id.tv_operator_phone)
    TextView tvOperatorPhone;
    @BindView(R.id.tv_trouble_submitter)
    TextView tvTroubleSubmitter;
    @BindView(R.id.tv_maintainer)
    TextView tvMaintainer;
    @BindView(R.id.tv_trouble_desc)
    TextView tvTroubleDesc;
    Unbinder unbinder;
    @BindView(R.id.ll_images)
    LinearLayout llImages;

    private int mParam_mode;
    private WorkOrderDetailVo mParam_obj;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时MM分");

    public RepairInfoFragment() {
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
    public static RepairInfoFragment newInstance(int param1, long param2) {
        RepairInfoFragment fragment = new RepairInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_repair_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        bINotRepair.setVisibility(View.GONE);
        bIRepair.setVisibility(View.GONE);

        tvDeviceName.setText(mParam_obj.getDeviceName());
        tvDeviceCode.setText(mParam_obj.getDeviceCode());
        tvDeviceType.setText(mParam_obj.getSpecification());
        tvDeviceDept.setText(mParam_obj.getUseDept());
        tvDevicePlace.setText(mParam_obj.getInstallationAddress());
        //TODO
        tvDeviceRunningStatus.setText("");
        tvHappenTime.setText(sdf.format(mParam_obj.getHappenTime()));
        tvRepairId.setText(String.valueOf(mParam_obj.getTroubleRecordId()));
        tvDeviceOperator.setText(mParam_obj.getDeviceUser());
        tvOperatorPhone.setText(mParam_obj.getPhone());
        tvTroubleSubmitter.setText(mParam_obj.getCreateUser());
        tvMaintainer.setText(mParam_obj.getRepairUserName());
        tvTroubleDesc.setText(mParam_obj.getRemark());
//        if (HttpClient.getInstance().getAuthUser().getUserName().equals(mParam_obj.getRepairUserName())) {
//            bIRepair.setVisibility(View.GONE);
//        }

        switch (mParam_mode) {
            case HttpClient.REQUEST_TROUBLE_WAIT_REPAIR:
                bValidateRepair.setVisibility(View.GONE);
                break;
            case HttpClient.REQUEST_TROUBLE_REPAIRING:
            case HttpClient.REQUEST_TROUBLE_WAIT_AUDIT:
            case Constant.FOR_DEVICE_ID:
                bValidateRepair.setVisibility(View.GONE);
            case HttpClient.REQUEST_TROUBLE_WAIT_VALIDATE:
                bINotRepair.setVisibility(View.GONE);
                bIRepair.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        return view;
    }

    @Override
    protected void loadData() {
        final List<String> imageUrls = mParam_obj.getImageKeys();
        if(imageUrls != null) {
            for (String url :
                    imageUrls) {
                View view = getActivity().getLayoutInflater().inflate(R.layout.item_image_browe_layout, null);
                llImages.addView(view);
                ImageView imageView = (ImageView) view.findViewById(R.id.image_button);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new ShowImagesDialog(getActivity(), imageUrls).show();
                    }
                });
                Glide.with(getActivity()).load(url).placeholder(R.drawable.dialog_loading).into(imageView);
            }
        }
    }

    @Override
    protected void buildUI() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.b_i_repair, R.id.b_i_not_repair, R.id.b_validate_repair})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.b_i_repair: {
                HttpClient client = HttpClient.getInstance();
                RequestParams params = new RequestParams();
                params.put("id", mParam_obj.getTroubleRecordId());
                client.requestGrabRepairTask(getContext(), this, params);
                break;
            }
            case R.id.b_i_not_repair: {
                HttpClient client = HttpClient.getInstance();
                RequestParams params = new RequestParams();
                params.put("id", mParam_obj.getTroubleRecordId());
                client.requestGiveUpRepairTask(getContext(), this, params);
                break;
            }
            case R.id.b_validate_repair: {
                Intent intent = new Intent(getActivity(), RepairValidateActivity.class);
                intent.putExtra("id", mParam_obj.getTroubleRecordId());
                startActivityForResult(intent, VALIDATE_REPAIR);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VALIDATE_REPAIR && resultCode == Activity.RESULT_OK) {
            bValidateRepair.setEnabled(false);
            Intent intent = new Intent();
            intent.putExtra("id", mParam_obj.getTroubleRecordId());
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        switch (requestType) {
            case HttpClient.REQUEST_REPAIR_GRAB_URL:
                tvMaintainer.setText(HttpClient.getInstance().getAuthUser().getUserName());
//                bIRepair.setEnabled(false);
//                bINotRepair.setEnabled(true);
                break;
            case HttpClient.REQUEST_REPAIR_GIVEUP_URL:
                tvMaintainer.setText("");
//                bIRepair.setEnabled(true);
//                bINotRepair.setEnabled(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailure(int requestType) {

    }
}
