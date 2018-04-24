package com.example.android.cdocs.ui.login;

import com.example.android.cdocs.base.BasePresenter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

public class LoginPresenter extends BasePresenter<LoginContract.view> {

    LoginPresenter() {
    }

    // Get the user name and token from TwitterSession
    public void getUserData() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String userName = session.getUserName();
        getView().getUserDetails(userName, token);
    }

    @Override
    public void attachView(LoginContract.view view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
