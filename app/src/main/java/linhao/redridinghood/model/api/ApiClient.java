package linhao.redridinghood.model.api;

import java.util.List;

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
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by linhao on 2016/8/13.
 */
public interface ApiClient {

    // 接口文档地址
    String MAIN_URL = "https://hltm-api.tomoya.cn/";

    //红旅动漫页首地址
    //String MAIN_URL="http://www.hltm.tv/";

    //轮播图
    String CAROUSEL = "carousel";

    //一周更新(节目单)
    String WEEK_UPDATE = "weekUpdate";

    //新增/推荐 连载/完结 动漫
    String ADD_RECOMMAND = "addRecommend";

    //最新更新
    String RECENENT_UPDATE = "recentUpdate";

    //热门新番
    String HOT_NEW_ANIMATE = "hotNewAnimate";

    //2016年7月新番排行榜/2016年4月新番排行榜/动漫总排行榜/动漫资讯
    String RAN_KING = "ranking";

    //搜索
    String SEARCH = "search";

    //动漫详情
    String DETAIL = "detail";

    //连载动漫/完结动漫
    String LIST = "list";

    //新闻列表
    String NEW_LIST = "news";

    //新闻详情
    String NEWS_DEDAIL = "newsDetail";

    //专题列表
    String TOPICS = "topics";

    //专题详情列表
    String TOPIC_DEDAIL = "topicDetail";

    /**
     * 获取轮播图列表
     *
     * @return
     */
    @GET(CAROUSEL)
    Observable<List<Carousel>> getCarousel();

    /**
     * 获取一周更新(节目单)
     *
     * @return
     */
    @GET(WEEK_UPDATE)
    Observable<List<WeekUpdate>> getWeekUpdate();

    /**
     * 获取新增/推荐 连载/完结 动漫
     *
     * @return
     */
    @GET(ADD_RECOMMAND)
    Observable<List<AddRecommend>> getAddRecommend();


    /**
     * 获取最新更新
     *
     * @return
     */
    @GET(RECENENT_UPDATE)
    Observable<List<RecentUpdate>> getRecentUpdate();

    /**
     * 获取热门新番
     *
     * @return
     */
    @GET(HOT_NEW_ANIMATE)
    Observable<List<HotNewAnimate>> getHotNewAnimate();

    /**
     * 获取2016年7月新番排行榜/2016年4月新番排行榜/动漫总排行榜/动漫资讯
     *
     * @return
     */
    @GET(RAN_KING)
    Observable<List<Ranking>> getRanking();

    /**
     * 获取搜索
     *
     * @param key
     * @param pageNum
     * @return
     */
    @GET(SEARCH)
    Observable<Search> getSearch(@Query("k") String key, @Query("page") int pageNum);

    /**
     * 获取动漫详情
     *
     * @param url
     * @return
     */
    @GET(DETAIL)
    Observable<Detail> getDetail(@Query("url") String url);

    /**
     * 获取连载动漫/完结动漫
     * @param ver
     * @param letter
     * @param sort
     * @param tab
     * @param page
     * @return
     */
    @GET(LIST)
    Observable<ComicList>getComicList(@Query("ver")int ver,@Query("letter")String letter,@Query("sort")int sort,@Query("tab")int tab,@Query("page")int page);


    /**
     * 获取新闻列表
     */
    @GET(NEW_LIST)
    Observable<News>getNews(@Query("page")int page);

    /**
     * 新闻详情
     * url String 对于红旅网站新闻详情页面的url(需要Encoder)
     */
    @GET(NEWS_DEDAIL)
    Observable<NewsDetail>getNewsDetail(@Query("url")String url);

    /**
     * 专题列表
     */
    @GET(TOPICS)
    Observable<List<Topics>>getTopics();

    /**
     * 专题详情列表
     * page Number 页数
     url String 对于红旅网站专题详情页面的url(需要Encoder)
     */
    @GET(TOPIC_DEDAIL)
    Observable<TopicsDetail>getTopicsDetail(@Query("url")String url,@Query("page")int page);

}
