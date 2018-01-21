package tech.yozo.factoryrp.ui;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.fragment.RepairRecordListFragment;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.HttpClient;

public class RepairRecordListActivity extends AppCompatActivity {

    public static final String RECORD_CATEGORY = "record_category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_record_list);

        int index = getIntent().getIntExtra(RECORD_CATEGORY, 0);
        switch (index) {
            case HttpClient.REQUEST_TROUBLE_WAIT_AUDIT:
                setTitle(R.string.text_repair_wait_audit);
                break;
            case HttpClient.REQUEST_TROUBLE_WAIT_REPAIR:
                setTitle(R.string.text_repair_wait_exec);
                break;
            case HttpClient.REQUEST_TROUBLE_REPAIRING:
                setTitle(R.string.text_repair_executing);
                break;
            case HttpClient.REQUEST_TROUBLE_WAIT_VALIDATE:
                setTitle(R.string.text_repair_wait_verify);
                break;
            default:
                break;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("mode", index);
        Fragment fragment = Fragment.instantiate(this, RepairRecordListFragment.class.getName(), bundle);
        transaction.add(R.id.ll_list_fragment, fragment);
        transaction.commit();

        fragment.setUserVisibleHint(true);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
