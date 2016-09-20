package linhao.redridinghood.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.presenter.implement.RankingDataImpl;
import linhao.redridinghood.ui.adapter.RankingAdapter;
import linhao.redridinghood.ui.adapter.TabFragmentPageAdapter;
import linhao.redridinghood.ui.fragment.RankingFragment;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.ui.listener.MyPageChangeOnListener;
import linhao.redridinghood.ui.listener.NavigationFinishClickListener;
import linhao.redridinghood.ui.view.ActivityView;
import linhao.redridinghood.util.ConstantUtil;

/**
 * Created by linhao on 2016/9/2.
 */
public class RangkingActivity extends BaseActivty implements ActivityView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_page)
    ViewPager viewPage;
    @BindView(R.id.ranking_tab)
    TabLayout rankingTab;
    @BindView(R.id.main)
    LinearLayout main;
    @BindView(R.id.ranking_refersh)
    SwipeRefreshLayout rankingRefersh;

    private List<String> titleList;
    private ArrayList<Fragment> fragmentList;
    private RankingFragment rankingFragment;
    private MyPageChangeOnListener myPageChangeListener;
    private int pageCurrentItem;
    private Bundle bundle;

    @Inject
    RankingAdapter rankingAdapter;
    @Inject
    RankingDataImpl rankingDataImpl;

    @Override
    protected int getLayoutId() {
        return R.layout.ranking;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        init_toolbar();
        initView();
        loadData();
    }

    private void init_toolbar() {
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.tool_color));
        toolbar.setTitle("");
        toolbarTitle.setText(R.string.ranking);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(RangkingActivity.this, R.drawable.back));
        search.setVisibility(View.GONE);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
    }

    private void initView() {
        MainActivity.InstanceManager().inject(this);
        rankingRefersh.setOnRefreshListener(this);
        titleList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titleList.add("7月");
        titleList.add("4月");
        titleList.add("动漫");
        titleList.add("咨询");
        for (int i = 0; i < titleList.size(); i++) {
            rankingFragment = new RankingFragment();
            bundle = new Bundle();
            rankingFragment.setArguments(bundle);
            fragmentList.add(rankingFragment);
            rankingTab.addTab(rankingTab.newTab().setText(titleList.get(i)));
        }
        rankingTab.setTabTextColors(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.red));
        TabFragmentPageAdapter tabPageAdapter = new TabFragmentPageAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPage.setAdapter(tabPageAdapter);
        viewPage.setCurrentItem(0);
        myPageChangeListener = new MyPageChangeOnListener();
        myPageChangeListener.setRankingAdapter(rankingAdapter);
        myPageChangeListener.setAdapterType(ConstantUtil.RankingAdapter);
        viewPage.addOnPageChangeListener(myPageChangeListener);
        rankingTab.setupWithViewPager(viewPage);
        rankingTab.setTabsFromPagerAdapter(tabPageAdapter);
    }

    @Override
    public void showProgress() {
        rankingRefersh.post(new Runnable() {
            @Override
            public void run() {
                rankingRefersh.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        rankingRefersh.post(new Runnable() {
            @Override
            public void run() {
                rankingRefersh.setRefreshing(false);
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

    private void loadData() {
        rankingDataImpl.getData();
        pageCurrentItem = myPageChangeListener.getCurrentPosition();
        bundle.putInt("position", pageCurrentItem);
        rankingFragment.setArguments(bundle);
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
        rankingAdapter = null;
    }
}
