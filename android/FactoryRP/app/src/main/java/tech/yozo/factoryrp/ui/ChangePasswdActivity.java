package tech.yozo.factoryrp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.loopj.android.http.RequestParams;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.utils.HttpClient;

import java.util.List;

public class ChangePasswdActivity extends AppCompatActivity implements HttpClient.OnHttpListener {

    @BindView(R.id.editText_old)
    EditText editTextOld;
    @BindView(R.id.editText_new)
    EditText editTextNew;
    @BindView(R.id.editText_again)
    EditText editTextAgain;
    @BindView(R.id.button_change)
    Button buttonChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwd);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_change)
    public void onClick() {
        String oldPass = editTextOld.getText().toString();
        String newPass = editTextNew.getText().toString();
        String againPass = editTextAgain.getText().toString();
        if(newPass.contentEquals(againPass)) {
            RequestParams params = new RequestParams();
            params.put("newPassword", newPass);
            params.put("oldPassword", oldPass);
            HttpClient.getInstance().requestResetPasswd(this, this, params);
        } else {
            editTextAgain.setError("两次输入不一致，请重输");
        }
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        finish();
    }

    @Override
    public void onFailure(int requestType) {

    }
}
