package linhao.redridinghood.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.model.entity.WeekUpdate;
import linhao.redridinghood.presenter.implement.WeekUpdateDataImpl;
import linhao.redridinghood.ui.adapter.TabFragmentPageAdapter;
import linhao.redridinghood.ui.adapter.WeekUpdateAdapter;
import linhao.redridinghood.ui.fragment.WeekUpdateFragment;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.ui.listener.MyPageChangeOnListener;
import linhao.redridinghood.ui.listener.NavigationFinishClickListener;
import linhao.redridinghood.ui.view.ActivityView;
import linhao.redridinghood.util.ConstantUtil;

/**
 * Created by linhao on 2016/8/27.
 */
public class WeekUpdateActivity extends BaseActivty implements SwipeRefreshLayout.OnRefreshListener, ActivityView ,ItemOnClick{
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.week_update_tabs)
    TabLayout weekUpdateTabs;
    @BindView(R.id.view_page)
    ViewPager viewPage;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.main)
    CoordinatorLayout main;

    @Inject
    WeekUpdateAdapter weekUpdateAdapter;
    @Inject
    WeekUpdateDataImpl weekUpdateDataImpl;

    private List<String> titleList;
    private ArrayList<Fragment> fragmentList;
    private WeekUpdateFragment weekUpdateFragment;
    private MyPageChangeOnListener myPageChangeOnListener;
    int currentPosition = 0;
    private Bundle bundle;

    @Override
    protected int getLayoutId() {
        return R.layout.week_update;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        init_Toolbar();
        init_TabLayout_Viewpage();
        loadData();
    }

    private void init_TabLayout_Viewpage() {
        MainActivity.InstanceManager().inject(this);
        weekUpdateAdapter.setItemOnClick(this);
        swipeRefresh.setColorSchemeResources(R.color.red_light, R.color.green_light, R.color.blue_light, R.color.orange_light);
        swipeRefresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        swipeRefresh.setOnRefreshListener(this);

        titleList = new ArrayList<>();
        titleList.add(getResources().getString(R.string.monday));
        titleList.add(getResources().getString(R.string.tuesday));
        titleList.add(getResources().getString(R.string.wednesday));
        titleList.add(getResources().getString(R.string.thursday));
        titleList.add(getResources().getString(R.string.friday));
        titleList.add(getResources().getString(R.string.saturday));
        titleList.add(getResources().getString(R.string.sunday));

        fragmentList = new ArrayList<>();
        for (int i = 0; i < titleList.size(); i++) {
            weekUpdateFragment = new WeekUpdateFragment();
            weekUpdateFragment.setWeekUpdateAdapter(weekUpdateAdapter);
            bundle=new Bundle();
            weekUpdateFragment.setArguments(bundle);
            fragmentList.add(weekUpdateFragment);
            weekUpdateTabs.addTab(weekUpdateTabs.newTab().setText(titleList.get(i)));
        }
        weekUpdateTabs.setTabTextColors(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.red));
        TabFragmentPageAdapter tabAdapter = new TabFragmentPageAdapter(getSupportFragmentManager(), fragmentList, titleList);
        tabAdapter.setFragment_Flag(ConstantUtil.WeekUpdateFragment);
        viewPage.setAdapter(tabAdapter);
        viewPage.setCurrentItem(0);
        myPageChangeOnListener = new MyPageChangeOnListener();
        myPageChangeOnListener.setWeekUpdateAdapter(weekUpdateAdapter);
        myPageChangeOnListener.setAdapterType(ConstantUtil.WeekUpdater);
        currentPosition=myPageChangeOnListener.getCurrentPosition();
        viewPage.addOnPageChangeListener(myPageChangeOnListener);
        weekUpdateTabs.setupWithViewPager(viewPage);
        weekUpdateTabs.setTabsFromPagerAdapter(tabAdapter);
    }

    private void init_Toolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbarTitle.setText(R.string.week_update_title);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(WeekUpdateActivity.this, R.drawable.back));
        search.setVisibility(View.GONE);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
    }


    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void showProgress() {
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void loadFailView() {
        Snackbar.make(main, R.string.loadfail, Snackbar.LENGTH_SHORT).show();
    }

    private void loadData(){
        weekUpdateDataImpl.getWeekUpdateData();
        bundle.putInt("currentPosition",currentPosition);
        weekUpdateFragment.setArguments(bundle);
    }

    @Override
    public void onClick(View view, String url) {
        Intent intent=new Intent(WeekUpdateActivity.this,ComicDetailActivity.class);
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
        weekUpdateAdapter=null;
    }
}
