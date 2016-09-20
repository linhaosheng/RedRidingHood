package linhao.redridinghood.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.inject.component.ClientApiManagerComponent;
import linhao.redridinghood.inject.component.DaggerClientApiManagerComponent;
import linhao.redridinghood.inject.module.ClientApiManagerModule;
import linhao.redridinghood.model.entity.Carousel;
import linhao.redridinghood.model.storage.SettingShared;
import linhao.redridinghood.presenter.implement.BannerDataImpl;
import linhao.redridinghood.presenter.implement.FragmentDataImpl;
import linhao.redridinghood.ui.adapter.BannerAdapter;
import linhao.redridinghood.ui.adapter.GridViewAdapter;
import linhao.redridinghood.ui.adapter.TabFragmentPageAdapter;
import linhao.redridinghood.ui.fragment.AddEndFragment;
import linhao.redridinghood.ui.fragment.AddSerialFragment;
import linhao.redridinghood.ui.fragment.RecommendEndFragment;
import linhao.redridinghood.ui.fragment.RecommendSerialFragment;
import linhao.redridinghood.ui.listener.DrawerLayoutListener;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.ui.listener.MyPageChangeOnListener;
import linhao.redridinghood.ui.listener.NavigationOpenClickListener;
import linhao.redridinghood.ui.view.ActivityView;
import linhao.redridinghood.util.ConstantUtil;
import linhao.redridinghood.util.DisplayUtils;
import linhao.redridinghood.util.ExitClickUtil;
import linhao.redridinghood.util.ThemeUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivty implements ActivityView, SwipeRefreshLayout.OnRefreshListener, ItemOnClick {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_content)
    ViewPager vp_content;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.dot_indicator1)
    ImageView dotIndicator1;
    @BindView(R.id.dot_indicator2)
    ImageView dotIndicator2;
    @BindView(R.id.dot_indicator3)
    ImageView dotIndicator3;
    @BindView(R.id.dot_indicator4)
    ImageView dotIndicator4;
    @BindView(R.id.view_page)
    ViewPager viewPage;
    @BindView(R.id.main)
    CoordinatorLayout main;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.search)
    ImageView search;
    //导航栏
    @BindView(R.id.nav_adapt_status_bar)
    View navAdaptStatusBar;
    @BindView(R.id.tv_login_name)
    TextView tvLoginName;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.layout_info)
    LinearLayout layoutInfo;
    @BindView(R.id.btn_logout)
    TextView btnLogout;
    @BindView(R.id.btn_theme_dark)
    ImageView btnThemeDark;
    @BindViews({
            R.id.week_update, R.id.recent_update, R.id.hot_new_animate, R.id.comic_list, R.id.end_comic,
            R.id.news_list,
            R.id.ranking, R.id.topics_datail, R.id.about
    })
    List<CheckedTextView> checkedTextViewList;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.center_adapt_status_bar)
    View centerAdaptStatusBar;
    @BindView(R.id.img_nav_top_background)
    ImageView imgNavTopBackground;

    @Inject
    BannerDataImpl bannerDataImpl;
    @Inject
    FragmentDataImpl fragmentData;
    @Inject
    GridViewAdapter gridViewAdapter;
    @Inject
    BannerAdapter bannerAdapter;
    private static ClientApiManagerComponent clientApiManagerComponent;

    private int bannerPosition = 0;
    private ImageView[] indicator;

    private ArrayList<Fragment> fragmentArrayList;
    private List<String> titleList;
    private static List<Carousel> carouselListl;
    private boolean isUserTouched;
    private final int DEFAULT_BANNER_SIZE = 4;
    private int pageCurrentPosition;

    private DrawerLayoutListener drawerLayoutListener;
    // 是否启用夜间模式
    private boolean enableThemeDark;

    private AddEndFragment addEndFragment;
    private AddSerialFragment addSerialFragment;
    private RecommendEndFragment recommendEndFragment;
    private RecommendSerialFragment recommendSerialFragment;
    private MyPageChangeOnListener myPageChangeOnListener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        initToolBar();
        initCompoment();
        initView(bundle);
        initNavigation();
        loadData();
        initTimer();
        initEvent();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar_title.setText(R.string.title);
        toolbar.setLogo(R.mipmap.icon);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    //导航页
    private void initNavigation() {
        toolbar.setNavigationOnClickListener(new NavigationOpenClickListener(drawerLayout));
        drawerLayout.setDrawerShadow(R.drawable.navigation_drawer_shadow, GravityCompat.START);
        checkedTextViewList = new ArrayList<>();
        drawerLayoutListener = new DrawerLayoutListener(checkedTextViewList);
        drawerLayout.addDrawerListener(drawerLayoutListener);
    }

    //轮播时间
    private void initTimer() {
        Observable.interval(4, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (!isUserTouched) {
                            bannerPosition = (bannerPosition + 1) % DEFAULT_BANNER_SIZE;
                            if (viewPage != null) {
                                if (bannerPosition == 0) {
                                    viewPage.setCurrentItem(0, false);
                                } else {
                                    viewPage.setCurrentItem(bannerPosition);
                                }
                            }
                        }
                    }
                });
    }

    private void initCompoment() {

        clientApiManagerComponent = DaggerClientApiManagerComponent
                .builder()
                .clientApiManagerModule(new ClientApiManagerModule(this, getApplicationContext(), viewPage))
                .build();
        MainActivity.InstanceManager().inject(this);
    }

    public static ClientApiManagerComponent InstanceManager() {
        return clientApiManagerComponent;
    }

    private void initView(Bundle bundle) {
        DisplayUtils.adaptStatusBar(this, navAdaptStatusBar);
        DisplayUtils.adaptStatusBar(this, centerAdaptStatusBar);

        //夜间和白天两种模式
        btnThemeDark.setImageResource(enableThemeDark ? R.drawable.ic_wb_sunny_white_24dp : R.drawable.ic_brightness_3_white_24dp);
        btnThemeDark.setVisibility(enableThemeDark ? View.INVISIBLE : View.VISIBLE);

        titleList = new ArrayList<>();
        titleList.add(getResources().getString(R.string.add_serial));
        titleList.add(getResources().getString(R.string.add_end));
        titleList.add(getResources().getString(R.string.recommend_serial));
        titleList.add(getResources().getString(R.string.recommend_end));

        for (int i = 0; i < titleList.size(); i++) {
            tabs.addTab(tabs.newTab().setText(titleList.get(i)));
        }
        tabs.setTabTextColors(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.red));
        addEndFragment = AddEndFragment.Instance(MainActivity.this, bundle);
        addSerialFragment = AddSerialFragment.Instance(MainActivity.this, bundle);
        recommendEndFragment = RecommendEndFragment.Instance(MainActivity.this, bundle);
        recommendSerialFragment = RecommendSerialFragment.Instance(MainActivity.this, bundle);

        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(addEndFragment);
        fragmentArrayList.add(addSerialFragment);
        fragmentArrayList.add(recommendEndFragment);
        fragmentArrayList.add(recommendSerialFragment);

        TabFragmentPageAdapter adapter = new TabFragmentPageAdapter(getSupportFragmentManager(), fragmentArrayList, titleList);
        vp_content.setAdapter(adapter);
        vp_content.setCurrentItem(0);
        gridViewAdapter.setItemOnClick(this);
        myPageChangeOnListener = new MyPageChangeOnListener();
        myPageChangeOnListener.setAdapterType(ConstantUtil.GridViewAdapter);
        myPageChangeOnListener.setGridViewAdapter(gridViewAdapter);
        vp_content.addOnPageChangeListener(myPageChangeOnListener);

        tabs.setupWithViewPager(vp_content);
        tabs.setTabsFromPagerAdapter(adapter);

        indicator = new ImageView[]{dotIndicator1, dotIndicator2, dotIndicator3, dotIndicator4};
        loadData();
        carouselListl = bannerAdapter.getCarouselList();
        viewPage.setAdapter(bannerAdapter);
        viewPage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                    isUserTouched = true;
                } else if (action == MotionEvent.ACTION_UP) {
                    isUserTouched = false;
                }
                return false;
            }
        });
        swipeRefresh.setOnRefreshListener(this);
    }


    public void initEvent() {
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bannerPosition = position;
                setIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setIndicator(int position) {
        position %= DEFAULT_BANNER_SIZE;
        for (ImageView imageView : indicator) {
            imageView.setImageResource(R.drawable.dot_normal);
        }
        indicator[position].setImageResource(R.drawable.dot_focused);
        if (carouselListl == null) {
            carouselListl = bannerAdapter.getCarouselList();
        }
        title.setText(carouselListl.get(position).getImgTitle());
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        /**       if (SettingShared.isEnableThemeDark(this) != enableThemeDark) {
         ThemeUtils.notifyThemeApply(this, true);
         }
         */
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


    @Override
    public void onRefresh() {
        loadData();
    }

    //加载数据
    public void loadData() {
        bannerDataImpl.getData();
        initFragmentData();
    }

    public void initFragmentData() {
        pageCurrentPosition = myPageChangeOnListener.getCurrentPosition();
        fragmentData.getFragmentData();
        switch (pageCurrentPosition) {
            case ConstantUtil.AddEndFragment_Flag:
                addEndFragment.setRecommendList(gridViewAdapter.getAddRecommendList());
                break;
            case ConstantUtil.AddSerialFragment_Flag:
                addSerialFragment.setAddSerialFragment(gridViewAdapter.getAddRecommendList());
                break;
            case ConstantUtil.RecommendEndFragmentt_Flag:
                recommendEndFragment.setRecommendEndFragment(gridViewAdapter.getAddRecommendList());
                break;
            case ConstantUtil.RecommendSerialFragmentt_Flag:
                recommendSerialFragment.setRecommendSerialFragment(gridViewAdapter.getAddRecommendList());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!ExitClickUtil.isClickAgain()) {
            Snackbar.make(main, R.string.exit, Snackbar.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }


    @OnClick({R.id.week_update, R.id.recent_update, R.id.hot_new_animate, R.id.comic_list, R.id.end_comic, R.id.news_list, R.id.ranking, R.id.topics_datail, R.id.about})
    public void onNavigationMainItemClick(CheckedTextView view) {
        Intent intent = null;
        for (CheckedTextView checkedTextView : checkedTextViewList) {
            checkedTextView.setChecked(checkedTextView.getId() == view.getId());
        }
        switch (view.getId()) {
            case R.id.week_update:
                intent = new Intent(MainActivity.this, WeekUpdateActivity.class);
                break;
            case R.id.recent_update:
                intent = new Intent(MainActivity.this, RecentUpdateActivity.class);
                break;
            case R.id.ranking:
                intent = new Intent(MainActivity.this, RangkingActivity.class);
                break;
            case R.id.news_list:
                intent = new Intent(MainActivity.this, NewsActivity.class);
                break;
            case R.id.hot_new_animate:
                intent = new Intent(MainActivity.this, HotNewActivity.class);
                break;
            case R.id.comic_list:
                intent = new Intent(MainActivity.this, ComicListActivity.class);
                break;
            case R.id.end_comic:
                intent = new Intent(MainActivity.this, EndComicActivity.class);
                break;
            case R.id.topics_datail:
                intent = new Intent(MainActivity.this, TopicActivity.class);
                break;
        }
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    @OnClick(R.id.btn_theme_dark)
    public void onBtnThemeDark() {
        SettingShared.setEnableThemeDark(this, !enableThemeDark);
        ThemeUtils.notifyThemeApply(this, false);
    }

    @Override
    public void onClick(View view, String url) {
        Intent intent = new Intent(MainActivity.this, ComicDetailActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
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
        //避免造成内存泄露
        addEndFragment = null;
        recommendEndFragment = null;
        addSerialFragment = null;
        recommendSerialFragment = null;
    }
}
