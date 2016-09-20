package linhao.redridinghood.ui.listener;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.CheckedTextView;

import java.util.List;

/**
 * Created by linhao on 2016/8/22.
 */
public class DrawerLayoutListener extends DrawerLayout.SimpleDrawerListener {

    private List<CheckedTextView> checkedTextViewList;

    public DrawerLayoutListener(List<CheckedTextView> checkedTextViewList){
        this.checkedTextViewList=checkedTextViewList;
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
    }
}
