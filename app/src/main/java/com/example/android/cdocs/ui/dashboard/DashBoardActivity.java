package com.example.android.cdocs.ui.dashboard;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.example.android.cdocs.R;
import com.example.android.cdocs.databinding.ActivityDashBoardBinding;
import com.example.android.cdocs.ui.adapter.DocsAdapter;
import com.example.android.cdocs.ui.model.Docs;
import com.example.android.cdocs.utils.IConstants;

import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends AppCompatActivity
        implements DocsAdapter.OnItemClickListener {
    ActivityDashBoardBinding activityDashBoardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashBoardBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_dash_board);

        if (getIntent() != null) {
            activityDashBoardBinding
                    .tvUserId
                    .setText(getIntent().getStringExtra(IConstants.Login.KEY_USER_NAME));
        }

        /* Temp list to check RecyclerView with DataBinding */
        List<Docs> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new Docs("This is to test RecyclerView with DataBinding!", "pdf"));
        }

        // LayoutManager that defines how RecyclerView behaves
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                this
                , LinearLayoutManager.VERTICAL
                , false);
        activityDashBoardBinding.rvDocs.setLayoutManager(layoutManager);
        activityDashBoardBinding.rvDocs.setAdapter(new DocsAdapter(this, list));
        runLayoutAnimation(activityDashBoardBinding.rvDocs);
    }

    @Override
    public void onClick(Docs item, int position) {
        Toast.makeText(DashBoardActivity.this,
                "Position : " + position + "Docs : " + item.getTitle(),
                Toast.LENGTH_SHORT)
                .show();
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
}
