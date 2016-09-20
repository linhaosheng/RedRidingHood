package linhao.redridinghood.presenter.implement;

import java.util.List;

import javax.inject.Inject;

import linhao.redridinghood.model.entity.HotNewAnimate;
import linhao.redridinghood.model.service.ClientApiManager;
import linhao.redridinghood.presenter.contract.HotNewData;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.GridViewAdapter;
import linhao.redridinghood.ui.adapter.HotNewAdapter;
import linhao.redridinghood.ui.view.ActivityView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by linhao on 2016/9/10.
 */
public class HotNewDataImpl implements HotNewData {

    @Inject
    ActivityView activityView;
    @Inject
    ClientApiManager clientApiManager;
    @Inject
    GridViewAdapter gridViewAdapter;
    @Inject
    HotNewAdapter hotNewAdapter;

    public HotNewDataImpl() {
        MainActivity.InstanceManager().inject(this);
    }

    @Override
    public void loadData() {
        Observable<List<HotNewAnimate>> hotNewAnimate = clientApiManager.getHotNewAnimate();
        hotNewAnimate.subscribeOn(Schedulers.io())
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
                if (hotNewAdapter != null) {
                    if (hotNewAdapter.getHotNewAnimateList().isEmpty()) {
                        activityView.loadFailView();
                    }
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<HotNewAnimate>>() {
                    @Override
                    public void call(List<HotNewAnimate> hotNewAnimates) {
                        if (hotNewAnimates != null) {
                            hotNewAdapter.setHotNewAnimateList(hotNewAnimates);
                            hotNewAdapter.setType(hotNewAdapter.getYear_Fragment_Flag(),hotNewAdapter.getMonth_Fragment_Flag());
                            hotNewAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("hot_new_data" + throwable.getMessage());
                        activityView.hideProgress();
                        activityView.loadFailView();
                    }
                });
    }
}
