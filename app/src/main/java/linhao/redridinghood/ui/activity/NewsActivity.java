package linhao.redridinghood.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.BindView;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.presenter.implement.NewsDataImpl;
import linhao.redridinghood.ui.adapter.NewsAdapter;
import linhao.redridinghood.ui.listener.NavigationFinishClickListener;
import linhao.redridinghood.ui.listener.PageLoadMoreListener;
import linhao.redridinghood.ui.view.ActivityView;
import linhao.redridinghood.ui.view.LoadMoreView;
import linhao.redridinghood.widget.DividerItemDecoration;

/**
 * Created by linhao on 2016/9/3.
 */
public class NewsActivity extends BaseActivty implements ActivityView, SwipeRefreshLayout.OnRefreshListener, LoadMoreView {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.new_recycle)
    RecyclerView newRecycle;
    @BindView(R.id.news_refresh)
    SwipeRefreshLayout newsRefresh;
    @BindView(R.id.main)
    CoordinatorLayout main;

    private static final int SPAN_COUNT = 1;  //列数
    private int currentPage = 1;
    @Inject
    NewsDataImpl newsData;
    @Inject
    NewsAdapter newsAdapter;
    private TextView textView;
    private ProgressWheel progress;

    @Override
    protected int getLayoutId() {
        return R.layout.news;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        MainActivity.InstanceManager().inject(this);
        initTool();
        initView();
        loadData();
    }

    private void initTool() {
        toolbar.setTitle("");
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.tool_color));
        toolbarTitle.setText(R.string.news_list);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(NewsActivity.this, R.drawable.back));
        search.setVisibility(View.GONE);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
    }

    private void initView() {
        newsRefresh.setColorSchemeResources(R.color.red_light, R.color.green_light, R.color.blue_light, R.color.orange_light);
        newsRefresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        newsRefresh.setOnRefreshListener(this);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        newRecycle.setLayoutManager(layoutManager);
        newsData.setLoadMoreView(this);
        newRecycle.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        newsAdapter.setLoadMoreListener(new PageLoadMoreListener() {
            @Override
            public void loadMore(TextView text, ProgressWheel progressWheel) {
                textView = text;
                progress = progressWheel;
                newsData.pageLoadMore(currentPage++);
            }
        });
        newRecycle.setAdapter(newsAdapter);

    }


    private void loadData() {
        newsData.getNewData();
    }

    @Override
    public void showProgress() {
        newsRefresh.post(new Runnable() {
            @Override
            public void run() {
                newsRefresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        newsRefresh.post(new Runnable() {
            @Override
            public void run() {
                newsRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void loadFailView() {
        Snackbar.make(main, R.string.loadfail, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void showMoreProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadFail() {
        textView.setVisibility(View.VISIBLE);
        textView.setText(R.string.load_more_fail);
    }

    @Override
    public void hideMoreProgress() {
        progress.setVisibility(View.GONE);
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
        newsAdapter=null;
    }
}
