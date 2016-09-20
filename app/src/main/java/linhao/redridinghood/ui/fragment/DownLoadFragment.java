package linhao.redridinghood.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.model.entity.Detail;
import linhao.redridinghood.ui.activity.DownLoadActivity;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.DownLoadAdapter;
import linhao.redridinghood.ui.listener.ItemOnClick;
import linhao.redridinghood.widget.DividerItemDecoration;

/**
 * Created by linhao on 2016/9/16.
 */
public class DownLoadFragment extends BaseFragment implements ItemOnClick {

    @Inject
    DownLoadAdapter downLoadAdapter;
    @BindView(R.id.download_list)
    RecyclerView downloadList;
    private static final int SPAN_COUNT = 1;  //列数

    @Override
    public int getLayoutId() {
        return R.layout.download_list;
    }

    @Override
    public void afterCreateView(View view) {
        MainActivity.InstanceManager().inject(this);
        downLoadAdapter.setItemOnClick(this);
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        downloadList.setLayoutManager(manager);
        downloadList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        downloadList.setAdapter(downLoadAdapter);
    }

    @Override
    public void onClick(View view, String url) {
        Intent intent = null;
        if (url.indexOf("http") > 0) {
            intent = new Intent(getActivity(), DownLoadActivity.class);
            intent.putExtra("url", url);
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addCategory("android.intent.category.DEFAULT");
        }
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("DownLoadFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("DownLoadFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

}
