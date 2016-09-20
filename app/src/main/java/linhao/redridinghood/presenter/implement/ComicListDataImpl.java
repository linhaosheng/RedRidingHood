package linhao.redridinghood.presenter.implement;

import javax.inject.Inject;

import linhao.redridinghood.model.entity.ComicList;
import linhao.redridinghood.model.service.ClientApiManager;
import linhao.redridinghood.presenter.contract.ComicListData;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.ComicListAdapter;
import linhao.redridinghood.ui.view.ActivityView;
import linhao.redridinghood.ui.view.LoadMoreView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by linhao on 2016/9/11.
 */
public class ComicListDataImpl implements ComicListData {

    @Inject
    ActivityView activityView;
    @Inject
    ClientApiManager clientApiManager;
    @Inject
    ComicListAdapter comicListAdapter;
    private LoadMoreView loadMoreView;
    public ComicListDataImpl(){
        MainActivity.InstanceManager().inject(this);
    }
    public void setLoadMoreView(LoadMoreView loadMoreView){
        this.loadMoreView=loadMoreView;
    }

    @Override
    public void getData(int edition, String letter, int sort,int tab,int page) {
        Observable<ComicList> comicList = clientApiManager.getComicList(edition, letter, sort, tab, page);
        comicList.subscribeOn(Schedulers.io())
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
                if (comicListAdapter.getList()!=null){
                    if (comicListAdapter.getList().isEmpty()){
                        activityView.loadFailView();
                    }
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ComicList>() {
                    @Override
                    public void call(ComicList comicList) {
                       System.out.println("comicList-----"+comicList.getListDataList().get(0).getName());
                        comicListAdapter.settList(comicList.getListDataList());
                        comicListAdapter.notifyDataSetChanged();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                     System.out.println("comic_list_error-----"+throwable.getMessage());
                        activityView.hideProgress();
                        activityView.loadFailView();
                    }
                });
    }

    @Override
    public void getLoadMoreData(int edition, String letter, int sort, int tab, int page) {
        Observable<ComicList> comicList = clientApiManager.getComicList(edition, letter, sort, tab, page);
        comicList.subscribeOn(Schedulers.io())
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
                if (comicListAdapter.getList()!=null){
                    if (comicListAdapter.getList().isEmpty()){
                        loadMoreView.showLoadFail();
                    }
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ComicList>() {
                    @Override
                    public void call(ComicList comicList) {
                        comicListAdapter.getList().clear();
                        comicListAdapter.getList().addAll(comicList.getListDataList());
                        comicListAdapter.notifyDataSetChanged();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("comic_list_error-----"+throwable.getMessage());
                         loadMoreView.hideMoreProgress();
                        loadMoreView.showLoadFail();
                    }
                });
    }
}
