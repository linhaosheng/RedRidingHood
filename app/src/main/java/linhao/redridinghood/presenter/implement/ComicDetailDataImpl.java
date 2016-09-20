package linhao.redridinghood.presenter.implement;

import javax.inject.Inject;

import linhao.redridinghood.model.entity.Detail;
import linhao.redridinghood.model.entity.Search;
import linhao.redridinghood.model.service.ClientApiManager;
import linhao.redridinghood.presenter.contract.ComicDetailData;
import linhao.redridinghood.ui.activity.ComicDetailActivity;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.DownLoadAdapter;
import linhao.redridinghood.ui.fragment.DownLoadFragment;
import linhao.redridinghood.ui.view.ActivityView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by linhao on 2016/9/15.
 */
public class ComicDetailDataImpl implements ComicDetailData {

    @Inject
    ClientApiManager clientApiManager;
    @Inject
    ActivityView activityView;

    public ComicDetailActivity comicDetailActivity;
    public DownLoadFragment downLoadFragment;


    public ComicDetailDataImpl(){
        MainActivity.InstanceManager().inject(this);
    }

    public void setComicDetailActivity(ComicDetailActivity comicDetailActivity){
        this.comicDetailActivity=comicDetailActivity;
    }

    @Override
    public void getData(String url) {
        Observable<Detail> detail = clientApiManager.getDetail(url);
        detail.subscribeOn(Schedulers.io())
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
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Detail>() {
                    @Override
                    public void call(Detail detail) {
                       comicDetailActivity.setContent(detail);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("comicDetail---error"+throwable.getMessage());
                        activityView.hideProgress();
                        activityView.loadFailView();
                    }
                });
    }
}
