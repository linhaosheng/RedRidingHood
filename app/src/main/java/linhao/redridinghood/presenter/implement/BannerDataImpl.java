package linhao.redridinghood.presenter.implement;

import java.util.List;


import javax.inject.Inject;

import linhao.redridinghood.model.entity.Carousel;
import linhao.redridinghood.model.service.ClientApiManager;
import linhao.redridinghood.presenter.contract.BannerData;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.BannerAdapter;
import linhao.redridinghood.ui.view.ActivityView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by linhao on 2016/8/17.
 */
public class BannerDataImpl implements BannerData {

    @Inject
    ClientApiManager clientApiManager;

    @Inject
    ActivityView bannerView;

    @Inject
    BannerAdapter bannerAdapter;

    public BannerDataImpl() {
        MainActivity.InstanceManager().inject(this);
    }

    @Override
    public void getData() {
        final Observable<List<Carousel>> data = clientApiManager.getCarousel();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        bannerView.showProgress();
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        bannerView.hideProgress();
                        if (bannerAdapter.getCarouselList() != null) {
                            if (bannerAdapter.getCarouselList().isEmpty()) {
                                bannerView.loadFailView();
                                System.out.println("banner-------doOnUnsubscribe");
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Carousel>>() {
                    @Override
                    public void call(List<Carousel> carouselList) {
                        if (carouselList != null) {
                            if (bannerAdapter != null) {
                                bannerAdapter.setCarouselList(carouselList);
                                bannerAdapter.notifyDataSetChanged();
                                System.out.print("carouselList --- " + carouselList.size());
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        bannerView.loadFailView();
                        System.out.println("throwable--------" + throwable.getMessage());
                    }
                });
    }
}
