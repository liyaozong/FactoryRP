package tech.yozo.factoryrp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.fragment.PartsListFragment;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;

public class PartsSelectActivity extends AppCompatActivity implements PartsListFragment.OnFragmentInteractionListener {
    public static final String PARTS_OBJECT = "parts_object";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("mode", Constant.FOR_CHOICE_MODE);
        bundle.putLong("id", getIntent().getLongExtra("id", -1));
        Fragment fragment = Fragment.instantiate(this, PartsListFragment.class.getName(), bundle);
        transaction.add(R.id.ll_list_fragment, fragment);
        transaction.commit();

        fragment.setUserVisibleHint(true);
    }

    @Override
    public void onPartSelected(SparePartsResp parts) {
        Intent intent = new Intent();
        intent.putExtra(PARTS_OBJECT, parts);
        setResult(RESULT_OK, intent);
        finish();
    }
}
