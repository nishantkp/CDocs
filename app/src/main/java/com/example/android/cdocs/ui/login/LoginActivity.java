package com.example.android.cdocs.ui.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.android.cdocs.R;
import com.example.android.cdocs.databinding.ActivityLoginBinding;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding mLoginActivityBinging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginActivityBinging =
                DataBindingUtil.setContentView(this, R.layout.activity_login);

        mLoginActivityBinging.btnTwitterLogin.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
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
}
