package com.example.android.cdocs.ui.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.android.cdocs.R;
import com.example.android.cdocs.base.BaseActivity;
import com.example.android.cdocs.databinding.ActivityLoginBinding;
import com.example.android.cdocs.ui.dashboard.DashBoardActivity;
import com.example.android.cdocs.utils.IConstants;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

public class LoginActivity extends BaseActivity implements LoginContract.View {
    private ActivityLoginBinding mLoginActivityBinging;
    private LoginPresenter mLoginPresenter;
    private TwitterAuthClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginActivityBinging =
                DataBindingUtil.setContentView(this, R.layout.activity_login);
        mLoginActivityBinging.setLogin(this);

        client = new TwitterAuthClient();

        // When app is in background or app is swiped off and it receives notification,
        // and user clicks on system tray notification, we get the data payload in launcher
        // activity's onCreate() method.
        // To get the data call getIntent().getExtras() and extract the data from bundle with
        // the key provided from firebase console when sending the message
        Bundle bundle = getIntent().getExtras();

        mLoginPresenter = new LoginPresenter(mDataManager, bundle);
        mLoginPresenter.attachView(this);

        if (mLoginPresenter.isUserTokenAvailable()) {
            startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass results of authentication back to TwitterAuthClient
        client.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getUserDetails(String userName, String token) {
        // Save user name and token into SharedPreference
        mLoginPresenter.saveDataToPreference(IConstants.Preference.KEY_USER_NAME_PREF, userName);
        mLoginPresenter.saveDataToPreference(IConstants.Preference.KEY_TOKEN_PREF, token);
        startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
        finish();
    }

    @Override
    public void onLoginButtonClick() {
        client.authorize(LoginActivity.this, new Callback<TwitterSession>() {
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
}
