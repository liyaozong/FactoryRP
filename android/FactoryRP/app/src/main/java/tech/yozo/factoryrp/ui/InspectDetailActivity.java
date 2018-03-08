package tech.yozo.factoryrp.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.loopj.android.http.RequestParams;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.scan.ScanActivity;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.req.InspectRecordSubmitReq;
import tech.yozo.factoryrp.vo.req.InspectionItemAddReq;
import tech.yozo.factoryrp.vo.resp.inspect.InspectDeviceDetailResp;
import tech.yozo.factoryrp.vo.resp.inspect.InspectItemResp;

import java.util.*;

public class InspectDetailActivity extends AppCompatActivity implements HttpClient.OnHttpListener {
    private static final int REQUEST_CODE_CAMERA = 0x902;

    @BindView(R.id.tv_inspect_device_name)
    TextView tvInspectDeviceName;
    @BindView(R.id.ll_inspect_device_item)
    LinearLayout llInspectDeviceItem;
    @BindView(R.id.button_inspect_detail_save)
    Button buttonInspectDetailSave;
    @BindView(R.id.ib_capture)
    ImageButton ibCapture;
    @BindView(R.id.ll_images)
    LinearLayout llImages;

    private int mImageCount;
    private Long taskID;
    private String deviceCode;
    private InspectDeviceDetailResp inspectItemResp;
    private List<InspectionItemAddReq> inspectionItemAddReqList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_detail);
        ButterKnife.bind(this);

        taskID = getIntent().getLongExtra("TASK_ID", 0);
        deviceCode = getIntent().getStringExtra("DEVICE_CODE");
        RequestParams params = new RequestParams();
        params.put("planId", taskID);
        params.put("deviceCode", deviceCode);
        HttpClient.getInstance().requestInspectItem(this, this, params);
    }

    @OnClick({R.id.button_inspect_detail_save, R.id.ib_capture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_inspect_detail_save: {
                if(inspectionItemAddReqList.size() == 0) {
                    Toast.makeText(this, R.string.failure_save_inspect, Toast.LENGTH_SHORT).show();
//            finish();
                    return;
                }
                InspectRecordSubmitReq req = new InspectRecordSubmitReq();
                req.setPlanId(taskID);
                req.setDeviceCode(deviceCode);
                if(inspectItemResp != null) {
                    req.setSpotInspectionStandard(inspectItemResp.getSpotInspectionStandard());
                    req.setDeviceName(inspectItemResp.getDeviceName());

                    req.setDetailList(inspectionItemAddReqList);
                }

                HttpClient.getInstance().requestInspectSubmit(this, this, req);
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
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    if (mImageCount < 4) {
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
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constant.REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            } else {
                Toast.makeText(this, R.string.failure_camera_permission, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addView(final InspectItemResp item) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(16, 16, 16, 16);
        LinearLayout lineLayout = new LinearLayout(this);
        lineLayout.setOrientation(LinearLayout.HORIZONTAL);
        lineLayout.setLayoutParams(params);
        lineLayout.setGravity(Gravity.CENTER_VERTICAL);

        TextView inpsectItem = new TextView(this);
        inpsectItem.setWidth(400);
        inpsectItem.setText(item.getName());
        lineLayout.addView(inpsectItem);

        if(item.getInspectionStatus() != null && item.getInspectionStatus() == 2) {
            final InspectionItemAddReq result = new InspectionItemAddReq();
            result.setItemId(item.getItemId());
            inspectionItemAddReqList.add(result);

            if ("options".equalsIgnoreCase(item.getRecordTypeName()) || "exception".equalsIgnoreCase(item.getRecordTypeName())) { //勾选
                RadioGroup radioGroup = new RadioGroup(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                radioGroup.setLayoutParams(lp);
                radioGroup.setOrientation(LinearLayout.HORIZONTAL);
                for (String value :
                        item.getInputLimitValue()) {
                    RadioButton yesButton = new RadioButton(this);
                    yesButton.setText(value);
                    radioGroup.addView(yesButton);
                }
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton x = (RadioButton) findViewById(i);
                        result.setRecordResult(x.getText().toString());
                    }
                });
                lineLayout.addView(radioGroup);
            } else if ("table".equalsIgnoreCase(item.getRecordTypeName())) { //列表
                Spinner spinner = new Spinner(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                spinner.setLayoutParams(lp);
                spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item.getInputLimitValue()));
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        result.setRecordResult(item.getInputLimitValue().get(i));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                lineLayout.addView(spinner);
            } else if ("number".equalsIgnoreCase(item.getRecordTypeName())) { //数值
                EditText editText = new EditText(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                editText.setLayoutParams(lp);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setHint("参考范围：" + item.getLowerLimit() + " - " + item.getUpperLimit());
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.length() > 0) {
                            result.setRecordResult(editable.toString());
                        }
                    }
                });
                lineLayout.addView(editText);
            } else if ("verbal_description".equalsIgnoreCase(item.getRecordTypeName())) { //文字描述
                EditText editText = new EditText(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                editText.setLayoutParams(lp);
                editText.setMinLines(3);
                editText.setHint("文字描述");
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.length() > 0) {
                            result.setRecordResult(editable.toString());
                        }
                    }
                });
                lineLayout.addView(editText);
            }
        } else {
            if ("options".equalsIgnoreCase(item.getRecordTypeName()) || "exception".equalsIgnoreCase(item.getRecordTypeName())) { //勾选
                RadioGroup radioGroup = new RadioGroup(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                radioGroup.setLayoutParams(lp);
                radioGroup.setOrientation(LinearLayout.HORIZONTAL);
                for (String value :
                        item.getInputLimitValue()) {
                    RadioButton yesButton = new RadioButton(this);
                    yesButton.setText(value);
                    yesButton.setEnabled(false);
                    radioGroup.addView(yesButton);
                }
                lineLayout.addView(radioGroup);
            } else if ("table".equalsIgnoreCase(item.getRecordTypeName())) { //列表
                Spinner spinner = new Spinner(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                spinner.setLayoutParams(lp);
                spinner.setEnabled(false);
                spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item.getInputLimitValue()));
                lineLayout.addView(spinner);
            } else if ("number".equalsIgnoreCase(item.getRecordTypeName())) { //数值
                EditText editText = new EditText(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                editText.setLayoutParams(lp);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setHint("参考范围：" + item.getLowerLimit() + " - " + item.getUpperLimit());
                editText.setEnabled(false);
                lineLayout.addView(editText);
            } else if ("verbal_description".equalsIgnoreCase(item.getRecordTypeName())) { //文字描述
                EditText editText = new EditText(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                editText.setLayoutParams(lp);
                editText.setMinLines(3);
                editText.setHint("文字描述");
                editText.setEnabled(false);
                lineLayout.addView(editText);
            }
        }

        llInspectDeviceItem.addView(lineLayout);

//        if("options".equalsIgnoreCase(item.getRecordTypeName()) || "table".equalsIgnoreCase(item.getRecordTypeName())) {
//            addExceptionSelect(item);
//            return;
//        }

        ImageView divide = new ImageView(this);
        divide.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
        divide.setBackgroundColor(R.color.colorBackground);
        llInspectDeviceItem.addView(divide);
    }

    private void addExceptionSelect(InspectItemResp item) {
        InspectItemResp i = new InspectItemResp();
        i.setName(item.getName());
        i.setRecordTypeName("exception");
        List<String> v = new ArrayList<>();
        v.add("正常");
        v.add("异常");
        i.setInputLimitValue(v);
        addView(i);
    }

    private void initUI() {
        if(inspectItemResp == null) {
            return;
        }
        tvInspectDeviceName.setText(inspectItemResp.getDeviceName());

        for (InspectItemResp item :
                inspectItemResp.getItemList()) {
            addView(item);
        }
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        switch (requestType) {
            case HttpClient.REQUEST_INSPECT_ITEM:
                inspectItemResp = (InspectDeviceDetailResp) obj;
                initUI();
                break;
            case HttpClient.REQUEST_SUBMIT_INSPECT_RESULT:
                finish();
                break;
        }
    }

    @Override
    public void onFailure(int requestType) {

    }
}
