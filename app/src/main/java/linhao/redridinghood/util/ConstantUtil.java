package linhao.redridinghood.util;

/**
 * Created by linhao on 2016/8/29.
 */
public class ConstantUtil {

    //recommendList 的下标是从零开始的 表示要获取的数据是在recommendList 的下标为0的数据

    /**
     * 首页的页面数据类型
     */
    public final static int AddEndFragment_Flag = 0;
    public final static int AddSerialFragment_Flag = 1;
    public final static int RecommendEndFragmentt_Flag = 2;
    public final static int RecommendSerialFragmentt_Flag = 3;

    public final static int WeekUpdateFragment = 4;
    public final static int GridViewAdapter = 5;
    public final static int RecycleViewAdapter = 6;

    public final static int Recent_Year_Month_One = 7;
    public final static int Recent_Year_Month_Two = 8;
    public final static int Carouse_Ranking = 9;
    public final static int Carouse_News = 10;

    /**
     * 在myOmPageChangeListener中根据adapter类型去刷新adapter数据
     */
    public final static int RankingAdapter = 11;
    public final static int WeekUpdater = 14;
    public final static int HotNewAdapter = 15;
    public final static int DownLoadAdapter=16;

    public final static int Week_Update_Week = 12;
    public final static int Week_Update_Content = 13;

    /**
     * 热门新播的年份
     */
    public final static int First_Year_Fragment_Flag = 3;
    public final static int Second_Year_Fragment_Flag = 2;
    public final static int Thrid_Year_Fragment_Flag = 1;
    public final static int Four_Year_Fragment_Flag = 0;

    /**
     * 热门新播的月份
     */
    public final static int October_Month_Fragment_Flag = 0;
    public final static int July_Fragment_Flag = 1;
    public final static int April_Month_Fragment_Flag = 2;
    public final static int January_Month_Fragment_Flag = 3;

    public final static int Comic_List = 10;
    public final static int Comic_List_Edition = 0;
    public final static int Comic_List_Letter = 1;
    public final static int Comic_List_Sort = 2;
    public final static int Comic_List_Type = 3;

    public final static int DownLoad_Ftp=0;
    public final static int DownLoad_Baidun=1;
    public final static int DownLoad_Magnetic=2;

    public final static String Comic_Content_State = "状态";
    public final static String Comic_Content_Volumn = "点击量";
    public final static String Comic_Content_Type = "动漫类型";
    public final static String Comic_Content_Zone = "动漫地区";
    public final static String Comic_Content_Language = "配音语言";
    public final static String Comic_Content_Year = "上映年份";
    public final static String Comic_Content_Update = "最后更新";

}
