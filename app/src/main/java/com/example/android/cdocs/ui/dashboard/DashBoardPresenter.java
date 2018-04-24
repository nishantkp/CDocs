package com.example.android.cdocs.ui.dashboard;

import com.example.android.cdocs.base.BasePresenter;
import com.example.android.cdocs.ui.model.Docs;

import java.util.ArrayList;
import java.util.List;

public class DashBoardPresenter extends BasePresenter<DashBoardContract.View>
        implements DashBoardContract.Presenter {

    DashBoardPresenter() {
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
    public void loadFakeData() {
        List<Docs> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new Docs("This is to test RecyclerView with DataBinding!", "pdf"));
        }
        getView().showData(list);
    }
}
