package linhao.redridinghood.presenter.implement;

import javax.inject.Inject;

import linhao.redridinghood.model.entity.TopicsDetail;
import linhao.redridinghood.model.service.ClientApiManager;
import linhao.redridinghood.presenter.contract.TopicDetailData;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.TopicDetailAdapter;
import linhao.redridinghood.ui.view.ActivityView;
import linhao.redridinghood.ui.view.LoadMoreView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by linhao on 2016/9/15.
 */
public class TopicDetailDataImpl implements TopicDetailData {

    @Inject
    ClientApiManager clientApiManager;
    @Inject
    ActivityView activityView;
    @Inject
    TopicDetailAdapter topicDetailAdapter;

    private LoadMoreView loadMoreView;

    public TopicDetailDataImpl() {
        MainActivity.InstanceManager().inject(this);
    }

    public void setLoadMoreView(LoadMoreView loadMoreView) {
        this.loadMoreView = loadMoreView;
    }


    @Override
    public void getData(String url, int page, final boolean isLoadMore) {
        Observable<TopicsDetail> topicsDetail = clientApiManager.getTopicsDetail(url, page);
        topicsDetail.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (isLoadMore) {
                            loadMoreView.showMoreProgress();
                        } else {
                            activityView.showProgress();
                        }
                    }
                }).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                if (topicDetailAdapter.getList() != null) {
                    if (isLoadMore) {
                        if (topicDetailAdapter.getList().isEmpty()) {
                            loadMoreView.showLoadFail();
                        } else {
                            activityView.loadFailView();
                        }
                    }
                }
                if (isLoadMore) {
                    loadMoreView.hideMoreProgress();
                } else {
                    activityView.hideProgress();
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TopicsDetail>() {
                    @Override
                    public void call(TopicsDetail topicsDetail) {
                         topicDetailAdapter.settList(topicsDetail.getDetailListList());
                        topicDetailAdapter.notifyDataSetChanged();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("topicDetail-----"+throwable.getMessage());
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
