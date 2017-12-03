package tech.yozo.factoryrp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.scan.CaptureActivity;
import tech.yozo.factoryrp.scan.Intents;
import tech.yozo.factoryrp.ui.fragment.DeviceListFragment;
import tech.yozo.factoryrp.ui.fragment.PartsListFragment;
import tech.yozo.factoryrp.ui.fragment.PersonFragment;
import tech.yozo.factoryrp.ui.fragment.WorkBenchFragment;
import tech.yozo.factoryrp.utils.BottomNavigationViewHelper;


public class MainActivity extends AppCompatActivity {
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
            return Fragment.instantiate(MainActivity.this, fragments[position]);
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
        mNavigation = (BottomNavigationView) findViewById(R.id.navigation_main);
        BottomNavigationViewHelper.disableShiftMode(mNavigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPageContianer = (ViewPager) findViewById(R.id.container_main);
        mViewPageContianer.setAdapter(mFragmentPagerAdapter);
        mViewPageContianer.setOnPageChangeListener(mOnPageChangeListener);
        mViewPageContianer.setCurrentItem(mCurrentFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scan_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan_code:
                startActivityForResult(new Intent(this, CaptureActivity.class), SCAN_CODE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SCAN_CODE:
                    Intent intent = new Intent(this, DeviceDetailActivity.class);
                    //TODO
                    //intent.putExtra(DeviceDetailActivity.DEVICE_CODE, data.getStringExtra(Intents.Scan.RESULT));
                    startActivity(intent);
            }
        }
    }

    public void onButtonPressed(View view) {
        switch(view.getId()){
            case R.id.button_add_device: {
                Intent intent = new Intent(this, DeviceAddActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button_add_repair:
            case R.id.button_ask_parts:
                break;
            case R.id.button_ask_repair: {
                Intent intent = new Intent(this, ReportFaultActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.textView_waitto_audit:
            case R.id.textView_waitto_exec:
            case R.id.textView_executing:
            case R.id.textView_waitto_verify: {
                Intent intent = new Intent(this, RepairRecordListActivity.class);
                intent.putExtra(RepairRecordListActivity.RECORD_CATEGORY, view.getId());
                startActivity(intent);
                break;
            }
            default:
                break;
        }

    }
}
