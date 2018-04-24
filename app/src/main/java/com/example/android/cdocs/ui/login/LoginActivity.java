package com.example.android.cdocs.ui.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.example.android.cdocs.R;
import com.example.android.cdocs.base.BaseActivity;
import com.example.android.cdocs.databinding.ActivityLoginBinding;
import com.example.android.cdocs.ui.dashboard.DashBoardActivity;
import com.example.android.cdocs.utils.IConstants;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

public class LoginActivity extends BaseActivity implements LoginContract.View {
    private ActivityLoginBinding mLoginActivityBinging;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginActivityBinging =
                DataBindingUtil.setContentView(this, R.layout.activity_login);
        mLoginPresenter = new LoginPresenter(mDataManager);
        mLoginPresenter.attachView(this);
        if (mLoginPresenter.isUserTokenAvailable()) {
            startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
            finish();
        }

        mLoginActivityBinging.btnTwitterLogin.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                mLoginPresenter.getUserTwitterData();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.i(LoginActivity.class.getSimpleName(), "Twitter Exception : " + exception.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass results of authentication back to button
        mLoginActivityBinging.btnTwitterLogin.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getUserDetails(String userName, String token) {
        // Save user name and token into SharedPreference
        mLoginPresenter.saveDataToPreference(IConstants.Preference.KEY_USER_NAME_PREF, userName);
        mLoginPresenter.saveDataToPreference(IConstants.Preference.KEY_TOKEN_PREF, token);
        startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
        finish();
    }
}
