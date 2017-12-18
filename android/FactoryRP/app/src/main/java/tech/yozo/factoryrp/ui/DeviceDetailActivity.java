package tech.yozo.factoryrp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.fragment.*;
import tech.yozo.factoryrp.utils.BottomNavigationViewHelper;
import tech.yozo.factoryrp.utils.Constant;

public class DeviceDetailActivity extends AppCompatActivity {
    public static final String DEVICE_ID = "device_id";
    public static final String DEVICE_CODE = "device_code";

    private ViewPager mViewPageContianer;
    private BottomNavigationView mNavigation;

    private String[] fragments = new String[]{
            DeviceInfoFragment.class.getName(),
            PartsListFragment.class.getName(),
            RepairRecordListFragment.class.getName(),
            MaintainRecordListFragment.class.getName(),
            CheckRecordListFragment.class.getName()
    };
    private int[] bottomNavigationItems = {
            R.id.navigation_device_info,
            R.id.navigation_device_parts_list,
            R.id.navigation_repair_record_list,
            R.id.navigation_maintain_record_list,
            R.id.navigation_check_record_list
    };

    private int mCurrentFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);

        mNavigation = (BottomNavigationView) findViewById(R.id.navigation_device);
        BottomNavigationViewHelper.disableShiftMode(mNavigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPageContianer = (ViewPager) findViewById(R.id.container_device);
        mViewPageContianer.setAdapter(mFragmentPagerAdapter);
        mViewPageContianer.setOnPageChangeListener(mOnPageChangeListener);
        mViewPageContianer.setCurrentItem(mCurrentFragment);
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

            Intent intent = getIntent();
            if(intent.hasExtra(DEVICE_ID)) {
                bundle.putInt("param1", Constant.FOR_DEVICE_ID);
                bundle.putString("param2", intent.getStringExtra(DEVICE_ID));
            } else if(intent.hasExtra(DEVICE_CODE)) {
                bundle.putInt("param1", Constant.FOR_DEVICE_CODE);
                bundle.putString("param2", intent.getStringExtra(DEVICE_CODE));
            }

            return Fragment.instantiate(DeviceDetailActivity.this, fragments[position], bundle);
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
}
