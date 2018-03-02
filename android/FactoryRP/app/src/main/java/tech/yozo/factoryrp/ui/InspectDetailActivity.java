package tech.yozo.factoryrp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.yozo.factoryrp.R;

public class InspectDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_inspect_device_name)
    TextView tvInspectDeviceName;
    @BindView(R.id.ll_inspect_device_item)
    LinearLayout llInspectDeviceItem;
    @BindView(R.id.button_inspect_detail_save)
    Button buttonInspectDetailSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_detail);
        ButterKnife.bind(this);

        tvInspectDeviceName.setText("高温压缩机");

        for(int i = 0; i < 6; i++) {
            addView(i);
        }
    }

    @OnClick(R.id.button_inspect_detail_save)
    public void onViewClicked() {
        finish();
    }

    private void addView(int i) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout lineLayout = new LinearLayout(this);
        lineLayout.setOrientation(LinearLayout.HORIZONTAL);
        lineLayout.setLayoutParams(params);
        lineLayout.setGravity(Gravity.CENTER_VERTICAL);

        TextView inpsectItem = new TextView(this);
        inpsectItem.setWidth(500);
        inpsectItem.setText("行车起落过程中，有无站立不正确"+i);
        lineLayout.addView(inpsectItem);

        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        RadioButton yesButton = new RadioButton(this);
        yesButton.setText("是");
        radioGroup.addView(yesButton);
        RadioButton noButton = new RadioButton(this);
        noButton.setText("否");
        radioGroup.addView(noButton);
        lineLayout.addView(radioGroup);

        llInspectDeviceItem.addView(lineLayout);
    }
}
