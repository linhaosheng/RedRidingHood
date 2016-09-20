package linhao.redridinghood.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.ui.activity.ComicDetailActivity;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.GridViewAdapter;
import linhao.redridinghood.ui.adapter.HotNewAdapter;
import linhao.redridinghood.ui.adapter.TabFragmentPageAdapter;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.ui.listener.MyPageChangeOnListener;
import linhao.redridinghood.util.ConstantUtil;

/**
 * Created by linhao on 2016/9/10.
 */
public class HotNewSecondFragemnt extends BaseFragment implements ItemOnClick{

    @BindView(R.id.month_tablayout)
    TabLayout monthTablayout;
    @BindView(R.id.month_viewPage)
    ViewPager monthViewPage;
    private HotNewItemFragment itemFragment;
    private Bundle bundle;
    private MyPageChangeOnListener pageChangeOnListener;
    @Inject
    HotNewAdapter hotNewAdapter;
    private List<String> titleList;
    private ArrayList<Fragment> contentFragmentList;
    private static HotNewSecondFragemnt secondFragemnt;
    private int currentPosition=0;
    public static HotNewSecondFragemnt Instance() {
        if (secondFragemnt == null) {
            secondFragemnt = new HotNewSecondFragemnt();
        }
        return secondFragemnt;
    }

    @Override
    public int getLayoutId() {
        return R.layout.hot_new_content;
    }

    @Override
    public void afterCreateView(View view) {
        initView();
    }

    private void initView() {
        MainActivity.InstanceManager().inject(this);
        hotNewAdapter.setItemOnClick(this);
        titleList = new ArrayList<>();
        titleList.add(getContext().getResources().getString(R.string.october));
        titleList.add(getContext().getResources().getString(R.string.july));
        titleList.add(getContext().getResources().getString(R.string.april));
        titleList.add(getContext().getResources().getString(R.string.january));
        contentFragmentList = new ArrayList<>();
        monthTablayout.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.white), ContextCompat.getColor(getActivity(), R.color.red));
        for (int i = 0; i < titleList.size(); i++) {
            itemFragment = new HotNewItemFragment();
            bundle = new Bundle();
            itemFragment.setArguments(bundle);
            contentFragmentList.add(itemFragment);
            monthTablayout.addTab(monthTablayout.newTab().setText(titleList.get(i)));
        }

        TabFragmentPageAdapter pageAdapter = new TabFragmentPageAdapter(getFragmentManager(), contentFragmentList, titleList);
        monthViewPage.setAdapter(pageAdapter);
        monthViewPage.setCurrentItem(0);
        pageChangeOnListener = new MyPageChangeOnListener();
        pageChangeOnListener.setYearType(ConstantUtil.Second_Year_Fragment_Flag);
        pageChangeOnListener.setAdapterType(ConstantUtil.HotNewAdapter);
        pageChangeOnListener.setHotNewAdapter(hotNewAdapter);
        currentPosition = pageChangeOnListener.getCurrentPosition();
        bundle.putInt("currentPosition",currentPosition);
        bundle.putInt("currentYear",ConstantUtil.Second_Year_Fragment_Flag);
        hotNewAdapter.getList().clear();
        hotNewAdapter.setType(ConstantUtil.Second_Year_Fragment_Flag,currentPosition);
        hotNewAdapter.getList().addAll(hotNewAdapter.getNewItemContentList());
        hotNewAdapter.notifyDataSetChanged();
        itemFragment.setArguments(bundle);
        monthViewPage.addOnPageChangeListener(pageChangeOnListener);
        monthTablayout.setupWithViewPager(monthViewPage);
        monthTablayout.setTabsFromPagerAdapter(pageAdapter);
    }

    @Override
    public void onClick(View view, String url) {
        Intent intent=new Intent(getActivity(), ComicDetailActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("HotNewSecondFragemnt");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("HotNewSecondFragemnt");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
        hotNewAdapter = null;
    }

}
