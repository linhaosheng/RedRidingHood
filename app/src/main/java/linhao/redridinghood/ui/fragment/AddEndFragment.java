package linhao.redridinghood.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.model.entity.AddRecommend;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.GridViewAdapter;
import linhao.redridinghood.util.ConstantUtil;

/**
 * Created by linhao on 2016/8/14.
 */
public class AddEndFragment extends BaseFragment {

    @Inject
    GridViewAdapter gridViewAdapter;
    private static Context context;
    @BindView(R.id.recycle_content)
    RecyclerView recycleContent;
    private List<AddRecommend> recommendList;
    private boolean isVisibleToUser;

    public static AddEndFragment Instance(Context context1, Bundle bundle) {
        context = context1;
        AddEndFragment addEndFragment = new AddEndFragment();
        addEndFragment.setArguments(bundle);
        return addEndFragment;
    }

    public void setRecommendList(List<AddRecommend> recommendList) {
        this.recommendList = recommendList;
    }

    public void setAddRecommendListUpdate() {
        if (gridViewAdapter.getAddRecommendList() != null && recommendList != null) {
            gridViewAdapter.getAddRecommendList().clear();
            gridViewAdapter.getAddRecommendList().addAll(recommendList);
            gridViewAdapter.setAddRecommendList(recommendList);
            gridViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity.InstanceManager().inject(this);
        if (isVisibleToUser) {
            setAddRecommendListUpdate();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    public void afterCreateView(View view) {
        MainActivity.InstanceManager().inject(this);
        gridViewAdapter.setFragment_flag(ConstantUtil.AddEndFragment_Flag);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        recycleContent.setLayoutManager(layoutManager);
        recycleContent.setAdapter(gridViewAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("AddEndFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("AddEndFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
