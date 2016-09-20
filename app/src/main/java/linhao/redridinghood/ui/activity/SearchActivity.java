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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.BindView;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.presenter.implement.SearchDataImpl;
import linhao.redridinghood.ui.adapter.SearchAdapter;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.ui.listener.NavigationFinishClickListener;
import linhao.redridinghood.ui.listener.PageLoadMoreListener;
import linhao.redridinghood.ui.view.ActivityView;
import linhao.redridinghood.ui.view.LoadMoreView;
import linhao.redridinghood.widget.DividerItemDecoration;

/**
 * Created by linhao on 2016/9/17.
 */
public class SearchActivity extends BaseActivty implements ActivityView, PageLoadMoreListener, LoadMoreView, ItemOnClick{

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_content)
    EditText searchContent;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.content)
    RecyclerView content;
    @BindView(R.id.search_refresh)
    SwipeRefreshLayout searchRefresh;
    @BindView(R.id.main)
    LinearLayout main;
    @BindView(R.id.search_icon)
    ImageView searchIcon;
    @Inject
    SearchAdapter searchAdapter;
    @Inject
    SearchDataImpl searchDataImpl;
    private TextView loadMoreTitle;
    private ProgressWheel progress;     //加载更多的进度条
    private final static int SPAN_COUNT = 1;
    private int page;
    private String key;
    private boolean isLoadMore;
    @Override
    protected int getLayoutId() {
        return R.layout.search;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        initToolBar();
        initView();
    }

    private void initView() {
        MainActivity.InstanceManager().inject(this);
        searchDataImpl.setLoadMoreView(this);
        searchDataImpl.setToolTtitle(toolbarTitle);
        searchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length()>0){
                    delete.setVisibility(View.VISIBLE);
                }else {
                    delete.setVisibility(View.GONE);
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                     if (s.length()>0){
                         delete.setVisibility(View.VISIBLE);
                     }else {
                         delete.setVisibility(View.GONE);
                     }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        content.setLayoutManager(layoutManager);
        content.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        content.setAdapter(searchAdapter);
        searchAdapter.setLoadMoreListener(this);
        searchAdapter.setItemOnClick(this);
        page = 1;
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key=searchContent.getText().toString();
                loadData();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContent.setText("");
            }
        });
    }

    private void initToolBar() {
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.tool_color));
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(SearchActivity.this, R.drawable.back));
        search.setVisibility(View.GONE);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbarTitle.setText(R.string.search);
    }


    @Override
    public void showProgress() {
        searchRefresh.post(new Runnable() {
            @Override
            public void run() {
                searchRefresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        searchRefresh.post(new Runnable() {
            @Override
            public void run() {
                searchRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void loadFailView() {
        Snackbar.make(main, R.string.loadfail, Snackbar.LENGTH_SHORT).show();
    }

    private void loadData() {
        searchDataImpl.getData(key, page,isLoadMore);
    }

    @Override
    public void loadMore(TextView text, ProgressWheel progressWheel) {
        this.loadMoreTitle = text;
        this.progress = progressWheel;
        page++;
        isLoadMore=true;
        loadData();
    }

    @Override
    public void showMoreProgress() {
        progress.setVisibility(View.VISIBLE);
        loadMoreTitle.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFail() {
        loadMoreTitle.setText(R.string.nodata);
    }

    @Override
    public void hideMoreProgress() {
        loadMoreTitle.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view, String url) {
        Intent intent = new Intent(SearchActivity.this, ComicDetailActivity.class);
        intent.putExtra("url", url);
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
        searchAdapter=null;
    }
}
