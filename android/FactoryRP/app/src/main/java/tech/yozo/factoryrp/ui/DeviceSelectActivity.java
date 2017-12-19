package tech.yozo.factoryrp.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.fragment.DeviceListFragment;
import tech.yozo.factoryrp.ui.fragment.RepairRecordListFragment;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.vo.resp.device.info.SimpleDeviceInfoResp;

public class DeviceSelectActivity extends AppCompatActivity implements DeviceListFragment.OnFragmentInteractionListener {
    public static final String DEVICE_OBJECT = "device_object";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("param1", Constant.FOR_CHOICE_MODE);
        Fragment fragment = Fragment.instantiate(this, DeviceListFragment.class.getName(), bundle);
        transaction.add(R.id.ll_list_fragment, fragment);
        transaction.commit();

        fragment.setUserVisibleHint(true);
    }

    @Override
    public void onDeviceSelected(SimpleDeviceInfoResp device) {
        Intent intent = new Intent();
        intent.putExtra(DEVICE_OBJECT, device);
        setResult(RESULT_OK, intent);
        finish();
    }
}
