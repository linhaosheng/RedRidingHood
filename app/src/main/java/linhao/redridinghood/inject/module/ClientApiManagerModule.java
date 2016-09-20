package linhao.redridinghood.inject.module;

import android.content.Context;
import android.support.v4.view.ViewPager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import linhao.redridinghood.model.service.ClientApiManager;
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
import linhao.redridinghood.ui.adapter.BannerAdapter;
import linhao.redridinghood.ui.adapter.ComicListAdapter;
import linhao.redridinghood.ui.adapter.DownLoadAdapter;
import linhao.redridinghood.ui.adapter.GridViewAdapter;
import linhao.redridinghood.ui.adapter.HotNewAdapter;
import linhao.redridinghood.ui.adapter.NewsAdapter;
import linhao.redridinghood.ui.adapter.RankingAdapter;
import linhao.redridinghood.ui.adapter.RecentUpdateAdapter;
import linhao.redridinghood.ui.adapter.RecommandAdapter;
import linhao.redridinghood.ui.adapter.SearchAdapter;
import linhao.redridinghood.ui.adapter.TopicAdapter;
import linhao.redridinghood.ui.adapter.TopicDetailAdapter;
import linhao.redridinghood.ui.adapter.WeekUpdateAdapter;
import linhao.redridinghood.ui.view.ActivityView;

/**
 * Created by linhao on 2016/8/17.
 */
@Module
public class ClientApiManagerModule {

    private ActivityView activityView;
    private Context context;
    private ViewPager viewPager;

    public ClientApiManagerModule(ActivityView activityView, Context context, ViewPager viewPager) {
        this.activityView = activityView;
        this.context = context;
        this.viewPager = viewPager;
    }

    public ClientApiManagerModule(ActivityView activityView, Context context) {
        this(activityView, context, null);
    }

    @Provides
    @Singleton
    public ClientApiManager provideApiManager() {
        return new ClientApiManager();
    }

    @Provides
    @Singleton
    public BannerDataImpl providerBannerDataImpl() {
        return new BannerDataImpl();
    }

    @Provides
    @Singleton
    public ActivityView providerBannerView() {
        return activityView;
    }

    @Provides
    @Singleton
    public BannerAdapter providerBannerAdapter() {
        return new BannerAdapter(context, viewPager);
    }

    @Singleton
    @Provides
    public GridViewAdapter providerGridViewAdapter() {
        return new GridViewAdapter(context);
    }

    @Provides
    @Singleton
    public FragmentDataImpl providerFragmentDataImpl() {
        return new FragmentDataImpl();
    }

    @Provides
    @Singleton
    public RecentUpdateAdapter providerRecentUpdateAdapter() {
        return new RecentUpdateAdapter(context);
    }

    @Provides
    @Singleton
    public RecentUpdateDataImpl providerRecentUpdate() {
        return new RecentUpdateDataImpl();
    }

    @Singleton
    @Provides
    public RankingDataImpl providerRanking(){
        return new RankingDataImpl();
    }

    @Provides
    @Singleton
    public RankingAdapter providerRankingAdapter(){
        return new RankingAdapter(context);
    }

    @Provides
    @Singleton
    public NewsAdapter providerNewsAdapter(){
        return new NewsAdapter(context);
    }

    @Provides
    @Singleton
    public NewsDataImpl providerNewsData(){
        return new NewsDataImpl();
    }

    @Provides
    @Singleton
    public WeekUpdateDataImpl providerWeekUpdateData(){
        return new WeekUpdateDataImpl();
    }

    @Provides
    @Singleton
    public WeekUpdateAdapter providerWeekUpdateAdapter(){
        return new WeekUpdateAdapter(context);
    }

    @Singleton
    @Provides
    public HotNewDataImpl providerHotNewDataImpl(){
        return new HotNewDataImpl();
    }

    @Singleton
    @Provides
    public HotNewAdapter providerHotNewAdapter(){
        return new HotNewAdapter(context);
    }

    @Singleton
    @Provides
    public ComicListDataImpl providerComicListDataImpl(){
        return new ComicListDataImpl();
    }

    @Singleton
    @Provides
    public ComicListAdapter providerComicListAdapter(){
        return new ComicListAdapter(context);
    }

    @Singleton
    @Provides
    public TopicDataImpl providerTopicDataImpl(){
        return new TopicDataImpl();
    }

    @Singleton
    @Provides
    public TopicAdapter providerTopicAdapter(){
        return new TopicAdapter(context);
    }

    @Singleton
    @Provides
    public TopicDetailAdapter providerTopicDetailAdapterr(){
        return new TopicDetailAdapter(context);
    }

    @Singleton
    @Provides
    public TopicDetailDataImpl providerTopicDetailDataImpl(){
        return new TopicDetailDataImpl();
    }

    @Singleton
    @Provides
    public ComicDetailDataImpl providerComicDetailDataImpl(){
        return new ComicDetailDataImpl();
    }

    @Singleton
    @Provides
    public DownLoadAdapter providerDownLoadAdapter(){
        return new DownLoadAdapter(context);
    }

    @Singleton
    @Provides
    public RecommandAdapter providerRecommandAdapter(){
        return new RecommandAdapter(context);
    }


    @Singleton
    @Provides
    public SearchDataImpl providerSearchDataImpl(){
        return new SearchDataImpl();
    }

    @Singleton
    @Provides
    public SearchAdapter providerSearchAdapter(){
        return new SearchAdapter(context);
    }
}
