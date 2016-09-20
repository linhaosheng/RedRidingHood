package linhao.redridinghood.ui.listener;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import linhao.redridinghood.R;

/**
 * Created by linhao on 2016/8/22.
 */
public class NavigationOpenClickListener implements View.OnClickListener {

    private DrawerLayout drawerLayout;

    public NavigationOpenClickListener(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    @Override
    public void onClick(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}
