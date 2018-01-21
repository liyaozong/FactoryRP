package tech.yozo.factoryrp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.entity.MaintenanceEngineer;
import tech.yozo.factoryrp.utils.HttpClient;

import java.util.ArrayList;
import java.util.List;

public class EngineerSelectActivity extends AppCompatActivity implements HttpClient.OnHttpListener {
    public static final String ENGINEER = "engineer";

    @BindView(R.id.lv_maintainer)
    ListView lvMaintainer;
    @BindView(R.id.button_selected)
    Button buttonSelected;

    private List<MaintenanceEngineer> engineerList;
    private List<MaintenanceEngineer> checkBoxSelected = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintainer_select);
        ButterKnife.bind(this);

        HttpClient client = HttpClient.getInstance();
        RequestParams params = new RequestParams();
        params.put("roleCode", "MAINTENANCE_PERSONNEL");
        client.requestMemberByRole(this, this, params);
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        if (requestType == HttpClient.REQUEST_MEMBER_LIST_BY_ROLE) {
            engineerList = (List<MaintenanceEngineer>) list;
            lvMaintainer.setAdapter(new EngineerListAdapter(this));
            lvMaintainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    LinearLayout view1 = (LinearLayout) adapterView.getChildAt(i);
                    CheckBox box = (CheckBox) view1.getChildAt(2);
                    box.setChecked(!box.isChecked());
                }
            });
        }
    }

    @Override
    public void onFailure(int requestType) {

    }

    @OnClick(R.id.button_selected)
    public void onViewClicked() {
        if(checkBoxSelected.size() > 0) {
            Intent intent = new Intent();
            intent.putExtra(ENGINEER, JSON.toJSONString(checkBoxSelected));
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    class ViewHolder {
        TextView number;
        TextView name;
        CheckBox isSelected;
    }

    private class EngineerListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        private EngineerListAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (engineerList != null) {
                return engineerList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_engineer_select, null);
                holder.number = (TextView) convertView.findViewById(R.id.textView_number);
                holder.name = (TextView) convertView.findViewById(R.id.textView_name);
                holder.isSelected = (CheckBox) convertView.findViewById(R.id.checkBox_isSelected);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.number.setText(String.valueOf(i + 1));
            holder.name.setText(engineerList.get(i).getUserName());
            holder.isSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        checkBoxSelected.add(engineerList.get(i));
                    } else {
                        checkBoxSelected.remove(engineerList.get(i));
                    }
                }
            });

            return convertView;
        }
    }
}
