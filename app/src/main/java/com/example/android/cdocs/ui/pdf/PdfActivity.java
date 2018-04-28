package com.example.android.cdocs.ui.pdf;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.android.cdocs.R;
import com.example.android.cdocs.base.BaseActivity;
import com.example.android.cdocs.databinding.ActivityPdfBinding;

public class PdfActivity extends BaseActivity {

    private ActivityPdfBinding activityPdfBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPdfBinding = DataBindingUtil.setContentView(this, R.layout.activity_pdf);
    }
}
