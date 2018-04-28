package com.example.android.cdocs.base;

public class BasePresenter<T extends MvpView> implements Presenter<T> {
    private T view;

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    public T getView() {
        return view;
    }

    public boolean isViewAvailable() {
        return view != null;
    }
}
