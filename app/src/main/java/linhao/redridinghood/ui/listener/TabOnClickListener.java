package linhao.redridinghood.ui.listener;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by linhao on 2016/8/14.
 */
public class TabOnClickListener implements View.OnClickListener{

    private ViewPager viewPager;
    private int currentItem;
    public TabOnClickListener(ViewPager viewPager,int currentItem){
    this.viewPager=viewPager;
    }

    @Override
    public void onClick(View view) {
       viewPager.setCurrentItem(currentItem);
    }
}
