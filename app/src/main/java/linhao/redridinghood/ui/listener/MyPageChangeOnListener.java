package linhao.redridinghood.ui.listener;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import linhao.redridinghood.ui.adapter.DownLoadAdapter;
import linhao.redridinghood.ui.adapter.GridViewAdapter;
import linhao.redridinghood.ui.adapter.HotNewAdapter;
import linhao.redridinghood.ui.adapter.RankingAdapter;
import linhao.redridinghood.ui.adapter.WeekUpdateAdapter;
import linhao.redridinghood.util.ConstantUtil;

/**
 * Created by linhao on 2016/8/14.
 */
public class MyPageChangeOnListener implements ViewPager.OnPageChangeListener {


    private int currentPosition;
    private GridViewAdapter gridViewAdapter;
    private int adapterType;
    private RankingAdapter rankingAdapter;
    private WeekUpdateAdapter weekUpdateAdapter;
    private HotNewAdapter hotNewAdapter;
    private DownLoadAdapter downLoadAdapter;
    private int YearType;

    public void setHotNewAdapter(HotNewAdapter hotNewAdapter){
        this.hotNewAdapter=hotNewAdapter;
    }

    public void setYearType(int YearType){
        this.YearType=YearType;
    }
    public void setWeekUpdateAdapter(WeekUpdateAdapter weekUpdateAdapter){
        this.weekUpdateAdapter=weekUpdateAdapter;
    }

    public void setRankingAdapter(RankingAdapter rankingAdapter){
        this.rankingAdapter=rankingAdapter;
    }

    public void setAdapterType(int adapterType){
        this.adapterType=adapterType;
    }

    public void setGridViewAdapter(GridViewAdapter gridViewAdapter) {
        this.gridViewAdapter = gridViewAdapter;
    }

    public void setDownLoadAdapter(DownLoadAdapter downLoadAdapter){
        this.downLoadAdapter=downLoadAdapter;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        switch (adapterType){
            case ConstantUtil.GridViewAdapter:
                gridViewAdapter.setFragment_flag(position);
                gridViewAdapter.notifyDataSetChanged();
                break;
            case ConstantUtil.RankingAdapter:
                rankingAdapter.setRanking_Flag(position);
                rankingAdapter.notifyDataSetChanged();
                break;
            case ConstantUtil.WeekUpdater:
                weekUpdateAdapter.setWeek_Data_Type(position);
                weekUpdateAdapter.notifyDataSetChanged();
                break;
            case ConstantUtil.HotNewAdapter:
                hotNewAdapter.setType(YearType,position);
                hotNewAdapter.notifyDataSetChanged();
                break;
            case ConstantUtil.Comic_List:
                break;
            case ConstantUtil.DownLoadAdapter:
                downLoadAdapter.changeData(currentPosition);
                downLoadAdapter.notifyDataSetChanged();
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
