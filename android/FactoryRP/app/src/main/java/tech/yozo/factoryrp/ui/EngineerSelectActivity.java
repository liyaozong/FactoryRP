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
import tech.yozo.factoryrp.entity.MaintenanceEngineer;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EngineerSelectActivity extends AppCompatActivity {
    public static final String ENGINEER = "engineer";

    @BindView(R.id.lv_maintainer)
    ListView lvMaintainer;

    List<Map<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintainer_select);
        ButterKnife.bind(this);

        HttpClient client = HttpClient.getInstance();
        //TODO

        lvMaintainer.setAdapter(new SimpleAdapter(this, getData(),
                R.layout.item_info_list, new String[] { "title", "info" },
                new int[] { R.id.tv_name, R.id.tv_value }));
        lvMaintainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent();
                MaintenanceEngineer engineer = new MaintenanceEngineer();
                engineer.setId((Long) list.get(position).get("title"));
                engineer.setName(list.get(position).get("info").toString());
                intent.putExtra(ENGINEER, engineer);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private List<Map<String, Object>> getData() {
        list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("title", 1L);
        map.put("info", "电台DJ");
        list.add(map);

        map = new HashMap<>();
        map.put("title", 2L);
        map.put("info", "四大美女");
        list.add(map);

        map = new HashMap<>();
        map.put("title", 3L);
        map.put("info", "清纯妹妹");
        list.add(map);

        map = new HashMap<>();
        map.put("title", 4L);
        map.put("info", "小狗");
        list.add(map);

        map = new HashMap<>();
        map.put("title", 5L);
        map.put("info", "进修工程师");
        list.add(map);

        map = new HashMap<>();
        map.put("title", 6L);
        map.put("info", "阿卡德工程师");
        list.add(map);

        return list;
    }
}
