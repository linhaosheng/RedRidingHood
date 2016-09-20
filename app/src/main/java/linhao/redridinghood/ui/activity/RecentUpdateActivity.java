package linhao.redridinghood.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.BindView;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.presenter.implement.RecentUpdateDataImpl;
import linhao.redridinghood.ui.adapter.RecentUpdateAdapter;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.ui.listener.NavigationFinishClickListener;
import linhao.redridinghood.ui.view.ActivityView;
import linhao.redridinghood.widget.DividerItemDecoration;

/**
 * Created by linhao on 2016/8/28.
 */
public class RecentUpdateActivity extends BaseActivty implements SwipeRefreshLayout.OnRefreshListener, ActivityView, ItemOnClick {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recent_update_list)
    RecyclerView recentUpdateList;
    @BindView(R.id.main)
    CoordinatorLayout main;
    @BindView(R.id.recent_refersh)
    SwipeRefreshLayout recent_refersh;

    @Inject
    RecentUpdateDataImpl recentUpdateData;
    @Inject
    RecentUpdateAdapter recentUpdateAdapter;
    public static final int SPAN_COUNT = 1;  //列数


    @Override
    protected int getLayoutId() {
        return R.layout.recent_update;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        MainActivity.InstanceManager().inject(this);
        initView();
        loadData();
    }

    private void loadData() {
        recentUpdateData.getRecentUpdateData();
    }

    private void initView() {
        toolbar.setTitle("");
        toolbarTitle.setText(R.string.recent_update);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(RecentUpdateActivity.this, R.drawable.back));
        search.setVisibility(View.GONE);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        setSupportActionBar(toolbar);
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        recentUpdateList.setLayoutManager(manager);
        recent_refersh.setOnRefreshListener(this);
        recentUpdateList.setAdapter(recentUpdateAdapter);
        recentUpdateAdapter.setItemOnClick(this);
        recentUpdateList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void showProgress() {
        recent_refersh.post(new Runnable() {
            @Override
            public void run() {
                recent_refersh.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        recent_refersh.post(new Runnable() {
            @Override
            public void run() {
                recent_refersh.setRefreshing(false);
            }
        });
    }

    @Override
    public void loadFailView() {
        Snackbar.make(main, getResources().getString(R.string.load_fail), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view, String url) {
        Intent intent = new Intent(RecentUpdateActivity.this, ComicDetailActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recentUpdateAdapter = null;
    }
}
