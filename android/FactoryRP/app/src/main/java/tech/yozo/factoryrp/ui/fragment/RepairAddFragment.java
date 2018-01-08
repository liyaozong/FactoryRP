package tech.yozo.factoryrp.ui.fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.loopj.android.http.RequestParams;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.scan.Intents;
import tech.yozo.factoryrp.scan.ScanActivity;
import tech.yozo.factoryrp.ui.DeviceSelectActivity;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.device.info.SimpleDeviceInfoResp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RepairAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepairAddFragment extends Fragment implements DatePickerDialog.OnDateSetListener, HttpClient.OnHttpListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int REQUEST_CODE_SCAN = 0x900;
    private static final int REQUEST_CODE_DEVICE = 0x901;

    @BindView(R.id.b_select_device)
    Button bSelectDevice;
    @BindView(R.id.iv_select_device)
    ImageView ivSelectDevice;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_device_code)
    TextView tvDeviceCode;
    @BindView(R.id.tv_device_type)
    TextView tvDeviceType;
    @BindView(R.id.tv_device_using_dept)
    TextView tvDeviceUsingDept;
    @BindView(R.id.tl_select_device)
    TableLayout tlSelectDevice;
    @BindView(R.id.et_fault_time)
    Button etFaultTime;
    @BindView(R.id.et_fault_desc)
    EditText etFaultDesc;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatePickerDialog dateDialog;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时MM分");

    public RepairAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RepairAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RepairAddFragment newInstance(String param1, String param2) {
        RepairAddFragment fragment = new RepairAddFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repair_add, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.b_select_device, R.id.iv_select_device, R.id.et_fault_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.b_select_device:
                Intent intent = new Intent(getContext(), DeviceSelectActivity.class);
                startActivityForResult(intent, REQUEST_CODE_DEVICE);
                break;
            case R.id.iv_select_device:
                if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.CAMERA}, Constant.REQUEST_SCAN_PERMISSION);
                }else {
                    startActivityForResult(new Intent(getContext(), ScanActivity.class), REQUEST_CODE_SCAN);
                }
                break;
            case R.id.et_fault_time:
                if (dateDialog == null) {
                    final Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    dateDialog = new DatePickerDialog(getContext(), this, year, month, day);
                }
                dateDialog.show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN:
                    if (data != null) {
                        List<SimpleDeviceInfoResp> list = HttpClient.getInstance().getSimpleDeviceList();
                        if(list != null) {
                            for (SimpleDeviceInfoResp device : list) {
                                if (device.getCode().contentEquals(data.getStringExtra(Intents.Scan.RESULT))) {
                                    showSelectedDevice(device);
                                }
                            }
                        } else {
                            RequestParams params = new RequestParams();
                            params.put("code", data.getStringExtra(Intents.Scan.RESULT));
                            HttpClient.getInstance().requestDeviceByCode(getContext(), this, params);
                        }
                    }
                    break;
                case REQUEST_CODE_DEVICE:
                    if (data != null) {
                        SimpleDeviceInfoResp device = (SimpleDeviceInfoResp) data.getSerializableExtra(DeviceSelectActivity.DEVICE_OBJECT);
                        showSelectedDevice(device);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        etFaultTime.setTag(calendar.getTime());
        etFaultTime.setText(sdf.format(calendar.getTime()));
    }

    private void showSelectedDevice(SimpleDeviceInfoResp device) {
        tvDeviceName.setText(device.getName());
        tvDeviceCode.setText(device.getCode());
        tvDeviceType.setText(device.getSpecification());
        tvDeviceUsingDept.setText(device.getUseDept());
        tlSelectDevice.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        if (requestType == HttpClient.REQUEST_DEVICE_GET_BY_CODE) {
            FullDeviceInfoResp device = (FullDeviceInfoResp) obj;
            showSelectedDevice(device);
        }
    }

    @Override
    public void onFailure(int requestType) {

    }
}
