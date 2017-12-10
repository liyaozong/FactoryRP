package tech.yozo.factoryrp.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.req.AddDeviceReq;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DeviceAddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
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
    EditText etBuyDate;

    private DatePickerDialog dateDialog;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_add);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.device_bar_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_device:
                attemptSaveDevice();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void attemptSaveDevice() {
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
            //TODO
            deviceCode = "DEV" + System.currentTimeMillis();
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            HttpClient client = HttpClient.getInstance();
            AddDeviceReq newDevice = new AddDeviceReq();
            newDevice.setCode(deviceCode);
            newDevice.setName(deviceName);
            newDevice.setSpecification(etDeviceType.getText().toString());
            //TODO
            newDevice.setDeviceType((long) spinnerDeviceCategory.getSelectedItemPosition());
            newDevice.setDeviceFlag(spinnerDeviceIdentify.getSelectedItem().toString());
            newDevice.setUseStatus(spinnerDeviceStatus.getSelectedItemPosition());
            newDevice.setUseDept((long) spinnerDeviceDept.getSelectedItemPosition());
            newDevice.setInstallationAddress(etDevicePlace.getText().toString());
            newDevice.setOperator(etDeviceOperator.getText().toString());
            newDevice.setBuyDate((Date) etBuyDate.getTag());
//            newDevice.setManufacturer(etDeviceManufacturer.getText().toString());
//            newDevice.setSupplier(etDeviceProvider.getText().toString());
            newDevice.setRemark(etDeviceDesc.getText().toString());
            StringEntity param = new StringEntity(JSON.toJSONString(newDevice), Charset.forName("UTF-8"));
            client.post(null, HttpClient.DEVICE_SAVE, param, saveDeviceResponse);
        }
    }

    private JsonHttpResponseHandler saveDeviceResponse = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Log.d("INFO", response.toString());
            try {
                if (ErrorCode.SUCCESS.getCode().equals(response.getString("errorCode"))) {
                    Toast.makeText(DeviceAddActivity.this, R.string.hint_device_save_success, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DeviceAddActivity.this, R.string.failure_save, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(DeviceAddActivity.this, R.string.exception_message, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Toast.makeText(DeviceAddActivity.this, R.string.failure_request, Toast.LENGTH_SHORT).show();
        }
    };

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
                startActivityForResult(new Intent(this, CaptureActivity.class), SCAN_CODE);
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
}
