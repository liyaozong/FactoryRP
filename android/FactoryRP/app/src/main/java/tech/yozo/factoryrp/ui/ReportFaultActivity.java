package tech.yozo.factoryrp.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.scan.CaptureActivity;
import tech.yozo.factoryrp.scan.Intents;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.DictSpinnerAdapter;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.req.AddTroubleRecordReq;
import tech.yozo.factoryrp.vo.resp.DeviceParamDicEnumResp;
import tech.yozo.factoryrp.vo.resp.device.info.SimpleDeviceInfoResp;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportFaultActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, HttpClient.OnHttpListener {
    private static final int REQUEST_CODE_SCAN = 0x900;
    private static final int REQUEST_CODE_DEVICE = 0x901;
    private static final int REQUEST_CODE_CAMERA = 0x902;

    @BindView(R.id.b_select_device)
    Button bSelectDevice;
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
    EditText etFaultTime;
    @BindView(R.id.spinner_fault_level)
    Spinner spinnerFaultLevel;
    @BindView(R.id.spinner_fault_type)
    Spinner spinnerFaultType;
    @BindView(R.id.spinner_repair_group)
    Spinner spinnerRepairGroup;
    @BindView(R.id.spinner_device_status)
    Spinner spinnerDeviceStatus;
    @BindView(R.id.et_operator)
    EditText etOperator;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_device_place)
    EditText etDevicePlace;
    @BindView(R.id.et_fault_desc)
    EditText etFaultDesc;
    @BindView(R.id.ib_capture)
    ImageButton ibCapture;
    @BindView(R.id.ll_images)
    LinearLayout llImages;

    private long selectedDeviceId = -1;

    private int mImageCount;
    private DatePickerDialog dateDialog;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时MM分");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_fault);
        ButterKnife.bind(this);

        Date now = new Date();
        etFaultTime.setText(sdf.format(now));
        etFaultTime.setTag(now);

        HttpClient client = HttpClient.getInstance();
        client.initDeviceDict(this, this, Constant.DICT_TROUBLE_LEVEL);
        client.initDeviceDict(this, this, Constant.DICT_DEVICE_STATUS);
        //TODO 维修班组 故障类别
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
        //TODO
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

        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            HttpClient client = HttpClient.getInstance();
            AddTroubleRecordReq reportReq = new AddTroubleRecordReq();
            reportReq.setDeviceId(selectedDeviceId);
            reportReq.setHappenTime((Date) etFaultTime.getTag());
            reportReq.setTroubleLevel(spinnerFaultLevel.getSelectedItemId());
            reportReq.setTroubleType(spinnerFaultType.getSelectedItemId());
            reportReq.setRepairGroupId(spinnerRepairGroup.getSelectedItemId());
            reportReq.setDeviceStatus(spinnerDeviceStatus.getSelectedItemId());
            reportReq.setDeviceUser(etOperator.getText().toString());
            reportReq.setPhone(etPhoneNumber.getText().toString());
            reportReq.setDeviceAddress(etDevicePlace.getText().toString());
            reportReq.setRemark(etFaultDesc.getText().toString());
            //TODO 图片处理

            StringEntity param = new StringEntity(JSON.toJSONString(reportReq), Charset.forName("UTF-8"));
            client.post(this, HttpClient.TROUBLE_ADD, param, reportFaultResponse);
        }
    }

    private JsonHttpResponseHandler reportFaultResponse = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Log.d("INFO", response.toString());
            try {
                if (ErrorCode.SUCCESS.getCode().equals(response.getString("errorCode"))) {
                    Toast.makeText(ReportFaultActivity.this, R.string.hint_report_fault_success, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ReportFaultActivity.this, R.string.failure_service, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ReportFaultActivity.this, R.string.failure_data_parse, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Toast.makeText(ReportFaultActivity.this, R.string.failure_request, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    if (mImageCount <= 3) {
                        if (data != null && data.hasExtra("data")) {
                            mImageCount++;
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            ImageView view = new ImageView(this);
                            view.setImageBitmap(thumbnail);
                            view.setPadding(6, 2, 6, 2);
                            llImages.addView(view);
                        }
                    } else {
                        Toast.makeText(this, R.string.hint_image_count, Toast.LENGTH_SHORT).show();
                    }
                    break;
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
                            //TODO
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

    @OnClick({R.id.b_select_device, R.id.iv_select_device, R.id.ib_capture, R.id.et_fault_time})
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
                    startActivityForResult(new Intent(this, CaptureActivity.class), REQUEST_CODE_SCAN);
                }
                break;
            }
            case R.id.ib_capture: {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CAMERA}, Constant.REQUEST_CAMERA_PERMISSION);
                }else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                }
                break;
            }
            case R.id.et_fault_time: {
                if (dateDialog == null) {
                    final Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    dateDialog = new DatePickerDialog(this, this, year, month, day);
                }
                dateDialog.show();
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
        tlSelectDevice.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        etFaultTime.setTag(calendar.getTime());
        etFaultTime.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constant.REQUEST_SCAN_PERMISSION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(this, CaptureActivity.class), REQUEST_CODE_SCAN);
            } else {
                Toast.makeText(this, R.string.failure_camera_permission, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == Constant.REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            } else {
                Toast.makeText(this, R.string.failure_camera_permission, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onHttpSuccess() {
        HttpClient client = HttpClient.getInstance();
        List<DeviceParamDicEnumResp> troubleLevelDict = client.getTroubleLevelDict();
        if(troubleLevelDict != null) {
            spinnerFaultLevel.setAdapter(new DictSpinnerAdapter(this, android.R.layout.simple_list_item_1, troubleLevelDict));
        }
        List<DeviceParamDicEnumResp> deviceUseStatusDict = client.getDeviceUseStatusDict();
        if(deviceUseStatusDict != null) {
            spinnerDeviceStatus.setAdapter(new DictSpinnerAdapter(this, android.R.layout.simple_list_item_1, deviceUseStatusDict));
        }
    }
}
