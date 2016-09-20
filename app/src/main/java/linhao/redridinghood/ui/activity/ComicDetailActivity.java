package linhao.redridinghood.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import linhao.redridinghood.R;
import linhao.redridinghood.model.entity.Detail;
import linhao.redridinghood.presenter.implement.ComicDetailDataImpl;
import linhao.redridinghood.ui.adapter.DownLoadAdapter;
import linhao.redridinghood.ui.adapter.RecommandAdapter;
import linhao.redridinghood.ui.adapter.TabFragmentPageAdapter;
import linhao.redridinghood.ui.fragment.DownLoadFragment;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.ui.listener.MyPageChangeOnListener;
import linhao.redridinghood.ui.listener.NavigationFinishClickListener;
import linhao.redridinghood.ui.view.ActivityView;
import linhao.redridinghood.util.ConstantUtil;
import linhao.redridinghood.util.GlideUtil;
import linhao.redridinghood.widget.DividerItemDecoration;

/**
 * Created by linhao on 2016/9/15.
 */
public class ComicDetailActivity extends BaseActivty implements SwipeRefreshLayout.OnRefreshListener, ActivityView,ItemOnClick{
    @BindView(R.id.content_title)
    TextView contentTitle;
    @BindView(R.id.content_img)
    ImageView contentImg;
    @BindView(R.id.content_state)
    TextView contentState;
    @BindView(R.id.click_volumn)
    TextView clickVolumn;
    @BindView(R.id.content_type)
    TextView contentType;
    @BindView(R.id.content_zone)
    TextView contentZone;
    @BindView(R.id.content_language)
    TextView contentLanguage;
    @BindView(R.id.content_year)
    TextView contentYear;
    @BindView(R.id.content_last_update)
    TextView contentLastUpdate;
    @BindView(R.id.recent_update_tabs)
    TabLayout recentUpdateTabs;
    @BindView(R.id.recent_update_vp)
    ViewPager recentUpdateVp;
    @BindView(R.id.recommand_recyler)
    RecyclerView recommandRecyler;
    @BindView(R.id.detail_content)
    WebView detailContent;
    @BindView(R.id.main)
    LinearLayout main;
    @BindView(R.id.detail_refresh)
    SwipeRefreshLayout detailRefresh;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.state)
    TextView state;
    @BindView(R.id.volumn)
    TextView volumn;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.language)
    TextView language;
    @BindView(R.id.year)
    TextView year;
    @BindView(R.id.last_update)
    TextView lastUpdate;
    @BindView(R.id.intro_img)
    ImageView introImg;
    @BindView(R.id.linear1)
    LinearLayout linearLayout;
    @Inject
    ComicDetailDataImpl comicDetailDataImpl;
    @Inject
    DownLoadAdapter downLoadAdapter;
    @Inject
    RecommandAdapter recommandAdapter;
    private String url;
    private List<String> downloadTitles;
    private ArrayList<Fragment> downLoadFragments;
    private Bundle bundle;
    private MyPageChangeOnListener myPageChangeOnListener;
    private final static int SPAN_COUNT = 1;
    private int currentPosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.recent_update_content;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        initToolBar();
        initView();
        initWebView();
        loadData();
    }

    private void loadData() {
        comicDetailDataImpl.getData(url);
    }

    private void initToolBar() {
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.tool_color));
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(ComicDetailActivity.this, R.drawable.back));
        search.setVisibility(View.GONE);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
    }

    private void initView() {
        url = getIntent().getStringExtra("url");
        MainActivity.InstanceManager().inject(this);
        detailRefresh.setColorSchemeResources(R.color.red_light, R.color.green_light, R.color.blue_light, R.color.orange_light);
        detailRefresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        detailRefresh.setOnRefreshListener(this);
        comicDetailDataImpl.setComicDetailActivity(this);
        downloadTitles = new ArrayList<>();
        downloadTitles.add(getResources().getString(R.string.ftp));
        downloadTitles.add(getResources().getString(R.string.baidu));
        downloadTitles.add(getResources().getString(R.string.magnetic));
        downLoadFragments = new ArrayList<>();
        for (int i = 0; i < downloadTitles.size(); i++) {
            DownLoadFragment downLoadFragment = new DownLoadFragment();
            bundle = new Bundle();
            downLoadFragment.setArguments(bundle);
            downLoadFragments.add(downLoadFragment);
            recentUpdateTabs.addTab(recentUpdateTabs.newTab().setText(downloadTitles.get(i)));
        }
        recentUpdateTabs.setTabTextColors(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.red));
        TabFragmentPageAdapter tabPageAdapter = new TabFragmentPageAdapter(getSupportFragmentManager(), downLoadFragments, downloadTitles);
        recentUpdateVp.setAdapter(tabPageAdapter);
        recentUpdateVp.setCurrentItem(0);
        myPageChangeOnListener = new MyPageChangeOnListener();
        myPageChangeOnListener.setDownLoadAdapter(downLoadAdapter);
        myPageChangeOnListener.setAdapterType(ConstantUtil.DownLoadAdapter);
        currentPosition = myPageChangeOnListener.getCurrentPosition();
        recentUpdateVp.addOnPageChangeListener(myPageChangeOnListener);
        recentUpdateTabs.setupWithViewPager(recentUpdateVp);
        recentUpdateTabs.setTabsFromPagerAdapter(tabPageAdapter);

        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        recommandRecyler.setLayoutManager(manager);
        recommandRecyler.addItemDecoration(new DividerItemDecoration(ComicDetailActivity.this, DividerItemDecoration.VERTICAL_LIST));
        recommandRecyler.setAdapter(recommandAdapter);
        recommandAdapter.setItemOnClick(this);
    }

    private void initWebView() {
        WebSettings settings = detailContent.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDisplayZoomControls(true);
        settings.setJavaScriptEnabled(true);
        detailContent.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int scale = dm.densityDpi;
        if (scale == 240) {
            settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (scale == 160) {
            settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else {
            settings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }
        detailContent.setWebChromeClient(new WebChromeClient());
    }


    @Override
    public void onRefresh() {
        loadData();
    }

    public void setContent(Detail detail) {
        GlideUtil.LoadImg(this, contentImg, detail.getImg());
        toolbarTitle.setText(detail.getName());
        contentTitle.setText(detail.getName());
        for (int i = 0 ; i<detail.getOptionsList().size();i++) {
            String key=detail.getOptionsList().get(i).getKey();
            if (key.equals(ConstantUtil.Comic_Content_State)){
                contentState.setText(detail.getOptionsList().get(i).getValue());
            }
            if (key.equals(ConstantUtil.Comic_Content_Volumn)){
                clickVolumn.setText(detail.getOptionsList().get(i).getValue());
            }
            if (key.equals(ConstantUtil.Comic_Content_Type)){
                contentType.setText(detail.getOptionsList().get(i).getValue());
            }
            if (key.equals(ConstantUtil.Comic_Content_Zone)){
                contentZone.setText(detail.getOptionsList().get(i).getValue());
            }
            if (key.equals(ConstantUtil.Comic_Content_Language)){
                contentLanguage.setText(detail.getOptionsList().get(i).getValue());
            }
            if (key.equals(ConstantUtil.Comic_Content_Year)){
                contentYear.setText(detail.getOptionsList().get(i).getValue());
            }
            if (key.equals(ConstantUtil.Comic_Content_Update)){
                contentLastUpdate.setText(detail.getOptionsList().get(i).getValue());
            }
        }
        downLoadAdapter.setViewPager(recentUpdateVp);
        downLoadAdapter.setDownloadList(detail.getDownloadList());
        downLoadAdapter.changeData(currentPosition);
        downLoadAdapter.notifyDataSetChanged();
        recommandAdapter.settList(detail.getEditRecommendList());
        recommandAdapter.notifyDataSetChanged();
        if (!detail.getIntro().contains(".jpg")){
            introImg.setVisibility(View.VISIBLE);
            GlideUtil.LoadImg(this,introImg,detail.getImg());
        }
        detailContent.loadData(detail.getIntro(), "text/html; charset=UTF-8", null);
    }


    @Override
    public void showProgress() {
        detailRefresh.post(new Runnable() {
            @Override
            public void run() {
                detailRefresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        detailRefresh.post(new Runnable() {
            @Override
            public void run() {
                detailRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void loadFailView() {
        Snackbar.make(main, R.string.loadfail, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view, String url){
        Intent intent=new Intent(ComicDetailActivity.this,ComicDetailActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
        finish();
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
    protected void onDestroy() {
        super.onDestroy();
        downLoadAdapter = null;
        recommandAdapter = null;
    }
}
