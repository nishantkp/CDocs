package com.example.android.cdocs.ui.dashboard;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.cdocs.R;
import com.example.android.cdocs.databinding.ActivityDashBoardBinding;

public class DashBoardActivity extends AppCompatActivity {
    ActivityDashBoardBinding activityDashBoardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashBoardBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_dash_board);
    }
}
