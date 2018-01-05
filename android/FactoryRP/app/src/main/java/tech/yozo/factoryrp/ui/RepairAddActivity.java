package tech.yozo.factoryrp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.fragment.*;
import tech.yozo.factoryrp.utils.BottomNavigationViewHelper;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderDetailVo;

import java.util.List;

public class RepairAddActivity extends AppCompatActivity implements HttpClient.OnHttpListener {
    private ViewPager mViewPageContianer;
    private BottomNavigationView mNavigation;

    private WorkOrderDetailVo detailVo;

    private String[] fragments = new String[]{
            RepairAddFragment.class.getName(),
            RepairAdviceFragment.class.getName(),
            RepairWorkloadFragment.class.getName(),
            RepairPartsFragment.class.getName()
    };

    private int[] bottomNavigationItems = {
            R.id.navigation_repair_info,
            R.id.navigation_repair_advice,
            R.id.navigation_repair_workload,
            R.id.navigation_repair_parts
    };

    private int mCurrentFragment = 0;

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

            bundle.putInt("mode", Constant.FOR_REPAIR_ADD);
            return Fragment.instantiate(RepairAddActivity.this, fragments[position], bundle);
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
        setContentView(R.layout.activity_repair_detail);

        mNavigation = (BottomNavigationView) findViewById(R.id.navigation_repair);
        BottomNavigationViewHelper.disableShiftMode(mNavigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mViewPageContianer = (ViewPager) findViewById(R.id.container_repair);
        mViewPageContianer.setAdapter(mFragmentPagerAdapter);
        mViewPageContianer.setOnPageChangeListener(mOnPageChangeListener);
        mViewPageContianer.setCurrentItem(mCurrentFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repair_add_submit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                //TODO
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {

    }

    @Override
    public void onFailure(int requestType) {

    }
}
