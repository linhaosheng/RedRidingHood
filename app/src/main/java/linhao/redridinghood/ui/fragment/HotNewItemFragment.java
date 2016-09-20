package linhao.redridinghood.ui.fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;
import butterknife.BindView;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.HotNewAdapter;

/**
 * Created by linhao on 2016/9/10.
 */
public class HotNewItemFragment extends BaseFragment {
    @BindView(R.id.item_content_listView)
    RecyclerView itemContentListView;
    @Inject
    HotNewAdapter hotNewAdapter;
    private int currentPosition;
    private int currentYear;
    private boolean isVisibleToUser;
    @Override
    public int getLayoutId() {
        return R.layout.hot_new_item_content;
    }

    @Override
    public void afterCreateView(View view) {
    initView();
    }

    private void initView() {
        MainActivity.InstanceManager().inject(this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        itemContentListView.setLayoutManager(layoutManager);
        itemContentListView.setAdapter(hotNewAdapter);
        currentPosition=this.getArguments().getInt("currentPosition");
        currentYear=this.getArguments().getInt("currentYear");
        hotNewAdapter.setType(currentYear,currentPosition);
        hotNewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        MainActivity.InstanceManager().inject(this);
        if (isVisibleToUser){
            hotNewAdapter.setType(currentYear,currentPosition);
            hotNewAdapter.notifyDataSetChanged();
        }
        super.onStart();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser=isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("HotNewItemFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("HotNewItemFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
        hotNewAdapter = null;
    }

}
