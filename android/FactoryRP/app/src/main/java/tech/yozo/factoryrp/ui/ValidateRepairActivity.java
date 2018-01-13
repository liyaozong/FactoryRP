package tech.yozo.factoryrp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.utils.HttpClient;
import tech.yozo.factoryrp.vo.req.ValidateRepairReq;

import java.util.List;

public class ValidateRepairActivity extends AppCompatActivity implements HttpClient.OnHttpListener {

    @BindView(R.id.rg_repair_suecss)
    RadioGroup rgRepairSuecss;
    @BindView(R.id.checkBox_again)
    CheckBox checkBoxAgain;
    @BindView(R.id.editText_comment)
    EditText editTextComment;
    @BindView(R.id.comment_star)
    RatingBar commentStar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.validate_repair_dialog);
        ButterKnife.bind(this);
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        finish();
    }

    @Override
    public void onFailure(int requestType) {

    }

    @OnClick(R.id.submit_button)
    public void onViewClicked() {
        attemptSubmitValidate();
    }

    private void attemptSubmitValidate() {
        Long id = getIntent().getLongExtra("id", -1);
        if (id == -1) {
            Toast.makeText(this, "非法参数", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpClient client = HttpClient.getInstance();
        ValidateRepairReq req = new ValidateRepairReq();
        req.setTroubleRecordId(id);
        switch (rgRepairSuecss.getCheckedRadioButtonId()) {
            case R.id.radioButton_yes:
                req.setRepaired(1);
                break;
            case R.id.radioButton_no:
                req.setRepaired(0);
                break;
            default:
                break;
        }
        //TODO
//        req.setRepairAgain(checkBoxAgain.isChecked());
        req.setSuggest(editTextComment.getText().toString());
        req.setStarLevel((int) commentStar.getRating());
        client.requestValidateRepairTask(this, this, req);
    }
}