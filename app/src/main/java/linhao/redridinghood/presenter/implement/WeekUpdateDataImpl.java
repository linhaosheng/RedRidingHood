package linhao.redridinghood.presenter.implement;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import linhao.redridinghood.model.entity.Search;
import linhao.redridinghood.model.entity.SearchEntity;
import linhao.redridinghood.model.entity.WeekUpdate;
import linhao.redridinghood.model.service.ClientApiManager;
import linhao.redridinghood.presenter.contract.WeekUpdateData;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.adapter.WeekUpdateAdapter;
import linhao.redridinghood.ui.view.ActivityView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by linhao on 2016/9/4.
 */
public class WeekUpdateDataImpl implements WeekUpdateData {

    @Inject
    ActivityView activityView;
    @Inject
    ClientApiManager clientApiManager;
    @Inject
    WeekUpdateAdapter weekUpdateAdapter;

    private int currentPosition;
    private WeekUpdate weekUpdate;
    private String url;

    public WeekUpdateDataImpl() {
        MainActivity.InstanceManager().inject(this);
    }

    //获取一周更新的数据
    @Override
    public void getWeekUpdateData() {
        final Observable<List<WeekUpdate>> weekUpdate = clientApiManager.getWeekUpdate();
        weekUpdate.subscribeOn(Schedulers.computation())
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
                if (weekUpdateAdapter.getUpdateList() != null) {
                    if (weekUpdateAdapter.getUpdateList().isEmpty()) {
                        activityView.loadFailView();
                    }
                } else {
                    activityView.loadFailView();
                }
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<WeekUpdate>>() {
                    @Override
                    public void call(List<WeekUpdate> weekUpdates) {
                        if (weekUpdates != null) {
                            weekUpdateAdapter.setUpdateList(weekUpdates);
                            for (WeekUpdate weekUpdate1 : weekUpdates) {
                                if (weekUpdate1.getWeekLists().size() > 0) {
                                    for (int i = 0; i < weekUpdate1.getWeekLists().size(); i++) {
                                        WeekUpdate.WeekListItem weekList = weekUpdate1.getWeekLists().get(i);
                                        String name = weekList.getName();
                                        int index = name.lastIndexOf("第");
                                        String key = null;
                                        if (index > 0) {
                                            key = name.substring(0, index);
                                        } else {
                                            key = name;
                                        }
                                        getWeekUpdataItem(key, url, 1, weekUpdate1, i);
                                    }
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println(" weekUpdate---throwable------" + throwable.getMessage());
                        activityView.hideProgress();
                        activityView.loadFailView();
                    }
                });
    }

    //根据标题和页码去查找数据

    @Override
    public void getWeekUpdataItem(final String key, final String url, final int pageNum, final WeekUpdate weekUpdate, final int position) {
        final Observable<Search> search = clientApiManager.getSearch(key, pageNum);
        search.subscribeOn(Schedulers.computation())
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
                if (weekUpdateAdapter.getListList() != null) {
                    if (weekUpdateAdapter.getListList().isEmpty()) {
                        activityView.loadFailView();
                    }
                } else {
                    activityView.loadFailView();
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Search>() {
                    @Override
                    public void call(Search searchs) {
                        if (searchs != null) {
                            int cuttentPosition = position + 1;
                            if (searchs.getTotalCount() == 0) {
                                Search.ListItem listItem = new Search.ListItem();
                                listItem.setName(key);
                                searchs.getListItems().add(listItem);
                            }
                            weekUpdateAdapter.setListList(searchs.getListItems());
                            if (cuttentPosition == weekUpdate.getWeekLists().size()) {
                                weekUpdateAdapter.setSearchMap(weekUpdate.getWeekDay(), weekUpdateAdapter.getListList());
                                weekUpdateAdapter.initList();
                                weekUpdateAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("search throwable----" + throwable.getMessage());
                        activityView.hideProgress();
                        activityView.loadFailView();
                    }
                });
    }

    public void test() {
        final Observable<List<WeekUpdate>> weekUpdate = clientApiManager.getWeekUpdate();
        weekUpdate.subscribeOn(Schedulers.io())
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
                if (weekUpdateAdapter.getUpdateList() != null) {
                    if (weekUpdateAdapter.getUpdateList().isEmpty()) {
                        activityView.loadFailView();
                    }
                } else {
                    activityView.loadFailView();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<List<WeekUpdate>, Observable<Search>>() {
                    @Override
                    public Observable<Search> call(List<WeekUpdate> weekUpdates) {
                        Observable<Search> searchObservable = null;
                        for (WeekUpdate weekUpdate1 : weekUpdates) {
                            for (int i = 0; i < weekUpdate1.getWeekLists().size(); i++) {
                                WeekUpdate.WeekListItem weekList = weekUpdate1.getWeekLists().get(i);
                                String name = weekList.getName();
                                int index = name.indexOf("第");
                                String key = null;
                                if (index > 0) {
                                    key = name.substring(0, index);
                                } else {
                                    key = name;
                                }
                                searchObservable = clientApiManager.getSearch(key, 1);
                                setCurrentPosition(i);
                                setWeekUpdate(weekUpdate1);
                                return searchObservable;
                            }
                        }
                        return searchObservable;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Search>() {
                    @Override
                    public void call(Search searchs) {
                        if (searchs != null) {
                            int cuttentPosition = getCurrentPosition() + 1;
                            if (searchs.getTotalCount() == 0) {
                                searchs.getListItems().get(0).setName("key");
                            }
                            weekUpdateAdapter.setListList(searchs.getListItems());
                            if (cuttentPosition == getWeekUpdate().getWeekLists().size()) {
                                weekUpdateAdapter.setSearchMap(getWeekUpdate().getWeekDay(), weekUpdateAdapter.getListList());
                                weekUpdateAdapter.getListList().clear();
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("test-throwable-----" + throwable.getMessage());
                    }
                });
    }


    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public WeekUpdate getWeekUpdate() {
        return weekUpdate;
    }

    public void setWeekUpdate(WeekUpdate weekUpdate) {
        this.weekUpdate = weekUpdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}



