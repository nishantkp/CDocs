package com.example.android.cdocs.ui.dashboard;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.android.cdocs.R;
import com.example.android.cdocs.ui.adapter.DocsAdapter;
import com.example.android.cdocs.databinding.ActivityDashBoardBinding;
import com.example.android.cdocs.ui.model.Docs;
import com.example.android.cdocs.utils.IConstants;

import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends AppCompatActivity {
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
        activityDashBoardBinding.rvDocs.setAdapter(new DocsAdapter(list));
    }
}
