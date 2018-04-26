package com.example.android.cdocs.ui.dashboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.example.android.cdocs.R;
import com.example.android.cdocs.base.BaseActivity;
import com.example.android.cdocs.databinding.ActivityDashBoardBinding;
import com.example.android.cdocs.ui.adapter.DocsAdapter;
import com.example.android.cdocs.ui.model.Docs;
import com.example.android.cdocs.utils.IConstants;

import java.util.List;

public class DashBoardActivity extends BaseActivity
        implements DocsAdapter.OnItemClickListener, DashBoardContract.View {

    private ActivityDashBoardBinding activityDashBoardBinding;
    private DashBoardPresenter mPresenter;
    private DocsAdapter mAdapter;
    private IntentFilter mIntentFilter;
    private NotificationBroadcast mNotificationBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashBoardBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_dash_board);

        mPresenter = new DashBoardPresenter(mDataManager);
        mPresenter.attachView(this);
        setRecyclerView();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(IConstants.Fcm.ACTION_FCM_NOTIFICATION);
        mNotificationBroadcast = new NotificationBroadcast();
        mPresenter.loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mNotificationBroadcast, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNotificationBroadcast);
    }

    @Override
    public void onClick(Docs item, int position) {
        Toast.makeText(DashBoardActivity.this,
                "Position : " + position + "Docs : " + item.getTitle(),
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showData(List<Docs> docsList) {
        mAdapter.swapData(docsList);
    }

    /**
     * Set up the Recycler view
     */
    private void setRecyclerView() {
        // LayoutManager that defines how RecyclerView behaves
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                this
                , LinearLayoutManager.VERTICAL
                , false);
        activityDashBoardBinding.rvDocs.setLayoutManager(layoutManager);
        mAdapter = new DocsAdapter(this, null);
        activityDashBoardBinding.rvDocs.setAdapter(mAdapter);
        runLayoutAnimation(activityDashBoardBinding.rvDocs);
    }

    // Adds fall down layout animation to RecyclerView items
    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_anim_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    private class NotificationBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentId = intent.getAction();
            final String fcmNotification = IConstants.Fcm.ACTION_FCM_NOTIFICATION;
            switch (intentId) {
                case fcmNotification:
                    mPresenter.loadData();
                    break;
                default:
                    Log.i(DashBoardActivity.class.getSimpleName(),
                            "NotificationBroadcast() : No intent ID found!");
                    break;
            }
        }
    }
}
