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
import tech.yozo.factoryrp.ui.fragment.RepairAdviceFragment;
import tech.yozo.factoryrp.ui.fragment.RepairInfoFragment;
import tech.yozo.factoryrp.ui.fragment.RepairPartsFragment;
import tech.yozo.factoryrp.ui.fragment.RepairWorkloadFragment;
import tech.yozo.factoryrp.utils.BottomNavigationViewHelper;
import tech.yozo.factoryrp.utils.Constant;

public class RepairDetailActivity extends AppCompatActivity {
    public static final String TROUBLE_ID = "trouble_id";

    private ViewPager mViewPageContianer;
    private BottomNavigationView mNavigation;

    private String[] fragments = new String[]{
            RepairInfoFragment.class.getName(),
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

            Intent intent = getIntent();
            if(intent.hasExtra(TROUBLE_ID)) {
                bundle.putInt("param1", Constant.FOR_WORK_ORDER_ID);
                bundle.putString("param2", intent.getStringExtra(TROUBLE_ID));
            }
            return Fragment.instantiate(RepairDetailActivity.this, fragments[position], bundle);
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

}
