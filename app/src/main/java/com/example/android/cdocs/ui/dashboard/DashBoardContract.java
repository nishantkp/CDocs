package com.example.android.cdocs.ui.dashboard;

import com.example.android.cdocs.base.MvpView;
import com.example.android.cdocs.ui.model.Docs;

import java.util.List;

public interface DashBoardContract {
    interface View extends MvpView {
        void showData(List<Docs> docsList);

        void showUserName(String userName);

        interface DashboardImageLoaderCallback {
            // called when banner starts downloading
            void onBannerLoading();

            // called when banner has successfully loaded
            void onBannerReady();

            // called when banner loading fails
            void onBannerLoadError();
        }
    }

    interface Presenter {
        void loadData();

        void logout();
    }
}
