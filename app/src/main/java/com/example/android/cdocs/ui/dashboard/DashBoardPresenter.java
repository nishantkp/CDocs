package com.example.android.cdocs.ui.dashboard;

import com.bumptech.glide.Glide;
import com.example.android.cdocs.base.BasePresenter;
import com.example.android.cdocs.data.DataManager;
import com.example.android.cdocs.ui.model.Docs;
import com.example.android.cdocs.utils.IConstants;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class DashBoardPresenter extends BasePresenter<DashBoardContract.View>
        implements DashBoardContract.Presenter {

    private DataManager dataManager;

    DashBoardPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(DashBoardContract.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public void loadData() {
        getView().showData(dataManager.readDataFromDatabase());
        getView().showUserName(
                dataManager.readDataFromPreference(IConstants.Preference.KEY_USER_NAME_PREF));
    }

    @Override
    public void logout() {
        dataManager.logout();
    }

    @Override
    public void loadUserBanner() {
        TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(true, true, false)
                .enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
                        String url = result.data.profileBannerUrl;
                        getView().loadBannerBitmap(url);
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });
    }
}
