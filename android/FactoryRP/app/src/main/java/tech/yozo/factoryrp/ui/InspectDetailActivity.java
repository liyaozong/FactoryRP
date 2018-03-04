package tech.yozo.factoryrp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.loopj.android.http.RequestParams;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.inspect.InspectDeviceDetailResp;
import tech.yozo.factoryrp.vo.resp.inspect.InspectItemResp;

import java.util.*;

public class InspectDetailActivity extends AppCompatActivity implements HttpClient.OnHttpListener {

    @BindView(R.id.tv_inspect_device_name)
    TextView tvInspectDeviceName;
    @BindView(R.id.ll_inspect_device_item)
    LinearLayout llInspectDeviceItem;
    @BindView(R.id.button_inspect_detail_save)
    Button buttonInspectDetailSave;

    private InspectDeviceDetailResp inspectItemResp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_detail);
        ButterKnife.bind(this);

        Long taskID = getIntent().getLongExtra("TASK_ID", 0);
        String deviceCode = getIntent().getStringExtra("DEVICE_CODE");
        RequestParams params = new RequestParams();
        params.put("planId", taskID);
        params.put("deviceCode", deviceCode);
        HttpClient.getInstance().requestInspectItem(this, this, params);
    }

    @OnClick(R.id.button_inspect_detail_save)
    public void onViewClicked() {
        finish();
    }

    private void addView(InspectItemResp item) {
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

        if("options".equalsIgnoreCase(item.getRecordTypeName()) || "exception".equalsIgnoreCase(item.getRecordTypeName())) { //勾选
            RadioGroup radioGroup = new RadioGroup(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            radioGroup.setLayoutParams(lp);
            radioGroup.setOrientation(LinearLayout.HORIZONTAL);
            for (String value :
                    item.getInputLimitValue()) {
                RadioButton yesButton = new RadioButton(this);
                yesButton.setText(value);
                if(item.getInspectionStatus() != null && item.getInspectionStatus() == 1) {
                    yesButton.setEnabled(false);
                }
                radioGroup.addView(yesButton);
            }
            lineLayout.addView(radioGroup);
        } else if("table".equalsIgnoreCase(item.getRecordTypeName())) { //列表
            Spinner spinner = new Spinner(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            spinner.setLayoutParams(lp);
            if(item.getInspectionStatus() != null && item.getInspectionStatus() == 1) {
                spinner.setEnabled(false);
            }
            spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item.getInputLimitValue()));
            lineLayout.addView(spinner);
        } else if("number".equalsIgnoreCase(item.getRecordTypeName())) { //数值
            EditText editText = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            editText.setLayoutParams(lp);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setHint("参考范围：" + item.getLowerLimit() + " - " + item.getUpperLimit());
            if(item.getInspectionStatus() != null && item.getInspectionStatus() == 1) {
                editText.setEnabled(false);
            }
            lineLayout.addView(editText);
        } else if("verbal_description".equalsIgnoreCase(item.getRecordTypeName())) { //文字描述
            EditText editText = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            editText.setLayoutParams(lp);
            editText.setMinLines(3);
            editText.setHint("文字描述");
            if(item.getInspectionStatus() != null && item.getInspectionStatus() == 1) {
                editText.setEnabled(false);
            }
            lineLayout.addView(editText);
        }

        llInspectDeviceItem.addView(lineLayout);

        if("options".equalsIgnoreCase(item.getRecordTypeName()) || "table".equalsIgnoreCase(item.getRecordTypeName())) {
            addExceptionSelect(item);
            return;
        }

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
        if(requestType == HttpClient.REQUEST_INSPECT_ITEM) {
            inspectItemResp = (InspectDeviceDetailResp) obj;
            initUI();
        }
    }

    @Override
    public void onFailure(int requestType) {

    }
}
