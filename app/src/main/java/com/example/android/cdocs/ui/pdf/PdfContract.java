package com.example.android.cdocs.ui.pdf;

import android.graphics.Bitmap;

import com.example.android.cdocs.base.MvpView;
import com.example.android.cdocs.ui.model.Docs;

public interface PdfContract {
    interface View extends MvpView {
        void loadPage(Bitmap bitmap);

        void onError(String error);
    }

    interface Presenter {
        void getNextPage();

        void getPreviousPage();

        //   void downloadFile(Docs docs);
    }
}
