package com.example.android.cdocs.ui.login;

import com.example.android.cdocs.base.MvpView;

public interface LoginContract {
    interface View extends MvpView {
        void getUserDetails(String userName, String token);

        void onLoginButtonClick();
    }

    interface Presenter {
        void getUserTwitterData();

        boolean isUserTokenAvailable();

        void saveDataToPreference(String key, String value);

        String readDataFromPreference(String key);
    }
}
