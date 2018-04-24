package com.example.android.cdocs.ui.login;

import com.example.android.cdocs.base.BasePresenter;
import com.example.android.cdocs.data.DataManager;
import com.example.android.cdocs.utils.IConstants;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

public class LoginPresenter extends BasePresenter<LoginContract.view>
        implements LoginContract.Presenter {
    private DataManager dataManager;

    LoginPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(LoginContract.view view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void getUserTwitterData() {
        // Get the user name and token from TwitterSession
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String userName = session.getUserName();
        getView().getUserDetails(userName, token);
    }

    @Override
    public boolean isUserTokenAvailable() {
        return dataManager.readDataFromPreference(IConstants.Preference.KEY_TOKEN_PREF) != null;
    }

    @Override
    public void saveDataToPreference(String key, String value) {
        dataManager.writeDataToPreference(key, value);
    }

    @Override
    public String readDataFromPreference(String key) {
        return dataManager.readDataFromPreference(key);
    }
}
