package linhao.redridinghood.model.storage;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by linhao on 2016/8/23.
 */
public final class SettingShared {

    private static final String TAG = "SettingShared";
    private static final String KEY_ENABLE_THEME_DARK = "enableThemeDark";


    //保存夜间和白天的主题
    public static void setEnableThemeDark(@NonNull Context context, boolean enable) {
     SharedWrapper.with(context,TAG);
        SharedWrapper.setBool(KEY_ENABLE_THEME_DARK,enable);
    }

    public static boolean isEnableThemeDark(@NonNull Context context) {
        SharedWrapper.with(context,TAG);
        boolean enableThemeDark=SharedWrapper.getBoolean(KEY_ENABLE_THEME_DARK,false);
        return enableThemeDark;
    }
}
