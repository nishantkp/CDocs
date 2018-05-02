package com.example.android.cdocs.ui.dashboard;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.android.cdocs.base.BasePresenter;
import com.example.android.cdocs.data.DataManager;
import com.example.android.cdocs.utils.IConstants;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

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

    @BindingAdapter({"bind:callBack"})
    public static void loadUserBanner(final ImageView view
            , final DashBoardContract.View.DashboardImageLoaderCallback callback) {

        callback.onBannerLoading();

        TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(true, true, false)
                .enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
                        String url = result.data.profileBannerUrl;
                        Glide.with(view.getContext())
                                .load(url)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        callback.onBannerLoadError();
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        callback.onBannerReady();
                                        return false;
                                    }
                                }).into(view);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        callback.onBannerLoadError();
                    }
                });
    }
}
