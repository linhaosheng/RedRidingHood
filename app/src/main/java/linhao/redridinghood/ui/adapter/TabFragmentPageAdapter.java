package linhao.redridinghood.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import linhao.redridinghood.ui.fragment.WeekUpdateFragment;
import linhao.redridinghood.util.ConstantUtil;

/**
 * Created by linhao on 2016/8/14.
 */
public class TabFragmentPageAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentArrayList;
    private List<String> titleList;
    private int Fragment_Flag;
    private FragmentManager fm;

    public void setFragment_Flag(int Fragment_Flag) {
        this.Fragment_Flag = Fragment_Flag;
    }

    public TabFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabFragmentPageAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList, List<String> titleList) {
        super(fm);
        this.fm = fm;
        this.fragmentArrayList = fragmentArrayList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {

        return fragmentArrayList == null ? null : fragmentArrayList.get(position);
    }


    @Override
    public int getCount() {
        return fragmentArrayList == null ? 0 : fragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleList != null) {
            String title = titleList.get(position);
            return title;
        }
        return null;
    }


}
