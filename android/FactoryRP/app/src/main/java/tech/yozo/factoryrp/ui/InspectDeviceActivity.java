package tech.yozo.factoryrp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.scan.ScanActivity;

import java.util.*;

public class InspectDeviceActivity extends AppCompatActivity {
    private final static int SCAN_CODE = 1;
    private static final int UPDATE_INSPECT_TASK = 111;

    @BindView(R.id.lv_inspect_devices)
    ListView lvInspectDevices;
    @BindView(R.id.button_scan)
    Button buttonScan;

    private List<Map<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_device);
        ButterKnife.bind(this);

        String[] from = {"title"};
        int[] to = {R.id.tv_inspect_device_name};
        list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", "设备：" + i);
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.item_inspect_devices, from, to);
        lvInspectDevices.setAdapter(adapter);

        lvInspectDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), R.string.failure_click, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.button_scan)
    public void onViewClicked() {
        startActivityForResult(new Intent(this, ScanActivity.class), SCAN_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SCAN_CODE:
                    //TODO
                    Intent intent = new Intent(InspectDeviceActivity.this, InspectDetailActivity.class);
                    startActivityForResult(intent, UPDATE_INSPECT_TASK);
                    break;
                default:
                    break;
            }
        }
    }
}
