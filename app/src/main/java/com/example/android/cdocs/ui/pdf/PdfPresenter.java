package com.example.android.cdocs.ui.pdf;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;

import com.example.android.cdocs.base.BasePresenter;
import com.example.android.cdocs.data.DataManager;
import com.example.android.cdocs.ui.model.Docs;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
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

    @Override
    public void startDownload() {
        // Set the download status to false indicating download is not complete
        getView().downloadStatus(false);
        // Download file from url
        dataManager.downloadFileFromUrlRx(docs.getUrl())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(final ResponseBody responseBody) {
                        // Store file in Internal storage using Rx
                        Observable.fromCallable(new Callable<Object>() {
                            @Override
                            public Object call() throws Exception {
                                return dataManager.writeDataToDisk(responseBody, docs);
                            }
                        }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Object>() {
                                    @Override
                                    public void onSubscribe(Disposable d) { }

                                    @Override
                                    public void onNext(Object o) { }

                                    @Override
                                    public void onError(Throwable e) {
                                        getView().onError("Error saving file to memory");
                                    }

                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onComplete() {
                                        // After writing is completed, set the download status true
                                        // to indicate download and writing operation is completed
                                        getView().downloadStatus(true);
                                        Bitmap bitmap = dataManager.getFirstPage(docs);
                                        if (bitmap == null) {
                                            getView().onError("Error retrieving page");
                                        } else {
                                            getView().loadPage(bitmap);
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onError("Error downloading file");
                    }

                    @Override
                    public void onComplete() { }
                });
    }
}
