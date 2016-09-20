package linhao.redridinghood.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.WeekUpdateAdapter;
import linhao.redridinghood.widget.DividerItemDecoration;

/**
 * Created by linhao on 2016/8/27.
 */
public class WeekUpdateFragment extends BaseFragment {


    @BindView(R.id.week_update_content)
    RecyclerView weekUpdateContent;

    //   @Inject
    WeekUpdateAdapter weekUpdateAdapter;
    private final static int SPAN_COUNT = 1;
    int currentPosition = 0;

    @Override
    public int getLayoutId() {
        return R.layout.week_update_content;
    }

    @Override
    public void afterCreateView(View view) {
        initView();
        loadData();
    }

    private void initView() {
        MainActivity.InstanceManager().inject(this);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        weekUpdateContent.setLayoutManager(layoutManager);
        weekUpdateContent.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        weekUpdateContent.setAdapter(weekUpdateAdapter);
    }

    private void loadData() {
        currentPosition = this.getArguments().getInt("currentPosition");
        weekUpdateAdapter.setWeek_Data_Type(currentPosition);
        weekUpdateAdapter.notifyDataSetChanged();
    }

    public void setWeekUpdateAdapter(WeekUpdateAdapter weekUpdateAdapter) {
        this.weekUpdateAdapter = weekUpdateAdapter;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("WeekUpdateFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("WeekUpdateFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
        weekUpdateAdapter = null;
    }

}
