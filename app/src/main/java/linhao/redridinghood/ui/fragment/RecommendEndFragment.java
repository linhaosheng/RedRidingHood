package linhao.redridinghood.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.model.entity.AddRecommend;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.GridViewAdapter;
import linhao.redridinghood.util.ConstantUtil;

/**
 * Created by linhao on 2016/8/14.
 */
public class RecommendEndFragment extends BaseFragment {

    @Inject
    GridViewAdapter gridViewAdapter;
    private static Context context;
    @BindView(R.id.recycle_content)
    RecyclerView recycleContent;
    private List<AddRecommend> recommendList;

    public static RecommendEndFragment Instance(Context context1, Bundle bundle){
        context=context1;
        RecommendEndFragment recommendEndFragment=new RecommendEndFragment();
        recommendEndFragment.setArguments(bundle);
        return recommendEndFragment;
    }

    public void setRecommendEndFragment(List<AddRecommend> recommendList) {
        this.recommendList = recommendList;
        gridViewAdapter.getAddRecommendList().clear();
        gridViewAdapter.getAddRecommendList().addAll(recommendList);
        gridViewAdapter.setAddRecommendList(recommendList);
        gridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    public void afterCreateView(View view) {
        MainActivity.InstanceManager().inject(this);
        gridViewAdapter.setFragment_flag(ConstantUtil.RecommendEndFragmentt_Flag);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        recycleContent.setLayoutManager(layoutManager);
        recycleContent.setAdapter(gridViewAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("RecommendEndFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("RecommendEndFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

}
