package tech.yozo.factoryrp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;

public class EngineerSelectActivity extends AppCompatActivity {
    public static final String ENGINEER = "engineer";

    @BindView(R.id.lv_maintainer)
    ListView lvMaintainer;
    @BindView(R.id.button_add_maintainer)
    Button buttonAddMaintainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintainer_select);
        ButterKnife.bind(this);

        HttpClient client = HttpClient.getInstance();
        //TODO
    }

    @OnClick(R.id.button_add_maintainer)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.putExtra(ENGINEER, "");
        setResult(RESULT_OK, intent);
        finish();
    }
}
