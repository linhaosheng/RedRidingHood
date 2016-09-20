package linhao.redridinghood.inject.component;

import javax.inject.Singleton;

import dagger.Component;
import linhao.redridinghood.inject.module.ClientApiManagerModule;
import linhao.redridinghood.model.service.ClientApiManager;
import linhao.redridinghood.presenter.contract.FragmentData;
import linhao.redridinghood.presenter.implement.BannerDataImpl;
import linhao.redridinghood.presenter.implement.ComicDetailDataImpl;
import linhao.redridinghood.presenter.implement.ComicListDataImpl;
import linhao.redridinghood.presenter.implement.FragmentDataImpl;
import linhao.redridinghood.presenter.implement.HotNewDataImpl;
import linhao.redridinghood.presenter.implement.NewsDataImpl;
import linhao.redridinghood.presenter.implement.RankingDataImpl;
import linhao.redridinghood.presenter.implement.RecentUpdateDataImpl;
import linhao.redridinghood.presenter.implement.SearchDataImpl;
import linhao.redridinghood.presenter.implement.TopicDataImpl;
import linhao.redridinghood.presenter.implement.TopicDetailDataImpl;
import linhao.redridinghood.presenter.implement.WeekUpdateDataImpl;
import linhao.redridinghood.ui.activity.BaseActivty;
import linhao.redridinghood.ui.activity.ComicDetailActivity;
import linhao.redridinghood.ui.activity.ComicListActivity;
import linhao.redridinghood.ui.activity.EndComicActivity;
import linhao.redridinghood.ui.activity.HotNewActivity;
import linhao.redridinghood.ui.activity.MainActivity;
import linhao.redridinghood.ui.activity.NewsActivity;
import linhao.redridinghood.ui.activity.RangkingActivity;
import linhao.redridinghood.ui.activity.RecentUpdateActivity;
import linhao.redridinghood.ui.activity.SearchActivity;
import linhao.redridinghood.ui.activity.TopicActivity;
import linhao.redridinghood.ui.activity.TopicDetailActivity;
import linhao.redridinghood.ui.activity.WeekUpdateActivity;
import linhao.redridinghood.ui.fragment.AddEndFragment;
import linhao.redridinghood.ui.fragment.AddSerialFragment;
import linhao.redridinghood.ui.fragment.BaseFragment;
import linhao.redridinghood.ui.fragment.DownLoadFragment;
import linhao.redridinghood.ui.fragment.HotNewFirstFragemnt;
import linhao.redridinghood.ui.fragment.HotNewFourFragemnt;
import linhao.redridinghood.ui.fragment.HotNewItemFragment;
import linhao.redridinghood.ui.fragment.HotNewSecondFragemnt;
import linhao.redridinghood.ui.fragment.HotNewThridFragemnt;
import linhao.redridinghood.ui.fragment.RankingFragment;
import linhao.redridinghood.ui.fragment.RecommendEndFragment;
import linhao.redridinghood.ui.fragment.RecommendSerialFragment;
import linhao.redridinghood.ui.fragment.WeekUpdateFragment;

/**
 * Created by linhao on 2016/8/17.
 */
@Singleton
@Component(modules = ClientApiManagerModule.class)
public interface ClientApiManagerComponent {

    void inject(BannerDataImpl bannerData);
    void inject(AddEndFragment addEndFragment);
    void inject(AddSerialFragment addSerialFragment);
    void inject(RecommendSerialFragment recommendSerialFragment);
    void inject(RecommendEndFragment recommendEndFragment);
    void inject(MainActivity baseActivty);            //不支持父类注入到子类
    void inject(FragmentDataImpl fragmentData);
    void inject(RecentUpdateActivity recentUpdateActivity);
    void inject(RecentUpdateDataImpl recentUpdateData);
    void inject(RankingDataImpl rankingData);
    void inject(RankingFragment rankingFragment);
    void inject(RangkingActivity rangkingActivity);
    void inject(NewsDataImpl newsData);
    void inject(NewsActivity newsActivity);
    void inject(WeekUpdateDataImpl weekUpdateData);
    void inject(WeekUpdateActivity weekUpdateActivity);
    void inject(WeekUpdateFragment weekUpdateFragment);
    void inject(HotNewFirstFragemnt hotNewFirstFragemnt);
    void inject(HotNewSecondFragemnt hotNewSecondFragemnt);
    void inject(HotNewThridFragemnt hotNewThridFragemnt);
    void inject(HotNewFourFragemnt hotNewFourFragemnt);
    void inject(HotNewDataImpl hotNewData);
    void inject(HotNewActivity hotNewActivity);
    void inject(HotNewItemFragment hotNewItemFragment);
    void inject(ComicListDataImpl comicListData);
    void inject(ComicListActivity comicListActivity);
    void inject(EndComicActivity endComicActivity);
    void inject(TopicDataImpl topicData);
    void inject(TopicActivity topicActivity);
    void inject(TopicDetailActivity topicDetailActivity);
    void inject(TopicDetailDataImpl topicDetailData);
    void inject(ComicDetailDataImpl comicDetailData);
    void inject(ComicDetailActivity comicDetailActivity);
    void inject(DownLoadFragment downLoadFragment);
    void inject(SearchDataImpl searchData);
    void inject(SearchActivity searchActivity);
}
