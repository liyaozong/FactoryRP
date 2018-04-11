package tech.yozo.factoryrp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.fastjson.JSONArray;
import com.loopj.android.http.RequestParams;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.entity.MaintenanceEngineer;
import tech.yozo.factoryrp.entity.RepairGroup;
import tech.yozo.factoryrp.utils.Constant;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.req.AssignTroubleReq;
import tech.yozo.factoryrp.vo.resp.DeviceParamDicEnumResp;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderDetailVo;

import java.text.SimpleDateFormat;
import java.util.List;

public class RepairDetailSimpleActivity extends AppCompatActivity implements HttpClient.OnHttpListener {
    public static final String TROUBLE_ID = "trouble_id";
    public static final String OPERATE_MODE = "operate_mode";
    private static final int SELECT_ENGINEER = 100;
    private static final int EXEC_AUDIT = 101;

    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_device_code)
    TextView tvDeviceCode;
    @BindView(R.id.tv_device_type)
    TextView tvDeviceType;
    @BindView(R.id.tv_device_dept)
    TextView tvDeviceDept;
    @BindView(R.id.tv_happen_time)
    TextView tvHappenTime;
    @BindView(R.id.tv_trouble_reason)
    TextView tvTroubleReason;
    @BindView(R.id.tv_trouble_type)
    TextView tvTroubleType;
    @BindView(R.id.tv_trouble_desc)
    TextView tvTroubleDesc;
    @BindView(R.id.tv_device_running_status)
    TextView tvDeviceRunningStatus;
    @BindView(R.id.tv_device_place)
    TextView tvDevicePlace;
    @BindView(R.id.tv_repair_group)
    TextView tvRepairGroup;
    @BindView(R.id.tv_device_operator)
    TextView tvDeviceOperator;
    @BindView(R.id.tv_operator_phone)
    TextView tvOperatorPhone;
    @BindView(R.id.tv_trouble_submitter)
    TextView tvTroubleSubmitter;
    @BindView(R.id.tv_maintainer)
    TextView tvMaintainer;
    @BindView(R.id.button_audit)
    Button buttonAudit;
    @BindView(R.id.button_assign)
    Button buttonAssign;

    private int mode;
    private long id;
    private WorkOrderDetailVo detailVo;
    private MaintenanceEngineer assignEngineer;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时MM分");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_detail_simple);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mode = intent.getIntExtra(OPERATE_MODE, -1);
        id = intent.getLongExtra(TROUBLE_ID, -1);
        if (mode == -1 || id == -1) {
            Toast.makeText(this, "非法参数", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if(mode == HttpClient.REQUEST_TROUBLE_WAIT_AUDIT) {
            buttonAssign.setVisibility(View.GONE);
        } else if(mode == HttpClient.REQUEST_TROUBLE_WAIT_ASSIGN) {
            buttonAudit.setVisibility(View.GONE);
        }

        HttpClient client = HttpClient.getInstance();
        RequestParams params = new RequestParams();
        params.put("id", id);
        client.requestRepairDetail(this, this, params);
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        switch (requestType) {
            case HttpClient.REQUEST_REPAIR_DETAIL_URL:
                detailVo = (WorkOrderDetailVo) obj;
                tvDeviceName.setText(detailVo.getDeviceName());
                tvDeviceCode.setText(detailVo.getDeviceCode());
                tvDeviceType.setText(detailVo.getSpecification());
                tvDeviceDept.setText(detailVo.getUseDept());
                tvDevicePlace.setText(detailVo.getInstallationAddress());
                if (HttpClient.getInstance().getDictEnum(Constant.DICT_TROUBLE_REASON) == null) {
                    HttpClient.getInstance().requestDeviceDict(this, this, Constant.DICT_TROUBLE_REASON);
                } else {
                    updateUI(HttpClient.REQUEST_DATA_DICT);
                }
                if (HttpClient.getInstance().getRepairGroupList() == null) {
                    HttpClient.getInstance().requestRepairGroup(this, this);
                } else {
                    updateUI(HttpClient.REQUEST_REPAIR_GROUP_URL);
                }
                tvTroubleType.setText(detailVo.getTroubleType());
                //TODO
                tvDeviceRunningStatus.setText("");
                tvHappenTime.setText(sdf.format(detailVo.getHappenTime()));
                tvDeviceOperator.setText(detailVo.getDeviceUser());
                tvOperatorPhone.setText(detailVo.getPhone());
                tvTroubleSubmitter.setText(detailVo.getCreateUser());
                tvMaintainer.setText(detailVo.getRepairUserName());
                tvTroubleDesc.setText(detailVo.getRemark());
                break;
            default:
                updateUI(requestType);
                break;
        }
    }

    @Override
    public void onFailure(int requestType) {

    }

    private void updateUI(int requestType) {
        HttpClient client = HttpClient.getInstance();
        switch (requestType) {
            case HttpClient.REQUEST_DATA_DICT:
                List<DeviceParamDicEnumResp> troubleReason = client.getDictEnum(Constant.DICT_TROUBLE_REASON);
                if (troubleReason != null) {
                    for (DeviceParamDicEnumResp item :
                            troubleReason) {
                        if (item.getId().equals(detailVo.getTroubleReason())) {
                            tvTroubleReason.setText(item.getName());
                        }
                    }
                }
                break;
            case HttpClient.REQUEST_REPAIR_GROUP_URL:
                List<RepairGroup> repairGroupList = client.getRepairGroupList();
                if (repairGroupList != null) {
                    for (RepairGroup group :
                            repairGroupList) {
                        if (group.getId().equals(detailVo.getRepairGroupId())) {
                            tvRepairGroup.setText(group.getName());
                        }
                    }
                }
                break;
            case HttpClient.REQUEST_TROUBLE_EXEC_ASSIGN:
                tvMaintainer.setText(assignEngineer.getUserName());
                break;
            case HttpClient.REQUEST_TROUBLE_EXEC_AUDIT:
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.button_audit, R.id.button_assign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_audit: {
                Intent intent = new Intent(this, TroubleAuditActivity.class);
                intent.putExtra("id", detailVo.getTroubleRecordId());
                startActivityForResult(intent, EXEC_AUDIT);
                break;
            }
            case R.id.button_assign: {
                Intent intent = new Intent(this, EngineerSelectActivity.class);
                intent.putExtra(EngineerSelectActivity.SELECT_MODE, 1);
                startActivityForResult(intent, SELECT_ENGINEER);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_ENGINEER:
                    List<MaintenanceEngineer> engineers = JSONArray.parseArray(data.getStringExtra(EngineerSelectActivity.ENGINEER), MaintenanceEngineer.class);
                    if(engineers.size() > 0) {
                        assignEngineer = engineers.get(0);
                        AssignTroubleReq req = new AssignTroubleReq();
                        req.setRepairUserId(assignEngineer.getUserId());
                        req.setRepairUserName(assignEngineer.getUserName());
                        req.setTroubleRecordId(detailVo.getTroubleRecordId());
                        HttpClient.getInstance().requestExecAssign(this, this, req);
                    }
                    break;
                case EXEC_AUDIT:
                    Intent intent = new Intent();
                    intent.putExtra("id", id);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    }
}
