package com.example.android.cdocs.ui.login;

import com.example.android.cdocs.base.MvpView;

public interface LoginContract {
    interface view extends MvpView {
        void getUserDetails(String userName, String token);
    }
}
