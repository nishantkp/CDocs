package com.example.android.cdocs.base;

public interface Presenter<T extends MvpView> {
    void attachView(T view);

    void detachView();
}
