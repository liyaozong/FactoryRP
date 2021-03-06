package tech.yozo.factoryrp.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.loopj.android.http.RequestParams;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.fragment.*;
import tech.yozo.factoryrp.utils.BottomNavigationViewHelper;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.req.SubmitRepairReq;
import tech.yozo.factoryrp.vo.resp.device.trouble.UsedSparePartsVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderDetailVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkTimeVo;

import java.util.List;

public class RepairDetailActivity extends AppCompatActivity implements HttpClient.OnHttpListener, RepairPartsFragment.OnFragmentInteractionListener, RepairWorkloadFragment.OnFragmentInteractionListener {
    public static final String TROUBLE_ID = "trouble_id";
    public static final String OPERATE_MODE = "operate_mode";

    private ViewPager mViewPageContianer;
    private BottomNavigationView mNavigation;

    private WorkOrderDetailVo detailVo;
    private List<UsedSparePartsVo> modifyParts;
    private List<WorkTimeVo> modifyWorkTimes;
    private int mode;
    private long id;

    private boolean cancelForParts = true;
    private boolean cancelForTimes = true;

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

            bundle.putInt("mode", mode);
            bundle.putSerializable("obj", detailVo);
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

        Intent intent = getIntent();
        mode = intent.getIntExtra(OPERATE_MODE, -1);
        id = intent.getLongExtra(TROUBLE_ID, -1);
        if(mode == -1 || id == -1) {
            Toast.makeText(this, "非法参数", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        HttpClient client = HttpClient.getInstance();
        RequestParams params = new RequestParams();
        params.put("id", id);
        client.requestRepairDetail(this, this, params);

        mNavigation = (BottomNavigationView) findViewById(R.id.navigation_repair);
        BottomNavigationViewHelper.disableShiftMode(mNavigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mode == HttpClient.REQUEST_TROUBLE_REPAIRING) {
            getMenuInflater().inflate(R.menu.repair_bar_submit, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                if(modifyParts != null && modifyParts.size() != 0) {
                    cancelForParts = false;
                    attemptSubmitRepair();
                } else {
                    cancelForParts = true;
                    partsAlertDialog();
                }
                if(modifyWorkTimes != null && modifyWorkTimes.size() != 0) {
                    cancelForTimes = false;
                    attemptSubmitRepair();
                } else {
                    cancelForTimes = true;
                    timesAlertDialog();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void partsAlertDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.hint);
        alertDialogBuilder.setMessage(R.string.submit_hint_1);
        alertDialogBuilder.setPositiveButton(R.string.submit_positive_1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancelForParts = false;
                attemptSubmitRepair();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.submit_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancelForParts = true;
            }
        });

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    private void timesAlertDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.hint);
        alertDialogBuilder.setMessage(R.string.submit_hint_2);
        alertDialogBuilder.setPositiveButton(R.string.submit_positive_2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancelForTimes = false;
                attemptSubmitRepair();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.submit_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cancelForTimes = true;
            }
        });

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    private void attemptSubmitRepair() {
        if(!cancelForParts && !cancelForTimes) {
            HttpClient client = HttpClient.getInstance();
            SubmitRepairReq req = new SubmitRepairReq();
            req.setTroubleRecordId(detailVo.getTroubleRecordId());
            req.setReplaceSpares(modifyParts);
            req.setWorkTimes(modifyWorkTimes);
            client.requestSubmitRepairTask(this, this, req);
        }
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        switch (requestType) {
            case HttpClient.REQUEST_REPAIR_DETAIL_URL:
                detailVo = (WorkOrderDetailVo) obj;
                mViewPageContianer = (ViewPager) findViewById(R.id.container_repair);
                mViewPageContianer.setAdapter(mFragmentPagerAdapter);
                mViewPageContianer.setOnPageChangeListener(mOnPageChangeListener);
                mViewPageContianer.setCurrentItem(mCurrentFragment);
                break;
            case HttpClient.REQUEST_SUBMIT_REPAIR_ACTION:
                Intent intent = new Intent();
                intent.putExtra("id", id);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public void onFailure(int requestType) {

    }

    @Override
    public void onModifyParts(List<UsedSparePartsVo> parts) {
        modifyParts = parts;
    }

    @Override
    public void onModifyWorkTimes(List<WorkTimeVo> workTimes) {
        modifyWorkTimes = workTimes;
    }
}
