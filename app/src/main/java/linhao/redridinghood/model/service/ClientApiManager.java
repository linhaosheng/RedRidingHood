package linhao.redridinghood.model.service;

import java.util.List;

import javax.inject.Inject;

import linhao.redridinghood.application.MyApplication;
import linhao.redridinghood.model.api.ApiClient;
import linhao.redridinghood.model.entity.AddRecommend;
import linhao.redridinghood.model.entity.Carousel;
import linhao.redridinghood.model.entity.ComicList;
import linhao.redridinghood.model.entity.Detail;
import linhao.redridinghood.model.entity.HotNewAnimate;
import linhao.redridinghood.model.entity.News;
import linhao.redridinghood.model.entity.NewsDetail;
import linhao.redridinghood.model.entity.Ranking;
import linhao.redridinghood.model.entity.RecentUpdate;
import linhao.redridinghood.model.entity.Search;
import linhao.redridinghood.model.entity.SearchEntity;
import linhao.redridinghood.model.entity.Topics;
import linhao.redridinghood.model.entity.TopicsDetail;
import linhao.redridinghood.model.entity.WeekUpdate;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by linhao on 2016/8/17.
 */
public class ClientApiManager implements ApiClient {


    public ClientApiManager(){
        MyApplication.Instance().inject(this);
    }

    public ClientApiManager(int a){

    }
    @Inject
    ApiClient apiClient;

    @Override
    public Observable<List<Carousel>> getCarousel() {
        return apiClient.getCarousel();
    }

    @Override
    public Observable<List<WeekUpdate>> getWeekUpdate() {
        return apiClient.getWeekUpdate();
    }

    @Override
    public Observable<List<AddRecommend>> getAddRecommend() {
        return apiClient.getAddRecommend();
    }

    @Override
    public Observable<List<RecentUpdate>> getRecentUpdate() {
        return apiClient.getRecentUpdate();
    }

    @Override
    public Observable<List<HotNewAnimate>> getHotNewAnimate() {
        return apiClient.getHotNewAnimate();
    }

    @Override
    public Observable<List<Ranking>> getRanking() {
        return apiClient.getRanking();
    }

    @Override
    public Observable<Search> getSearch(@Query("k") String key, @Query("page") int pageNum) {
        return apiClient.getSearch(key,pageNum);
    }


    @Override
    public Observable<Detail> getDetail(@Query("url") String url) {
        return apiClient.getDetail(url);
    }

    @Override
    public Observable<ComicList> getComicList(@Query("ver") int ver, @Query("letter") String letter, @Query("sort") int sort, @Query("tab") int tab, @Query("page") int page) {
        return apiClient.getComicList(ver, letter, sort, tab, page);
    }

    @Override
    public Observable<News> getNews(@Query("page") int page) {
        return apiClient.getNews(page);
    }

    @Override
    public Observable<NewsDetail> getNewsDetail(@Query("url") String url) {
        return apiClient.getNewsDetail(url);
    }

    @Override
    public Observable<List<Topics>> getTopics() {
        return apiClient.getTopics();
    }

    @Override
    public Observable<TopicsDetail> getTopicsDetail(@Query("url") String url, @Query("page") int page) {
        return apiClient.getTopicsDetail(url, page);
    }
}
