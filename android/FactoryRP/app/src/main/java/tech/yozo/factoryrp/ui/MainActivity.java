package tech.yozo.factoryrp.ui;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;
import com.loopj.android.http.RequestParams;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.scan.Intents;
import tech.yozo.factoryrp.scan.ScanActivity;
import tech.yozo.factoryrp.ui.dialog.ShowImageConfig;
import tech.yozo.factoryrp.ui.fragment.DeviceListFragment;
import tech.yozo.factoryrp.ui.fragment.PartsListFragment;
import tech.yozo.factoryrp.ui.fragment.PersonFragment;
import tech.yozo.factoryrp.ui.fragment.WorkBenchFragment;
import tech.yozo.factoryrp.utils.BottomNavigationViewHelper;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;

import java.util.List;


public class MainActivity extends AppCompatActivity implements HttpClient.OnHttpListener {
    private final static int SCAN_CODE = 1;

    private ViewPager mViewPageContianer;
    private BottomNavigationView mNavigation;

    private String[] fragments = new String[]{
            WorkBenchFragment.class.getName(),
            DeviceListFragment.class.getName(),
            PartsListFragment.class.getName(),
            PersonFragment.class.getName()
    };
    private int[] bottomNavigationItems = {
            R.id.navigation_home,
            R.id.navigation_device,
            R.id.navigation_parts,
            R.id.navigation_person
    };

    private int mCurrentFragment = 0;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            for (int i = 0; i < bottomNavigationItems.length; i++) {
                if (bottomNavigationItems[i] == item.getItemId()) {
                    mCurrentFragment = i;
                    mViewPageContianer.setCurrentItem(mCurrentFragment);
                    return true;
                }
            }
            return false;
        }

    };

    private FragmentPagerAdapter mFragmentPagerAdapter
            = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("mode", Constant.FOR_BROWE_MODE);
            return Fragment.instantiate(MainActivity.this, fragments[position], bundle);
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mCurrentFragment = position;
            mNavigation.setSelectedItemId(bottomNavigationItems[mCurrentFragment]);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDeviceDensity();
        mNavigation = (BottomNavigationView) findViewById(R.id.navigation_main);
        BottomNavigationViewHelper.disableShiftMode(mNavigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPageContianer = (ViewPager) findViewById(R.id.container_main);
        mViewPageContianer.setAdapter(mFragmentPagerAdapter);
        mViewPageContianer.setOnPageChangeListener(mOnPageChangeListener);
        mViewPageContianer.setCurrentItem(mCurrentFragment);

//        SharedPreferences sharedPreferences = getSharedPreferences("private_data", MODE_PRIVATE);
//        if("false".equals(sharedPreferences.getString("isSynced","false"))) {
//            HttpClient client = HttpClient.getInstance();
//            client.syncData(this, this);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.device_bar_scan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan_device_code:
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CAMERA}, Constant.REQUEST_SCAN_PERMISSION);
                }else {
//                    startActivityForResult(new Intent(this, CaptureActivity.class), SCAN_CODE);
                    startActivityForResult(new Intent(this, ScanActivity.class), SCAN_CODE);
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("确认退出吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SCAN_CODE:
                    boolean needNet = true;
                    String code = data.getStringExtra(Intents.Scan.RESULT);
                    HttpClient client = HttpClient.getInstance();
                    List<FullDeviceInfoResp> list = client.getFullDeviceInfoList();
                    for(FullDeviceInfoResp device : list) {
                        if (code.contentEquals(device.getCode())) {
                            Intent intent = new Intent(this, DeviceDetailActivity.class);
                            intent.putExtra(DeviceDetailActivity.DEVICE_ID, device.getId());
                            startActivity(intent);
                            needNet = false;
                            break;
                        }
                    }
                    if(needNet) {
                        RequestParams params = new RequestParams();
                        params.put("code", code);
                        client.requestDeviceByCode(this, this, params);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constant.REQUEST_SCAN_PERMISSION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startActivityForResult(new Intent(this, CaptureActivity.class), SCAN_CODE);
                startActivityForResult(new Intent(this, ScanActivity.class), SCAN_CODE);
            } else {
                Toast.makeText(this, R.string.failure_camera_permission, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        if (requestType == HttpClient.REQUEST_DEVICE_GET_BY_CODE) {
            FullDeviceInfoResp device = (FullDeviceInfoResp) obj;
            Intent intent = new Intent(this, DeviceDetailActivity.class);
            intent.putExtra(DeviceDetailActivity.DEVICE_ID, device.getId());
            startActivity(intent);
        }

    }

    @Override
    public void onFailure(int requestType) {

    }


    /**
     * 获取当前设备的屏幕密度等基本参数
     */
    protected void getDeviceDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ShowImageConfig.EXACT_SCREEN_HEIGHT = metrics.heightPixels;
        ShowImageConfig.EXACT_SCREEN_WIDTH = metrics.widthPixels;
    }
}
