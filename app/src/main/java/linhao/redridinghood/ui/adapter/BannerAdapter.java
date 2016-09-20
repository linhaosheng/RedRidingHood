package linhao.redridinghood.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import linhao.redridinghood.R;
import linhao.redridinghood.model.entity.Carousel;
import linhao.redridinghood.util.GlideUtil;

/**
 * Created by linhao on 2016/8/13.
 */
public class BannerAdapter  extends PagerAdapter{

    private Context context;
    private  List<Carousel>carouselList;
    private ViewPager viewPager;
    private final int FAKE_BANNER_SIZE = 100;
    private final int DEFAULT_BANNER_SIZE = 4;

    public BannerAdapter(Context context, ViewPager viewPager){
        this.context=context;
        this.viewPager=viewPager;
    }

    public BannerAdapter(Context context){
        this.context=context;
    }

    public void setCarouselList(List<Carousel> carouselList1){
        this.carouselList=carouselList1;
    }

    public  List<Carousel> getCarouselList(){
        return carouselList;
    }

    @Override
    public int getCount() {
        return carouselList==null ? 0:carouselList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.banner_item,container,false);
        ImageView imageView= (ImageView) view.findViewById(R.id.banner_img);
        GlideUtil.LoadImg(context,imageView,carouselList.get(position).getImgUrl());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=carouselList.get(position).getDetailUrl();
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        int position = viewPager.getCurrentItem();
        if (position == 0){
          //  position = DEFAULT_BANNER_SIZE;
            viewPager.setCurrentItem(position,false);
        }else if (position == FAKE_BANNER_SIZE - 1){
            position = DEFAULT_BANNER_SIZE - 1;
            viewPager.setCurrentItem(position,false);
        }
    }
}
