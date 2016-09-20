package linhao.redridinghood.presenter.implement;

import javax.inject.Inject;

import linhao.redridinghood.model.entity.News;
import linhao.redridinghood.model.service.ClientApiManager;
import linhao.redridinghood.presenter.contract.NewsData;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.NewsAdapter;
import linhao.redridinghood.ui.view.ActivityView;
import linhao.redridinghood.ui.view.LoadMoreView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by linhao on 2016/9/3.
 */
public class NewsDataImpl implements NewsData {

    @Inject
    ActivityView activityView;
    @Inject
    NewsAdapter newsAdapter;
    @Inject
    ClientApiManager clientApiManager;

    private LoadMoreView loadMoreView;

    public NewsDataImpl() {
        MainActivity.InstanceManager().inject(this);
    }

    public void setLoadMoreView(LoadMoreView loadMoreView) {
        this.loadMoreView = loadMoreView;
    }

    @Override
    public void getNewData() {

        Observable<News> news = clientApiManager.getNews(1);
        news.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        activityView.showProgress();
                    }
                }).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                activityView.hideProgress();
                if (newsAdapter.getList() != null) {
                    if (newsAdapter.getList().isEmpty()) {
                        activityView.loadFailView();
                    }
                } else {
                    activityView.loadFailView();
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                //  .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        if (news != null) {
                            //          adapter.getList().clear();
                            newsAdapter.settList(news.getNewListList());
                            //        newsAdapter.getList().addAll(news.getNewListList());
                            newsAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        activityView.hideProgress();
                        activityView.loadFailView();
                    }
                });
    }

    @Override
    public void pageLoadMore(int page) {
        if (page < 1) {
            page = 1;
        }
        Observable<News> news = clientApiManager.getNews(page);
        news.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadMoreView.showMoreProgress();
                    }
                }).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                loadMoreView.hideMoreProgress();
                if (newsAdapter.getList() != null) {
                    if (newsAdapter.getList().isEmpty()) {
                        loadMoreView.showLoadFail();
                    }
                } else {
                    loadMoreView.showLoadFail();
                }
            }
        }).subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        if (news != null) {
                            newsAdapter.getList().clear();
                            newsAdapter.settList(news.getNewListList());
                            //        newsAdapter.getList().addAll(news.getNewListList());
                            newsAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadMoreView.hideMoreProgress();
                        loadMoreView.showLoadFail();
                    }
                });
    }
}
