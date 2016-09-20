package linhao.redridinghood.presenter.implement;

import java.util.List;

import javax.inject.Inject;

import linhao.redridinghood.model.entity.AddRecommend;
import linhao.redridinghood.model.service.ClientApiManager;
import linhao.redridinghood.presenter.contract.FragmentData;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.GridViewAdapter;
import linhao.redridinghood.ui.view.ActivityView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by linhao on 2016/8/25.
 */
public class FragmentDataImpl implements FragmentData {

    @Inject
    ClientApiManager clientApiManager;

    @Inject
    ActivityView bannerView;

    @Inject
    GridViewAdapter gridViewAdapter;

    public FragmentDataImpl() {
        MainActivity.InstanceManager().inject(this);
    }

    @Override
    public void getFragmentData() {
        Observable<List<AddRecommend>> data = clientApiManager.getAddRecommend();
        data.subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        bannerView.showProgress();
                        if (gridViewAdapter.getAddRecommendList() != null) {
                            if (gridViewAdapter.getAddRecommendList().isEmpty()) {
                                bannerView.loadFailView();
                                System.out.println("doOnSubscribe");
                            }
                        }
                    }
                }).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                bannerView.hideProgress();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<AddRecommend>>() {
                    @Override
                    public void call(List<AddRecommend> addRecommends) {
                        gridViewAdapter.setAddRecommendList(addRecommends);
                        gridViewAdapter.notifyDataSetChanged();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        bannerView.hideProgress();
                        bannerView.loadFailView();
                        System.out.println("throwable " + throwable.getMessage());
                    }
                });
    }
}
