package linhao.redridinghood.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.model.entity.Pickers;
import linhao.redridinghood.presenter.implement.ComicListDataImpl;
import linhao.redridinghood.ui.adapter.ComicListAdapter;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.ui.listener.MyPageChangeOnListener;
import linhao.redridinghood.ui.listener.NavigationFinishClickListener;
import linhao.redridinghood.ui.listener.PageLoadMoreListener;
import linhao.redridinghood.ui.view.ActivityView;
import linhao.redridinghood.ui.view.LoadMoreView;
import linhao.redridinghood.util.ConstantUtil;
import linhao.redridinghood.widget.DividerItemDecoration;
import linhao.redridinghood.widget.PickerScrollView;

/**
 * Created by linhao on 2016/9/15.
 */
public class EndComicActivity extends BaseActivty implements ActivityView, SwipeRefreshLayout.OnRefreshListener, LoadMoreView, ItemOnClick {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.comic_tab)
    TabLayout comicTab;
    @BindView(R.id.comic_list)
    RecyclerView comicList;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.certain)
    Button certain;
    @BindView(R.id.pickerscrlllview)
    PickerScrollView pickerscrlllview;  // 滚动选择器
    @BindView(R.id.picker_rel)
    RelativeLayout pickerRel;      // 选择器布局
    @BindView(R.id.comic_refresh)
    SwipeRefreshLayout comic_refresh;
    @Inject
    ComicListDataImpl comicListDataImpl;
    @Inject
    ComicListAdapter comicListAdapter;
    @BindView(R.id.main)
    LinearLayout main;
    private static final int SPAN_COUNT = 1;  //列数
    private List<String> titleList;
    private MyPageChangeOnListener pageChangeListener;
    private TextView loadMoreTitle;
    private ProgressWheel progress;     //加载更多的进度条
    private List<Pickers> list; // 滚动选择器数据
    private String[] id;
    private String[] name;
    private int ver;
    private String letter;
    private int sort;
    private int tab;
    private int page;
    private Pickers selectPickers;

    @Override
    protected int getLayoutId() {
        return R.layout.comic_list;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        initToolBar();
        initView();
        initLinstener();
        initData(0);
        loadData();
    }

    private void loadData() {
        comicListDataImpl.getData(ver, letter, sort, tab, page);
    }

    @OnClick({R.id.cancel, R.id.certain})
    public void onClkic(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                pickerRel.setVisibility(View.GONE);
                break;
            case R.id.certain:
                if (selectPickers == null) {
                    selectPickers = list.get(0);
                }
                int pageId = selectPickers.getPageId();
                switch (pageId) {
                    case ConstantUtil.Comic_List_Edition:
                        ver = Integer.parseInt(selectPickers.getShowId());
                        break;
                    case ConstantUtil.Comic_List_Letter:
                        letter = selectPickers.getShowId();
                        break;
                    case ConstantUtil.Comic_List_Sort:
                        sort = Integer.parseInt(selectPickers.getShowId());
                        break;
                }
                comicListAdapter.getList().clear();
                loadData();
                selectPickers = null;
                pickerRel.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 设置监听事件
     */
    private void initLinstener() {
        pickerscrlllview.setOnSelectListener(pickerListener);
    }

    // 滚动选择器选中事件
    PickerScrollView.onSelectListener pickerListener = new PickerScrollView.onSelectListener() {

        @Override
        public void onSelect(Pickers pickers) {
            selectPickers = pickers;
        }
    };

    /**
     * 初始化数据
     */
    private void initData(int pageId) {
        ver = 0;
        letter = " ";
        sort = 1;
        tab = 18;
        page = 1;
        list = new ArrayList<Pickers>();
        switch (pageId) {
            case ConstantUtil.Comic_List_Edition:
                id = new String[]{" ", "1", "2", "3", "4", "5", "6", "7", "100"};
                name = new String[]{"全部", "TV番组", "OVA", "OAD", "剧场版", "特别版", "真人版", "动画版", "其他"};
                for (int i = 0; i < name.length; i++) {
                    list.add(new Pickers(name[i], id[i], pageId));
                }
                break;
            case ConstantUtil.Comic_List_Letter:
                list.add(new Pickers("全部", " ", pageId));
                for (char i = 'A'; i < 'Z'; i++) {
                    list.add(new Pickers(String.valueOf(i), String.valueOf(i), pageId));
                }
                break;
            case ConstantUtil.Comic_List_Sort:
                id = new String[]{"0", "1"};
                name = new String[]{"最新更新", "热门播放"};
                for (int i = 0; i < name.length; i++) {
                    list.add(new Pickers(name[i], id[i], pageId));
                }
                break;
        }
        // 设置数据，默认选择第一条
        pickerscrlllview.setData(list);
        pickerscrlllview.setSelected(0);
    }


    private void initView() {
        MainActivity.InstanceManager().inject(this);
        comicListAdapter.setItemOnClick(this);
        comic_refresh.setColorSchemeResources(R.color.red_light, R.color.green_light, R.color.blue_light, R.color.orange_light);
        comic_refresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        comic_refresh.setOnRefreshListener(this);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        comicList.setLayoutManager(layoutManager);
        comicList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        comicListDataImpl.setLoadMoreView(this);
        comicListAdapter.setLoadMoreListener(new PageLoadMoreListener() {
            @Override
            public void loadMore(TextView text, ProgressWheel progressWheel) {
                loadMoreTitle = text;
                progress = progressWheel;
                page++;
                comicListDataImpl.getLoadMoreData(ver, letter, sort, tab, page);

            }
        });
        comicList.setAdapter(comicListAdapter);
        titleList = new ArrayList<>();
        titleList.add(this.getResources().getString(R.string.edition));
        titleList.add(this.getResources().getString(R.string.letter));
        titleList.add(this.getResources().getString(R.string.sort));
        pageChangeListener = new MyPageChangeOnListener();
        for (int i = 0; i < titleList.size(); i++) {
            comicTab.addTab(comicTab.newTab().setCustomView(getTabView(i)));
        }
        comicTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabSelectChange(tab);
                initData(comicTab.getSelectedTabPosition());
                pickerRel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabUnSelectChange(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void tabUnSelectChange(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView title = (TextView) view.findViewById(R.id.title);
        ImageView image = (ImageView) view.findViewById(R.id.icon);
        title.setTextColor(ContextCompat.getColor(this, R.color.white));
        image.setImageResource(R.drawable.white_down);
    }

    private void tabSelectChange(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView title = (TextView) view.findViewById(R.id.title);
        ImageView image = (ImageView) view.findViewById(R.id.icon);
        title.setTextColor(ContextCompat.getColor(this, R.color.red));
        image.setImageResource(R.drawable.red_down);
    }

    private View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        ImageView image = (ImageView) view.findViewById(R.id.icon);
        if (position == 0) {
            image.setImageResource(R.drawable.red_down);
        } else {
            image.setImageResource(R.drawable.white_down);
        }
        title.setText(titleList.get(position));
        return view;
    }

    private void initToolBar() {
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.tool_color));
        toolbar.setTitle("");
        toolbarTitle.setText(R.string.end_comic);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(EndComicActivity.this, R.drawable.back));
        search.setVisibility(View.GONE);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
    }


    @Override
    public void showProgress() {
        comic_refresh.post(new Runnable() {
            @Override
            public void run() {
                comic_refresh.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        comic_refresh.post(new Runnable() {
            @Override
            public void run() {
                comic_refresh.setRefreshing(false);
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
    public void onClick(View view, String url) {
        Intent intent = new Intent(EndComicActivity.this, ComicDetailActivity.class);
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
        comicListAdapter = null;
    }
}
