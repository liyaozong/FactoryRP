package tech.yozo.factoryrp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.dialog.LoadingDialog;
import tech.yozo.factoryrp.ui.fragment.UploadImageFragment;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.req.InspectRecordSubmitReq;
import tech.yozo.factoryrp.vo.req.InspectionItemAddReq;
import tech.yozo.factoryrp.vo.resp.inspect.InspectDeviceDetailResp;
import tech.yozo.factoryrp.vo.resp.inspect.InspectItemResp;

import java.util.*;

public class InspectDetailActivity extends AppCompatActivity implements HttpClient.OnHttpListener, UploadImageFragment.OnFragmentInteractionListener {
    @BindView(R.id.tv_inspect_device_name)
    TextView tvInspectDeviceName;
    @BindView(R.id.ll_inspect_device_item)
    LinearLayout llInspectDeviceItem;
    @BindView(R.id.button_inspect_detail_save)
    Button buttonInspectDetailSave;

    private int mCount;
    private Long taskID;
    private String deviceCode;
    private InspectDeviceDetailResp inspectItemResp;
    private List<InspectionItemAddReq> inspectionItemAddReqList = new ArrayList<>();
    private List<String> imageKey = new ArrayList<>();

    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_detail);
        ButterKnife.bind(this);

        taskID = getIntent().getLongExtra("TASK_ID", 0);
        deviceCode = getIntent().getStringExtra("DEVICE_CODE");

        LoadingDialog.Builder builder = new LoadingDialog.Builder(this)
                .setMessage(R.string.loading_loading);
        dialog = builder.create();
        dialog.show();

        RequestParams params = new RequestParams();
        params.put("planId", taskID);
        params.put("deviceCode", deviceCode);
        HttpClient.getInstance().requestInspectItem(this, this, params);
    }

    @OnClick({R.id.button_inspect_detail_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_inspect_detail_save: {
                for (InspectionItemAddReq item :
                        inspectionItemAddReqList) {
                    if(item.getRecordResult() == null) {
                        Toast.makeText(this, R.string.failure_save_inspect, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                LoadingDialog.Builder builder = new LoadingDialog.Builder(this)
                        .setMessage(R.string.loading_save);
                dialog = builder.create();
                dialog.show();

//                Log.e("InspectDetailActivity",JSON.toJSONString(inspectionItemAddReqList));
                InspectRecordSubmitReq req = new InspectRecordSubmitReq();
                req.setPlanId(taskID);
                req.setDeviceCode(deviceCode);
                if(inspectItemResp != null) {
                    req.setSpotInspectionStandard(inspectItemResp.getSpotInspectionStandard());
                    req.setDeviceName(inspectItemResp.getDeviceName());

                    req.setDetailList(inspectionItemAddReqList);
                }
                if(imageKey.size() > 0) {
                    req.setImageIdList(imageKey);
                }

                HttpClient.getInstance().requestInspectSubmit(this, this, req);
                break;
            }
        }

    }

    private void addView(final InspectItemResp item) {
        if(item.getInspectionStatus() != 2) {
            mCount++;
        }
        final InspectionItemAddReq req = new InspectionItemAddReq();
        req.setItemId(item.getItemId());
        inspectionItemAddReqList.add(req);

        if ("options".equalsIgnoreCase(item.getRecordTypeName()) || "status".equalsIgnoreCase(item.getRecordTypeName())) { //勾选
            View view = getLayoutInflater().inflate(R.layout.item_inspect_option_layout, null);
            TextView inspectItem = (TextView) view.findViewById(R.id.tv_option_desc);
            inspectItem.setText(item.getName());
            RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.option_group);
            for (String value :
                    item.getInputLimitValue()) {
                RadioButton button = new RadioButton(this);
                button.setText(value);
                if(item.getInspectionStatus() != 2) {
                    button.setEnabled(false);
                }
                radioGroup.addView(button);
            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    RadioButton button = (RadioButton) radioGroup.findViewById(i);
                    req.setRecordResult(button.getText().toString());
                }
            });
            llInspectDeviceItem.addView(view);
        } else if ("table".equalsIgnoreCase(item.getRecordTypeName())) { //列表
            View view = getLayoutInflater().inflate(R.layout.item_inspect_spinner_layout, null);
            TextView inspectItem = (TextView) view.findViewById(R.id.tv_spinner_desc);
            inspectItem.setText(item.getName());
            Spinner spinner = (Spinner) view.findViewById(R.id.spinner_option);
            if(item.getInspectionStatus() != 2) {
                spinner.setEnabled(false);
            }
            final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item.getInputLimitValue());
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    req.setRecordResult((String) adapter.getItem(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            llInspectDeviceItem.addView(view);
        } else if ("number".equalsIgnoreCase(item.getRecordTypeName())) { //数值
            View view = getLayoutInflater().inflate(R.layout.item_inspect_number_layout, null);
            TextView inspectItem = (TextView) view.findViewById(R.id.tv_number_desc);
            inspectItem.setText(item.getName());
            EditText editText = (EditText) view.findViewById(R.id.editText_number);
            editText.setHint("参考范围：" + item.getLowerLimit() + " - " + item.getUpperLimit());
            if(item.getInspectionStatus() != 2) {
                editText.setEnabled(false);
            }
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    req.setRecordResult(editable.toString());
                }
            });
            llInspectDeviceItem.addView(view);
        } else if ("verbal_description".equalsIgnoreCase(item.getRecordTypeName())) { //文字描述
            View view = getLayoutInflater().inflate(R.layout.item_inspect_text_layout, null);
            TextView inspectItem = (TextView) view.findViewById(R.id.tv_text_desc);
            inspectItem.setText(item.getName());
            EditText editText = (EditText) view.findViewById(R.id.editText_text);
            editText.setHint("文字描述");
            if(item.getInspectionStatus() != 2) {
                editText.setEnabled(false);
            }
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    req.setRecordResult(editable.toString());
                }
            });
            llInspectDeviceItem.addView(view);
        }

//        if("options".equalsIgnoreCase(item.getRecordTypeName()) || "table".equalsIgnoreCase(item.getRecordTypeName())) {
//            addExceptionSelect(item);
//            return;
//        }

        View view = getLayoutInflater().inflate(R.layout.item_dividing_line_layout, null);
        llInspectDeviceItem.addView(view);
    }

    private void addStatusSelect(InspectItemResp item) {
        InspectItemResp i = new InspectItemResp();
        i.setName(item.getName());
        i.setRecordTypeName("status");
        List<String> v = new ArrayList<>();
        v.add("正常");
        v.add("异常");
        i.setInputLimitValue(v);
        addView(i);
    }

    private void initUI() {
        mCount = 0;
        if(inspectItemResp == null) {
            return;
        }
        tvInspectDeviceName.setText(inspectItemResp.getDeviceName());
        int itemNumber = inspectItemResp.getItemList().size();

        for (InspectItemResp item :
                inspectItemResp.getItemList()) {
            addView(item);
        }

        if(mCount < itemNumber) {
            Toast.makeText(this, "总共有" + itemNumber + "项，已完成" + mCount + "项", Toast.LENGTH_SHORT).show();
        } else if(mCount == itemNumber) {
            buttonInspectDetailSave.setEnabled(false);
            Toast.makeText(this, "全部点检项目已完成，请勿重复提交", Toast.LENGTH_SHORT).show();
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
            default:
                break;
        }

        if(dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onFailure(int requestType) {
        if(dialog != null) {
            dialog.dismiss();
        }

        if(requestType == HttpClient.REQUEST_INSPECT_ITEM) {
            finish();
        }
    }

    @Override
    public void onImageUploadSuccess(String uri) {
        imageKey.add(uri);
    }
}
