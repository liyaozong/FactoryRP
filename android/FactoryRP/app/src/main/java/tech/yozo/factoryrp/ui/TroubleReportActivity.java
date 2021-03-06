package tech.yozo.factoryrp.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.loopj.android.http.RequestParams;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.entity.RepairGroup;
import tech.yozo.factoryrp.scan.Intents;
import tech.yozo.factoryrp.scan.ScanActivity;
import tech.yozo.factoryrp.ui.dialog.LoadingDialog;
import tech.yozo.factoryrp.ui.dialog.TimePickerDialog;
import tech.yozo.factoryrp.ui.fragment.UploadImageFragment;
import tech.yozo.factoryrp.utils.*;
import tech.yozo.factoryrp.vo.req.AddTroubleRecordReq;
import tech.yozo.factoryrp.vo.resp.DeviceParamDicEnumResp;
import tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.device.info.SimpleDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.device.trouble.DeviceTroubleTypeVo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TroubleReportActivity extends AppCompatActivity implements HttpClient.OnHttpListener, UploadImageFragment.OnFragmentInteractionListener {
    private static final int REQUEST_CODE_SCAN = 0x900;
    private static final int REQUEST_CODE_DEVICE = 0x901;

    @BindView(R.id.b_select_device)
    TextView bSelectDevice;
    @BindView(R.id.iv_select_device)
    ImageView ivSelectDevice;
    @BindView(R.id.tl_select_device)
    TableLayout tlSelectDevice;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_device_code)
    TextView tvDeviceCode;
    @BindView(R.id.tv_device_type)
    TextView tvDeviceType;
    @BindView(R.id.tv_device_using_dept)
    TextView tvDeviceUsingDept;
    @BindView(R.id.et_fault_time)
    TextView etFaultTime;
    @BindView(R.id.spinner_fault_level)
    Spinner spinnerFaultLevel;
    @BindView(R.id.spinner_fault_type)
    Spinner spinnerFaultType;
    @BindView(R.id.spinner_repair_group)
    Spinner spinnerRepairGroup;
    @BindView(R.id.spinner_device_running_status)
    Spinner spinnerDeviceRunningStatus;
    @BindView(R.id.et_operator)
    EditText etOperator;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_device_place)
    EditText etDevicePlace;
    @BindView(R.id.et_fault_desc)
    EditText etFaultDesc;

    private long selectedDeviceId = -1;

    private List<String> imageKey = new ArrayList<>();
    private LoadingDialog dialog;
    private TimePickerDialog dateDialog;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_trouble);
        ButterKnife.bind(this);

        Date now = new Date();
        etFaultTime.setText(sdf.format(now));
        etFaultTime.setTag(now);

        HttpClient client = HttpClient.getInstance();
        if(client.getDictEnum(Constant.DICT_TROUBLE_LEVEL) == null) {
            client.requestDeviceDict(this, this, Constant.DICT_TROUBLE_LEVEL);
        } else {
            updateUI(HttpClient.REQUEST_DATA_DICT);
        }

        if(client.getRepairGroupList() == null) {
            client.requestRepairGroup(this, this);
        } else {
            updateUI(HttpClient.REQUEST_REPAIR_GROUP_URL);
        }

        if(client.getTroubleTypeVoList() == null) {
            client.requestTroubleType(this, this);
        } else {
            updateUI(HttpClient.REQUEST_DEVICE_TROUBLE_TYPE_URL);
        }

        if(client.getDictEnum(Constant.DICT_DEVICE_RUNNING_STATUS) == null) {
            client.requestDeviceDict(this, this, Constant.DICT_DEVICE_RUNNING_STATUS);
        } else {
            updateUI(HttpClient.REQUEST_DATA_DICT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.device_bar_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                attemptSaveTrouble();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void attemptSaveTrouble() {
        boolean cancel = false;
        View focusView = null;

        if (selectedDeviceId == -1) {
            cancel = true;
            Toast.makeText(this, R.string.hint_select_a_device, Toast.LENGTH_SHORT).show();
        }

        String faultDesc = etFaultDesc.getText().toString();
        if (TextUtils.isEmpty(faultDesc)) {
            cancel = true;
            focusView = etFaultDesc;
            etFaultDesc.setError(getString(R.string.error_field_required));
        }

        Date happenTime = null;
        try {
            happenTime = sdf.parse(etFaultTime.getText().toString());
        } catch (ParseException e) {
            cancel = true;
            Toast.makeText(this, R.string.hint_error_time, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            HttpClient client = HttpClient.getInstance();
            AddTroubleRecordReq reportReq = new AddTroubleRecordReq();
            reportReq.setDeviceId(selectedDeviceId);
            reportReq.setHappenTime(happenTime);
            reportReq.setTroubleLevel(spinnerFaultLevel.getSelectedItemId());
            reportReq.setTroubleType(spinnerFaultType.getSelectedItemId());
            reportReq.setRepairGroupId(spinnerRepairGroup.getSelectedItemId());
            reportReq.setDeviceStatus(spinnerDeviceRunningStatus.getSelectedItemId());
            reportReq.setDeviceUser(etOperator.getText().toString());
            reportReq.setPhone(etPhoneNumber.getText().toString());
            reportReq.setDeviceAddress(etDevicePlace.getText().toString());
            reportReq.setRemark(etFaultDesc.getText().toString());
            if(imageKey.size() > 0) {
                reportReq.setImageKeys(imageKey);
            }

            LoadingDialog.Builder builder = new LoadingDialog.Builder(this)
                    .setMessage(R.string.loading_report);
            dialog = builder.create();
            dialog.show();
            client.requestReportFault(this, this, reportReq);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN:
                    if (data != null) {
                        RequestParams params = new RequestParams();
                        params.put("code", data.getStringExtra(Intents.Scan.RESULT));
                        HttpClient.getInstance().requestDeviceByCode(this, this, params);
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

    @OnClick({R.id.b_select_device, R.id.iv_select_device, R.id.et_fault_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.b_select_device: {
                Intent intent = new Intent(this, DeviceSelectActivity.class);
                startActivityForResult(intent, REQUEST_CODE_DEVICE);
                break;
            }
            case R.id.iv_select_device: {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CAMERA}, Constant.REQUEST_SCAN_PERMISSION);
                }else {
                    startActivityForResult(new Intent(this, ScanActivity.class), REQUEST_CODE_SCAN);
                }
                break;
            }
            case R.id.et_fault_time: {
//                if (dateDialog == null) {
//                    dateDialog = new TimePickerDialog(this, new TimePickerDialog.TimePickerDialogInterface() {
//                        @Override
//                        public void positiveListener() {
//                            StringBuffer buffer = new StringBuffer();
//                            buffer.append(dateDialog.getYear()).append("年")
//                                    .append(dateDialog.getMonth()).append("月")
//                                    .append(dateDialog.getDay()).append("日 ")
//                                    .append(dateDialog.getHour()).append(":")
//                                    .append(dateDialog.getMinute());
//                            etFaultTime.setText(buffer.toString());
//                        }
//
//                        @Override
//                        public void negativeListener() {
//
//                        }
//                    });
//                }
//                dateDialog.showDateAndTimePickerDialog();
                break;
            }
        }
    }

    private void showSelectedDevice(SimpleDeviceInfoResp device) {
        selectedDeviceId = device.getId();
        tvDeviceName.setText(device.getName());
        tvDeviceCode.setText(device.getCode());
        tvDeviceType.setText(device.getSpecification());
        tvDeviceUsingDept.setText(device.getUseDept());
        etDevicePlace.setText(device.getInstallationAddress());
        if(device instanceof FullDeviceInfoResp) {
            etOperator.setText(((FullDeviceInfoResp) device).getOperator());
        }
        tlSelectDevice.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constant.REQUEST_SCAN_PERMISSION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(this, ScanActivity.class), REQUEST_CODE_SCAN);
            } else {
                Toast.makeText(this, R.string.failure_camera_permission, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        switch (requestType) {
            case HttpClient.REQUEST_TROUBLE_ADD:
                dialog.dismiss();
                updateUI(requestType);
                break;
            case HttpClient.REQUEST_DEVICE_GET_BY_CODE:
                FullDeviceInfoResp device = (FullDeviceInfoResp) obj;
                showSelectedDevice(device);
                updateUI(requestType);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailure(int requestType) {
        if(dialog != null) {
            dialog.dismiss();
        }
    }

    private void updateUI(int requestType) {
        HttpClient client = HttpClient.getInstance();
        switch (requestType) {
            case HttpClient.REQUEST_TROUBLE_ADD:
                setResult(RESULT_OK);
                finish();
                break;
            case HttpClient.REQUEST_DATA_DICT:
                List<DeviceParamDicEnumResp> troubleLevelDict = client.getDictEnum(Constant.DICT_TROUBLE_LEVEL);
                if(troubleLevelDict != null) {
                    spinnerFaultLevel.setAdapter(new DictSpinnerAdapter(this, android.R.layout.simple_list_item_1, troubleLevelDict));
                }

                List<DeviceParamDicEnumResp> deviceRunningStatus = client.getDictEnum(Constant.DICT_DEVICE_RUNNING_STATUS);
                if(deviceRunningStatus != null) {
                    spinnerDeviceRunningStatus.setAdapter(new DictSpinnerAdapter(this, android.R.layout.simple_list_item_1, deviceRunningStatus));
                }
                break;
            case HttpClient.REQUEST_DEVICE_TROUBLE_TYPE_URL:
                List<DeviceTroubleTypeVo> troubleTypeVoList = client.getTroubleTypeVoList();
                if(troubleTypeVoList != null) {
                    spinnerFaultType.setAdapter(new TroubleTypeSpinnerAdapter(this, android.R.layout.simple_list_item_1, troubleTypeVoList));
                }
                break;
            case HttpClient.REQUEST_REPAIR_GROUP_URL:
                List<RepairGroup> repairGroupList = client.getRepairGroupList();
                if(repairGroupList != null) {
                    spinnerRepairGroup.setAdapter(new GroupSpinnerAdapter(this, android.R.layout.simple_list_item_1, repairGroupList));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onImageUploadSuccess(String uri) {
        imageKey.add(uri);
    }
}
