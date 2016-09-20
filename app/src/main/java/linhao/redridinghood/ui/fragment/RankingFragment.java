package linhao.redridinghood.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.model.entity.Ranking;
import linhao.redridinghood.ui.activity.ComicDetailActivity;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.RankingAdapter;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.util.ConstantUtil;
import linhao.redridinghood.widget.DividerItemDecoration;

/**
 * Created by linhao on 2016/9/2.
 */
public class RankingFragment extends BaseFragment implements ItemOnClick {


    private int currentFragmentItem;
    private RankingFragment rankingFragment;
    private Ranking.ListData listData;
    public static final int SPAN_COUNT = 1;  //列数

    @BindView(R.id.ranking_recycle)
    RecyclerView rankingRecycle;

    @Inject
    RankingAdapter rankingAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.rangking_item;
    }

    @Override
    public void afterCreateView(View view) {
        MainActivity.InstanceManager().inject(this);
        rankingAdapter.setItemOnClick(this);
        initView();
        loadData();
    }

    private void loadData() {
        currentFragmentItem = this.getArguments().getInt("position");
        //   listData= (Ranking.ListData)this.getArguments().getSerializable("listData");
        rankingAdapter.setRanking_Flag(currentFragmentItem);
    }

    private void initView() {
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        rankingRecycle.setLayoutManager(layoutManager);
        rankingRecycle.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        rankingRecycle.setAdapter(rankingAdapter);
    }

    @Override
    public void onClick(View view, String url) {
        Intent intent = new Intent(getActivity(), ComicDetailActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("RankingFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("RankingFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
        rankingAdapter = null;
    }

}
