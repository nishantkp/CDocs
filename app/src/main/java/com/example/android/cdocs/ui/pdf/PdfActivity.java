package com.example.android.cdocs.ui.pdf;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import com.example.android.cdocs.R;
import com.example.android.cdocs.base.BaseActivity;
import com.example.android.cdocs.databinding.ActivityPdfBinding;
import com.example.android.cdocs.ui.model.Docs;
import com.example.android.cdocs.utils.IConstants;

public class PdfActivity extends BaseActivity implements PdfContract.View {

    private ActivityPdfBinding activityPdfBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPdfBinding = DataBindingUtil.setContentView(this, R.layout.activity_pdf);

        Docs docs = getIntent().getParcelableExtra(IConstants.Pdf.KEY_PDF_DOCS);
        PdfPresenter presenter = new PdfPresenter(mDataManager, docs);

        activityPdfBinding.setPresenter(presenter);
        presenter.attachView(this);

        presenter.startDownload();
    }

    @Override
    public void loadPage(Bitmap bitmap) {
        activityPdfBinding.imvPdfContainer.setImageBitmap(bitmap);
    }

    @Override
    public void onError(String error) {
        Snackbar.make(activityPdfBinding.coordinatorLayoutPdf, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void downloadStatus(boolean status) {
        activityPdfBinding.setStatus(status);
    }
}
