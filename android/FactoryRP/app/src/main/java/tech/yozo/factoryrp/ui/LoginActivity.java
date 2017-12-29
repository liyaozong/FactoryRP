package tech.yozo.factoryrp.ui;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.loopj.android.http.RequestParams;
import tech.yozo.factoryrp.R;
import tech.yozo.factoryrp.ui.dialog.LoadingDialog;
import tech.yozo.factoryrp.utils.HttpClient;

import java.util.List;


/**
 * A login screen that offers login via user/password.
 */
public class LoginActivity extends AppCompatActivity implements HttpClient.OnHttpListener {

    // UI references.
    private EditText mCorpView;
    private EditText mUserView;
    private EditText mPasswordView;

    LoadingDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mCorpView = (EditText) findViewById(R.id.address);
        SharedPreferences sharedPreferences = getSharedPreferences("private_data", MODE_PRIVATE);
        mCorpView.setText(sharedPreferences.getString("corporateCode",""));
        mUserView = (EditText) findViewById(R.id.user);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL || id == EditorInfo.IME_ACTION_DONE) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mUserSignInButton = (Button) findViewById(R.id.user_sign_in_button);
        mUserSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid user, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String corp = mCorpView.getText().toString();
        String user = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid user address.
        if (TextUtils.isEmpty(user)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            RequestParams params = new RequestParams();
            params.add("username", user);
            params.add("password", password);
            params.add("corporateCode", corp);
            HttpClient client = HttpClient.getInstance();
            if(!client.isNetworkConnected(this)) {
                Toast.makeText(this, R.string.failure_network, Toast.LENGTH_SHORT).show();
                return;
            }

            LoadingDialog.Builder builder = new LoadingDialog.Builder(this)
                    .setMessage(R.string.loading_login);
            dialog = builder.create();
            dialog.show();
            client.login(this, this, params);
        }
    }

    @Override
    protected void onStop() {
        SharedPreferences sharedPreferences = getSharedPreferences("private_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("corporateCode", mCorpView.getText().toString().trim());
        editor.apply();

        super.onStop();
    }

    @Override
    public void onHttpSuccess(int requestType, Object obj, List<?> list) {
        dialog.dismiss();
        finish();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void onFailure(int requestType) {
        dialog.dismiss();
    }

    public void onAboutUS(View view) {
        Intent intent = new Intent(this, AboutUsActivity.class);
        startActivity(intent);
    }
}

