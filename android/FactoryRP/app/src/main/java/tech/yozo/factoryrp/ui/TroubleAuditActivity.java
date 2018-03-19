package tech.yozo.factoryrp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.req.AuditTroubleReq;
import tech.yozo.factoryrp.vo.req.ValidateRepairReq;

import java.util.List;

public class TroubleAuditActivity extends AppCompatActivity implements HttpClient.OnHttpListener {

    @BindView(R.id.rg_audit_suecss)
    RadioGroup rgAuditSuecss;
    @BindView(R.id.editText_comment)
    EditText editTextComment;
    @BindView(R.id.button_audit_save)
    Button buttonAuditSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble_audit);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_audit_save)
    public void onViewClicked() {
        attemptSubmitAudit();
    }

    private void attemptSubmitAudit() {
        Long id = getIntent().getLongExtra("id", -1);
        if (id == -1) {
            Toast.makeText(this, "非法参数", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpClient client = HttpClient.getInstance();
        AuditTroubleReq req = new AuditTroubleReq();
        req.setTroubleRecordId(id);
        switch (rgAuditSuecss.getCheckedRadioButtonId()) {
            case R.id.radioButton_yes:
                req.setDealStatus(1);
                break;
            case R.id.radioButton_no:
                req.setDealStatus(2);
                break;
            default:
                break;
        }

        req.setDealSuggest(editTextComment.getText().toString());
        req.setTroubleRecordId(id);
        client.requestExecAudit(this, this, req);
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onFailure(int requestType) {

    }
}
