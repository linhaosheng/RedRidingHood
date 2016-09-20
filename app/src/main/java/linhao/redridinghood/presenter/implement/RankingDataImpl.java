package linhao.redridinghood.presenter.implement;

import java.util.List;

import javax.inject.Inject;

import linhao.redridinghood.model.entity.Ranking;
import linhao.redridinghood.model.service.ClientApiManager;
import linhao.redridinghood.presenter.contract.RankingData;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.RankingAdapter;
import linhao.redridinghood.ui.view.ActivityView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by linhao on 2016/9/2.
 */
public class RankingDataImpl implements RankingData {

    @Inject
    ClientApiManager clientApiManager;
    @Inject
    ActivityView activityView;
    @Inject
    RankingAdapter rankingAdapter;

    public RankingDataImpl(){
        MainActivity.InstanceManager().inject(this);
    }
    @Override
    public void getData() {
        final Observable<List<Ranking>> ranking = clientApiManager.getRanking();
        ranking.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        activityView.showProgress();
                    }
                }).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                if (rankingAdapter.getRankingList()!=null){
                    if (rankingAdapter.getRankingList().isEmpty()){
                        activityView.loadFailView();
                    }
                }
                activityView.hideProgress();
            }
        }).subscribe(new Action1<List<Ranking>>() {
            @Override
            public void call(List<Ranking> rankings) {
                if (rankingAdapter !=null) {
                    rankingAdapter.setRankingList(rankings);
                    rankingAdapter.notifyDataSetChanged();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("ranking-error--"+throwable.getMessage());
              activityView.hideProgress();
                activityView.loadFailView();
            }
        });
    }
}
