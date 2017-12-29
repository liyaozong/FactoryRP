package tech.yozo.factoryrp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartsDetailActivity extends AppCompatActivity {
    public static final String PARTS_ID = "PARTS_ID";

    private ListView mPartsInfoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts_detail);

        SparePartsResp part = (SparePartsResp) getIntent().getSerializableExtra(PARTS_ID);
        SimpleAdapter adapter = new SimpleAdapter(this, getData(part), R.layout.item_info_list,
                new String[]{"name","value"},
                new int[]{R.id.tv_name,R.id.tv_value});
        mPartsInfoListView = (ListView) findViewById(R.id.lv_parts_info);
        mPartsInfoListView.setAdapter(adapter);
    }

    private List<Map<String, Object>> getData(SparePartsResp part) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "备件名称");
        map.put("value", part.getName());
        list.add(map);

        map = new HashMap<>();
        map.put("name", "备件编号");
        map.put("value", part.getCode());
        list.add(map);

        map = new HashMap<>();
        map.put("name", "设备类型");
        map.put("value", part.getSparePartType());
        list.add(map);

        map = new HashMap<>();
        map.put("name", "规格型号");
        map.put("value", part.getSpecificationsAndodels());
        list.add(map);

        map = new HashMap<>();
        map.put("name", "生产厂商");
        map.put("value", part.getManufacturer());
        list.add(map);

        map = new HashMap<>();
        map.put("name", "参考价格");
        map.put("value", part.getReferencePrice());
        list.add(map);

        map = new HashMap<>();
        map.put("name", "供应商");
        map.put("value", part.getSuppliers());
        list.add(map);

        return list;
    }
}
