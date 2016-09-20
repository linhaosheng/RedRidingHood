package linhao.redridinghood.presenter.implement;

import java.util.List;

import javax.inject.Inject;

import linhao.redridinghood.model.entity.Topics;
import linhao.redridinghood.model.service.ClientApiManager;
import linhao.redridinghood.presenter.contract.TopicData;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.TopicAdapter;
import linhao.redridinghood.ui.view.ActivityView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by linhao on 2016/9/15.
 */
public class TopicDataImpl implements TopicData {

    @Inject
    ClientApiManager clientApiManager;
    @Inject
    ActivityView activityView;
    @Inject
    TopicAdapter topicAdapter;

    public TopicDataImpl(){
        MainActivity.InstanceManager().inject(this);
    }
    @Override
    public void getData() {
        Observable<List<Topics>> topics = clientApiManager.getTopics();
        topics.subscribeOn(Schedulers.io())
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
                if (topicAdapter.getList()!=null){
                    if (topicAdapter.getList().isEmpty()){
                        activityView.loadFailView();
                    }
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Topics>>() {
                    @Override
                    public void call(List<Topics> topicses) {
                        System.out.println("title------"+topicses.get(0).getTitle());
                        topicAdapter.settList(topicses);
                        topicAdapter.notifyDataSetChanged();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("topic---error"+throwable.getMessage());
                        activityView.hideProgress();
                        activityView.loadFailView();
                    }
                });
    }
}
