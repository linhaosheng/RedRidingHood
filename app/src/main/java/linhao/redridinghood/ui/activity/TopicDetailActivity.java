package linhao.redridinghood.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.presenter.implement.TopicDetailDataImpl;
import linhao.redridinghood.ui.adapter.TopicDetailAdapter;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.ui.listener.NavigationFinishClickListener;
import linhao.redridinghood.ui.listener.PageLoadMoreListener;
import linhao.redridinghood.ui.view.ActivityView;
import linhao.redridinghood.ui.view.LoadMoreView;
import linhao.redridinghood.widget.DividerItemDecoration;

/**
 * Created by linhao on 2016/9/15.
 */
public class TopicDetailActivity extends BaseActivty implements ActivityView, LoadMoreView ,SwipeRefreshLayout.OnRefreshListener,ItemOnClick{

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.topic_recycle)
    RecyclerView topicRecycle;
    @BindView(R.id.topic_refresh)
    SwipeRefreshLayout topicRefresh;
    @BindView(R.id.main)
    LinearLayout main;
    private TextView loadMoreTitle;
    private ProgressWheel progress;     //加载更多的进度条
    private static final int SPAN_COUNT = 1;  //列数
    private String url;
    private int page;
    private boolean isLoadMore;
    @Inject
    TopicDetailAdapter topicDetailAdapter;
    @Inject
    TopicDetailDataImpl topicDetailDataImpl;
    @Override
    protected int getLayoutId() {
        return R.layout.topic;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        initToolBar();
        initView();
        loadData();
    }

    private void loadData() {
         topicDetailDataImpl.getData(url,page,isLoadMore);
    }

    private void initToolBar() {
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.tool_color));
        toolbar.setTitle("");
        toolbarTitle.setText(R.string.topics_detail);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(TopicDetailActivity.this, R.drawable.back));
        search.setVisibility(View.GONE);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
    }

    private void initView() {
        url=getIntent().getStringExtra("url");
        url="http://www.hltm.tv"+url;
        topicRefresh.setOnRefreshListener(this);
        MainActivity.InstanceManager().inject(this);
        topicRefresh.setColorSchemeResources(R.color.red_light, R.color.green_light, R.color.blue_light, R.color.orange_light);
        topicRefresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        topicRecycle.setLayoutManager(layoutManager);
        topicRecycle.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        topicDetailAdapter.setLoadMoreListener(new PageLoadMoreListener() {
            @Override
            public void loadMore(TextView text, ProgressWheel progressWheel) {
                loadMoreTitle=text;
                progress=progressWheel;
                page++;
                isLoadMore=true;
                loadData();
            }
        });
        topicRecycle.setAdapter(topicDetailAdapter);
        topicDetailDataImpl.setLoadMoreView(this);
        topicDetailAdapter.setItemOnClick(this);
    }

    @Override
    public void showProgress() {
        topicRefresh.post(new Runnable() {
            @Override
            public void run() {
            topicRefresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        topicRefresh.post(new Runnable() {
            @Override
            public void run() {
                topicRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void loadFailView() {
        Snackbar.make(main,R.string.loadfail,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMoreProgress() {
        progress.setVisibility(View.VISIBLE);
        loadMoreTitle.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFail() {
        loadMoreTitle.setVisibility(View.VISIBLE);
        loadMoreTitle.setText(R.string.load_more_fail);
    }

    @Override
    public void hideMoreProgress() {
        loadMoreTitle.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
       loadData();
    }

    @Override
    public void onClick(View view, String url) {
        Intent intent=new Intent(TopicDetailActivity.this,ComicDetailActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
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
        topicDetailAdapter=null;
    }
}
