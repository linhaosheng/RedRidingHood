package linhao.redridinghood.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.BindView;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.presenter.implement.HotNewDataImpl;
import linhao.redridinghood.ui.fragment.HotNewFirstFragemnt;
import linhao.redridinghood.ui.fragment.HotNewFourFragemnt;
import linhao.redridinghood.ui.fragment.HotNewSecondFragemnt;
import linhao.redridinghood.ui.fragment.HotNewThridFragemnt;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.ui.listener.NavigationFinishClickListener;
import linhao.redridinghood.ui.view.ActivityView;

/**
 * Created by linhao on 2016/9/8.
 */
public class HotNewActivity extends BaseActivty implements SwipeRefreshLayout.OnRefreshListener, ActivityView {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.hot_new_animate_fragment)
    FrameLayout hotNewAnimateFragment;
    @BindView(R.id.hot_new_animate_refresh)
    SwipeRefreshLayout hotNewAnimateRefresh;
    @BindView(R.id.hot_new_animate_bottom_bar)
    BottomBar hotNewAnimateBottomBar;
    @BindView(R.id.main)
    CoordinatorLayout main;
    @Inject
    HotNewDataImpl hotNewDataImpl;

    HotNewFirstFragemnt firstFragment;
    HotNewSecondFragemnt secondFragment;
    HotNewThridFragemnt thridFragment;
    HotNewFourFragemnt fourFragment;

    FragmentTransaction transaction;

    @Override
    protected int getLayoutId() {
        return R.layout.hot_new_animate;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        //initComponent();
        initToolBar();
        initView();
        loadData();
    }

    private void initComponent() {
        /**fragmentComponent = DaggerClientFragmentComponent.
                builder().
                clientFragmentModule(new ClientFragmentModule(this)).build();
        fragmentComponent.inject(this);
     **/
    }

    private void initView() {
        MainActivity.InstanceManager().inject(this);

        firstFragment = HotNewFirstFragemnt.Instance();
        secondFragment = HotNewSecondFragemnt.Instance();
        thridFragment = HotNewThridFragemnt.Instance();
        fourFragment = HotNewFourFragemnt.Instance();
        hotNewAnimateBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.item_2016_broadcast:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.hot_new_animate_fragment, fourFragment)
                                .commit();
                        break;
                    case R.id.item_2015_broadcast:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.hot_new_animate_fragment, thridFragment).commit();
                        break;
                    case R.id.item_2014_broadcast:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.hot_new_animate_fragment, secondFragment).commit();
                        break;
                    case R.id.item_2013_broadcast:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.hot_new_animate_fragment, firstFragment).commit();
                        break;
                }
            }
        });
    }

    private void initToolBar() {
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.tool_color));
        toolbar.setTitle("");
        toolbarTitle.setText(R.string.hot_new_animate);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(HotNewActivity.this, R.drawable.back));
        search.setVisibility(View.GONE);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    private void loadData() {
        hotNewDataImpl.loadData();
    }

    @Override
    public void showProgress() {
        hotNewAnimateRefresh.post(new Runnable() {
            @Override
            public void run() {
                hotNewAnimateRefresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        hotNewAnimateRefresh.post(new Runnable() {
            @Override
            public void run() {
                hotNewAnimateRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void loadFailView() {
        Snackbar.make(main, R.string.loadfail, Snackbar.LENGTH_SHORT).show();

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
        firstFragment= null;
        secondFragment=null;
        thridFragment=null;
        fourFragment=null;
    }
}
