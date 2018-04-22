package com.example.android.cdocs.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.cdocs.R;
import com.example.android.cdocs.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding loginActivityBinging =
                DataBindingUtil.setContentView(this, R.layout.activity_login);
    }
}
