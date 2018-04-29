package com.example.android.cdocs.ui.pdf;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.android.cdocs.base.BasePresenter;
import com.example.android.cdocs.data.DataManager;
import com.example.android.cdocs.ui.model.Docs;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class PdfPresenter extends BasePresenter<PdfContract.View>
        implements PdfContract.Presenter {

    private DataManager dataManager;
    private static Docs docs;

    public PdfPresenter(DataManager dataManager, Docs docs) {
        this.dataManager = dataManager;
        PdfPresenter.docs = docs;
    }

    @Override
    public void attachView(PdfContract.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void getNextPage() {
        Bitmap bitmap = dataManager.getNextPage(docs);
        if (bitmap == null) {
            getView().onError("Error retrieving page");
        } else {
            getView().loadPage(bitmap);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void getPreviousPage() {
        Bitmap bitmap = dataManager.getPreviousPage(docs);
        if (bitmap == null) {
            getView().onError("Error retrieving page");
        } else {
            getView().loadPage(bitmap);
        }
    }
}
