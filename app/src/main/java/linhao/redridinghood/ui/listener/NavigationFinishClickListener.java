package linhao.redridinghood.ui.listener;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;


/**
 * Created by linhao on 2016/9/17.
 */
public class NavigationFinishClickListener implements OnClickListener {

    private final Activity activity;

    public NavigationFinishClickListener(@NonNull Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        ActivityCompat.finishAfterTransition(activity);
    }
}
