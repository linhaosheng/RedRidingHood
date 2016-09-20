package linhao.redridinghood.presenter.implement;

import java.util.List;

import javax.inject.Inject;

import linhao.redridinghood.model.entity.RecentUpdate;
import linhao.redridinghood.model.service.ClientApiManager;
import linhao.redridinghood.presenter.contract.RecentUpdateData;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.RecentUpdateAdapter;
import linhao.redridinghood.ui.view.ActivityView;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by linhao on 2016/9/1.
 */
public class RecentUpdateDataImpl implements RecentUpdateData {

    @Inject
    ClientApiManager clientApiManager;

    @Inject
    ActivityView bannerView;

    @Inject
    RecentUpdateAdapter recentUpdateAdapter;

    public RecentUpdateDataImpl() {
        MainActivity.InstanceManager().inject(this);
    }

    @Override
    public void getRecentUpdateData() {
        Observable<List<RecentUpdate>> recentUpdate = clientApiManager.getRecentUpdate();
        recentUpdate.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        bannerView.showProgress();
                    }
                }).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                bannerView.hideProgress();
                if (recentUpdateAdapter.getRecentUpdateList() != null) {
                    if (recentUpdateAdapter.getRecentUpdateList().isEmpty()) {
                        bannerView.loadFailView();
                    }
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<RecentUpdate>>() {
                    @Override
                    public void call(List<RecentUpdate> recentUpdates) {
                        if (recentUpdateAdapter != null) {
                    //       recentUpdateAdapter.getRecentUpdateList().clear();
                            recentUpdateAdapter.setRecentUpdateList(recentUpdates);
                          //  recentUpdateAdapter.getRecentUpdateList().addAll(recentUpdates);
                            recentUpdateAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        bannerView.hideProgress();
                        bannerView.loadFailView();
                    }
                });
    }
}
