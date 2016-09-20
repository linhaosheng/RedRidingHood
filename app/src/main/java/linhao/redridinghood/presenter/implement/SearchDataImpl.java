package linhao.redridinghood.presenter.implement;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import javax.inject.Inject;

import linhao.redridinghood.R;
import linhao.redridinghood.model.entity.Search;
import linhao.redridinghood.model.service.ClientApiManager;
import linhao.redridinghood.presenter.contract.SearchData;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.SearchAdapter;
import linhao.redridinghood.ui.view.ActivityView;
import linhao.redridinghood.ui.view.LoadMoreView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by linhao on 2016/9/18.
 */
public class SearchDataImpl implements SearchData {

    @Inject
    ClientApiManager clientApiManager;
    @Inject
    ActivityView activityView;
    @Inject
    SearchAdapter searchAdapter;
    private TextView toolTtitle;
    private LoadMoreView loadMoreView;

    public SearchDataImpl() {
        MainActivity.InstanceManager().inject(this);
    }

    public void setLoadMoreView(LoadMoreView loadMoreView) {
        this.loadMoreView = loadMoreView;
    }

    public void setToolTtitle(TextView toolTtitle) {
        this.toolTtitle = toolTtitle;
    }

    @Override
    public void getData(final String key, int page, final boolean isLoadMore) {
        Observable<Search> search = clientApiManager.getSearch(key, page);
        search.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (isLoadMore) {
                            loadMoreView.showMoreProgress();
                        } else {
                            toolTtitle.setText("正在搜索" + key);
                            activityView.showProgress();
                        }
                    }
                }).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                if (isLoadMore) {
                    loadMoreView.hideMoreProgress();
                    if (searchAdapter.getList() != null) {
                        if (searchAdapter.getList().isEmpty()) {
                            loadMoreView.showLoadFail();
                        }
                    }
                } else {
                    activityView.hideProgress();
                    toolTtitle.setText("搜索"+ key);
                    if (searchAdapter.getList() != null) {
                        if (searchAdapter.getList().isEmpty()) {
                            activityView.loadFailView();
                        }
                    }
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Search>() {
                    @Override
                    public void call(Search search) {
                        if (!search.getListItems().isEmpty()) {
                            searchAdapter.settList(search.getListItems());
                            searchAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (isLoadMore) {
                            loadMoreView.hideMoreProgress();
                            loadMoreView.showLoadFail();
                        } else {
                            activityView.hideProgress();
                            activityView.loadFailView();
                        }
                    }
                });
    }
}
