package tech.yozo.factoryrp.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.entity.Department;
import tech.yozo.factoryrp.entity.DeviceType;
import tech.yozo.factoryrp.scan.Intents;
import tech.yozo.factoryrp.scan.ScanActivity;
import tech.yozo.factoryrp.ui.dialog.LoadingDialog;
import tech.yozo.factoryrp.utils.*;
import tech.yozo.factoryrp.vo.req.AddDeviceReq;
import tech.yozo.factoryrp.vo.resp.ContactCompany;
import tech.yozo.factoryrp.vo.resp.DeviceParamDicEnumResp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DeviceAddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, HttpClient.OnHttpListener {
    private static final int SCAN_CODE = 1;

    @BindView(R.id.et_device_code)
    EditText etDeviceCode;
    @BindView(R.id.et_device_name)
    EditText etDeviceName;
    @BindView(R.id.et_device_type)
    EditText etDeviceType;
    @BindView(R.id.spinner_device_category)
    Spinner spinnerDeviceCategory;
    @BindView(R.id.spinner_device_identify)
    Spinner spinnerDeviceIdentify;
    @BindView(R.id.spinner_device_status)
    Spinner spinnerDeviceStatus;
    @BindView(R.id.spinner_device_dept)
    Spinner spinnerDeviceDept;
    @BindView(R.id.et_device_place)
    EditText etDevicePlace;
    @BindView(R.id.et_device_operator)
    EditText etDeviceOperator;
    @BindView(R.id.et_device_manufacturer)
    AutoCompleteTextView etDeviceManufacturer;
    @BindView(R.id.et_device_provider)
    AutoCompleteTextView etDeviceProvider;
    @BindView(R.id.et_device_desc)
    EditText etDeviceDesc;
    @BindView(R.id.et_buy_date)
    TextView etBuyDate;

    private DatePickerDialog dateDialog;
    private LoadingDialog dialog;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_add);
        ButterKnife.bind(this);

        HttpClient client = HttpClient.getInstance();
        if(client.getDictEnum(Constant.DICT_DEVICE_STATUS) == null) {
            client.requestDeviceDict(this, this, Constant.DICT_DEVICE_STATUS);
        } else {
            updateUI(HttpClient.REQUEST_DATA_DICT);
        }

        if(client.getDictEnum(Constant.DICT_DEVICE_IDENTIFY) == null) {
            client.requestDeviceDict(this, this, Constant.DICT_DEVICE_IDENTIFY);
        } else {
            updateUI(HttpClient.REQUEST_DATA_DICT);
        }

        if (client.getContactCompanies() == null) {
            client.requestContactCompany(this, this);
        } else {
            updateUI(HttpClient.REQUEST_CONTACT_COMPANY);
        }

        if(client.getDeviceTypeList() == null)  {
            client.requestDeviceType(this, this);
        } else {
            updateUI(HttpClient.REQUEST_DEVICE_TYPE_URL);
        }

        if(client.getDepartmentList() == null) {
            client.requestDeptList(this, this);
        } else {
            updateUI(HttpClient.REQUEST_DEPARTMENT_LIST_URL);
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
                attemptSaveDevice();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void attemptSaveDevice() {
        HttpClient client = HttpClient.getInstance();
        etDeviceName.setError(null);

        boolean cancel = false;
        View focusView = null;

        String deviceName = etDeviceName.getText().toString();
        if (TextUtils.isEmpty(deviceName)) {
            etDeviceName.setError(getString(R.string.error_field_required));
            focusView = etDeviceName;
            cancel = true;
        }

        String deviceCode = etDeviceCode.getText().toString();
        if (TextUtils.isEmpty(deviceCode)) {
            deviceCode = "DEV" + System.currentTimeMillis();
        } else if(deviceCode.length() > 17) {
            etDeviceCode.setError(getString(R.string.error_too_long));
            focusView = etDeviceName;
            cancel = true;
        }

        long manufacturerId = -1;
        long providerId = -1;
        if(client.getContactCompanies() != null) {
            for (ContactCompany cc : client.getContactCompanies()) {
                if (cc.getName().contentEquals(etDeviceManufacturer.getText())) {
                    manufacturerId = cc.getId();
                } else if (cc.getName().contentEquals(etDeviceProvider.getText())) {
                    providerId = cc.getId();
                }
            }
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            AddDeviceReq newDevice = new AddDeviceReq();
            newDevice.setCode(deviceCode);
            newDevice.setName(deviceName);
            newDevice.setSpecification(etDeviceType.getText().toString());
            newDevice.setDeviceType(spinnerDeviceCategory.getSelectedItemId());
            newDevice.setDeviceFlag(((DeviceParamDicEnumResp)spinnerDeviceIdentify.getSelectedItem()).getName());
            newDevice.setUseStatus(spinnerDeviceStatus.getSelectedItemId());
            newDevice.setUseDept(spinnerDeviceDept.getSelectedItemId());
            newDevice.setInstallationAddress(etDevicePlace.getText().toString());
            newDevice.setOperator(etDeviceOperator.getText().toString());
            newDevice.setBuyDate((Date) etBuyDate.getTag());
            if(manufacturerId != -1) {
                newDevice.setManufacturer(manufacturerId);
            }
            if(providerId != -1) {
                newDevice.setSupplier(providerId);
            }
            newDevice.setRemark(etDeviceDesc.getText().toString());

            LoadingDialog.Builder builder = new LoadingDialog.Builder(this)
                    .setMessage(R.string.loading_save);
            dialog = builder.create();
            dialog.show();
            client.requestDeviceAdd(this, this, newDevice);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SCAN_CODE:
                    etDeviceCode.setText(data.getStringExtra(Intents.Scan.RESULT));
            }
        }
    }

    @OnClick({R.id.ib_scan_device, R.id.et_buy_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_scan_device:
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CAMERA},Constant.REQUEST_SCAN_PERMISSION);
                }else {
                    startActivityForResult(new Intent(this, ScanActivity.class), SCAN_CODE);
                }
                break;
            case R.id.et_buy_date:
                if(dateDialog == null) {
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

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        etBuyDate.setTag(calendar.getTime());
        etBuyDate.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        if(requestType == HttpClient.REQUEST_DEVICE_SAVE) {
            dialog.dismiss();
        }
        updateUI(requestType);
    }

    @Override
    public void onFailure(int requestType) {
        if(requestType == HttpClient.REQUEST_DEVICE_SAVE) {
            dialog.dismiss();
        }
    }

    private void updateUI(int requestType) {
        HttpClient client = HttpClient.getInstance();
        switch (requestType) {
            case HttpClient.REQUEST_DEVICE_SAVE:
                // 设备保存成功
                finish();
                break;
            case HttpClient.REQUEST_CONTACT_COMPANY:
                List<ContactCompany> contactCompanies = client.getContactCompanies();
                if(contactCompanies != null) {
                    List<String> dd = new ArrayList<>();
                    for(ContactCompany cc : contactCompanies) {
                        dd.add(cc.getName());
                    }
                    etDeviceManufacturer.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dd));
                    etDeviceProvider.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dd));
                }
                break;
            case HttpClient.REQUEST_DATA_DICT:
                List<DeviceParamDicEnumResp> deviceUseStatusDict = client.getDictEnum(Constant.DICT_DEVICE_STATUS);
                if(deviceUseStatusDict != null) {
                    spinnerDeviceStatus.setAdapter(new DictSpinnerAdapter(this, android.R.layout.simple_list_item_1, deviceUseStatusDict));
                }
                List<DeviceParamDicEnumResp> deviceIdentifyDict = client.getDictEnum(Constant.DICT_DEVICE_IDENTIFY);
                if(deviceIdentifyDict != null) {
                    spinnerDeviceIdentify.setAdapter(new DictSpinnerAdapter(this, android.R.layout.simple_list_item_1, deviceIdentifyDict));
                }
                break;
            case HttpClient.REQUEST_DEPARTMENT_LIST_URL:
                List<Department> departments = client.getDepartmentList();
                if(departments != null) {
                    spinnerDeviceDept.setAdapter(new DepartmentSpinnerAdapter(this, android.R.layout.simple_list_item_1, departments));
                }
                break;
            case HttpClient.REQUEST_DEVICE_TYPE_URL:
                List<DeviceType> deviceTypes = client.getDeviceTypeList();
                if(deviceTypes != null) {
                    spinnerDeviceCategory.setAdapter(new DeviceTypeSpinnerAdapter(this, android.R.layout.simple_list_item_1, deviceTypes));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constant.REQUEST_SCAN_PERMISSION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(this, ScanActivity.class), SCAN_CODE);
            } else {
                Toast.makeText(this, R.string.failure_camera_permission, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
