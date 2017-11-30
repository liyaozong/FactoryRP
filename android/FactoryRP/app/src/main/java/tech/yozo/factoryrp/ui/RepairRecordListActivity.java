package tech.yozo.factoryrp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import tech.yozo.factoryrp.R;

public class RepairRecordListActivity extends AppCompatActivity {

    public static final String RECORD_CATEGORY = "record_category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_record_list);

        Intent intent = getIntent();
        switch (intent.getIntExtra(RECORD_CATEGORY, 0)) {
            case R.id.textView_waitto_audit:
                setTitle(R.string.text_repair_wait_audit);
                break;
            case R.id.textView_waitto_exec:
                setTitle(R.string.text_repair_wait_exec);
                break;
            case R.id.textView_executing:
                setTitle(R.string.text_repair_executing);
                break;
            case R.id.textView_waitto_verify:
                setTitle(R.string.text_repair_wait_verify);
                break;
            default:
                break;
        }
    }
}
