package com.example.android.cdocs.ui.dashboard;

import com.example.android.cdocs.base.BasePresenter;
import com.example.android.cdocs.data.DataManager;

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
    }

    @Override
    public void logout() {
        // TODO: Implement logout functionality, particularly clearing database and preferences
    }
}
